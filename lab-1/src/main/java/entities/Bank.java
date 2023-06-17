package entities;

import exceptions.BankException;
import exceptions.BankLogicException;
import exceptions.BankValidationException;
import lombok.Data;
import models.BankAccount;
import models.BankConfiguration;
import models.InterestsContaining;
import models.TermableAccount;
import org.jetbrains.annotations.NotNull;
import value_objects.Money;
import value_objects.Pair;
import value_objects.Percent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * Representation of bank
 */
@Data
public class Bank {
    private ArrayList<BankAccount> listOfBankAccounts;
    private String name;
    private BankConfiguration bankConfiguration;
    private EventManager eventManager;

    public enum NotifyOption
    {
        DebitInfo,
        DepositInfo,
        CreditInfo,
        Remittance,
        Withdrawal,
    }

    public Bank(
            @NotNull String name,
            @NotNull BankConfiguration bankConfiguration) throws BankValidationException {

        if (name.isBlank() || name.isEmpty()) {
            throw new BankValidationException("Name can't be empty");
        }

        this.name = name;
        this.bankConfiguration = bankConfiguration;
        listOfBankAccounts = new ArrayList<BankAccount>();
        eventManager = new EventManager("DebitInfo", "DepositInfo", "CreditInfo",
                "RemittanceInfo", "WithdrawalInfo");
    }

    public List<BankAccount> getListOfBankAccounts()
    {
        return  Collections.unmodifiableList(listOfBankAccounts);
    }

    /**
     * Add new {@link BankAccount}
     * @param bankAccount
     */
    public void addAccount(@NotNull BankAccount bankAccount) {
        listOfBankAccounts.add(bankAccount);
    }

    /**
     * Create {@link Client}'s subscribe to event
     * @param clientId
     * @param option
     * @throws BankLogicException
     */
    public void createSubscribe(UUID clientId, NotifyOption option) throws BankLogicException {

        var account = listOfBankAccounts.stream()
                .filter(entry -> entry.getOwner().getId() == clientId).findFirst().orElse(null);

        if (account == null)
        {
            throw new BankLogicException("Client have no accounts in this bank");
        }

        var client = account.getOwner();

        switch (option) {
            case DebitInfo -> {
                eventManager.subscribe("DebitInfo", client.getMessageHandler());
            }
            case DepositInfo -> {
                eventManager.subscribe("DepositInfo", client.getMessageHandler());
            }
            case CreditInfo -> {
                eventManager.subscribe("CreditInfo", client.getMessageHandler());
            }
            case Remittance -> {
                eventManager.subscribe("RemittanceInfo", client.getMessageHandler());
            }
            case Withdrawal -> {
                eventManager.subscribe("WithdrawalInfo", client.getMessageHandler());
            }
        }
    }
    /**
     * Recounting interests to all {@link BankAccount}s
     */
    public void recountInterests()
    {
        for (BankAccount listOfBankAccount : listOfBankAccounts) {
            if (listOfBankAccount instanceof DebitAccount debitAccount) {
                debitAccount.getInterestsCounter().increase(
                        bankConfiguration.getDebitAccountInfo().getInterestsPerDay(),
                        Math.abs(debitAccount.getFund()));
            }

            if (listOfBankAccount instanceof DepositAccount depositAccount) {
                if (!depositAccount.isOpened()) {
                    var interests = bankConfiguration.getDepositAccountInfo().getInterestsPerDay().stream()
                            .filter(entry -> entry.getElem1().getSum().doubleValue() > depositAccount.getFund())
                            .findFirst().orElse(null);

                    if (interests == null) {
                        depositAccount.getInterestsCounter()
                                .increase(bankConfiguration.getDepositAccountInfo()
                                                .getInterestsPerDay()
                                                .get(bankConfiguration.getDepositAccountInfo()
                                                        .getInterestsPerDay().size() - 1).getElem2(),
                                        Math.abs(depositAccount.getFund()));
                    }

                    if (interests != null) {
                        depositAccount.getInterestsCounter().increase(interests.getElem2(), Math.abs(depositAccount.getFund()));
                    }
                }
            }
        }
    }

    /**
     * Paying interests to all {@link BankAccount}s
     * @throws BankException
     */
    public void payInterests() throws BankException {
        for (BankAccount listOfBankAccount : listOfBankAccounts) {
            if (listOfBankAccount instanceof InterestsContaining interestsContainingAccount) {
                listOfBankAccount.SpecificBankReplenishment(new Money(new BigDecimal(interestsContainingAccount.getInterestsCounter()
                        .getInterests())));
                interestsContainingAccount.getInterestsCounter().reset();
            }
        }
    }

    /**
     * Check of all {@link TermableAccount}s if the term is ended
     * @param curDateTime
     */
    public void checkTermableAccounts(@NotNull LocalDate curDateTime)
    {
        var termEndedAccounts = listOfBankAccounts.stream()
                .filter(entry -> entry instanceof TermableAccount termableAccount
                        && ((termableAccount.getStart().plus(termableAccount.getTerm())).isBefore(curDateTime)
                        || (termableAccount.getStart().plus(termableAccount.getTerm())).isEqual(curDateTime))).toList();

        for (BankAccount termEndedAccount : termEndedAccounts) {
            if (termEndedAccount instanceof TermableAccount termableAccount) {
                termableAccount.open();
            }
        }
    }

    /**
     * Changing of {@link DebitAccountInfo}
     * @param percent
     * @throws BankException
     */
    public void changeDebitAccountInfo(@NotNull Percent percent) throws BankException {
        bankConfiguration.setDebitAccountInfo(new DebitAccountInfo(percent));
        eventManager.notify("DebitInfo", "Bank {Name} changed interests for debit accounts to {percent} per year");
    }

    /**
     * Changing of {@link DepositAccountInfo}
     * @param interests
     * @throws BankException
     */
    public void changeDepositAccountInfo(@NotNull ArrayList<Pair<Money, Percent>> interests) throws BankException {
        interests.sort(new Comparator<Pair<Money, Percent>>() {
            @Override
            public int compare(Pair<Money, Percent> o1, Pair<Money, Percent> o2) {
                return o1.getElem1().compareTo(o2.getElem1());
            }
        });

        bankConfiguration.setDepositAccountInfo(new DepositAccountInfo(interests));
        eventManager.notify("DepositInfo","Bank {Name} changed interests for deposit accounts to {interests[0].Item2} per year");
    }

    /**
     * Changing of {@link CreditAccountInfo}
     * @param commission
     * @param limit
     * @throws BankException
     */
    public void changeCreditAccountInfo(@NotNull Percent commission, @NotNull Money limit) throws BankException {
        bankConfiguration.setCreditAccountInfo(new CreditAccountInfo(commission, limit));
        eventManager.notify("CreditInfo","Bank {Name} changed commission for credit accounts to {commission} per year");
    }

    /**
     * Changing of remittance commission
     * @param commission
     */
    public void changeRemittanceCommission(@NotNull Percent commission)
    {
        bankConfiguration.setAnotherBankRemittanceCommission(commission);
        eventManager.notify("RemittanceInfo","Bank {Name} changed commission for remittance to another banks to {commission}");
    }

    /**
     * Changing of withdrawal commission
     * @param commission
     * @param limit
     */
    public void changeWithdrawalCommission(@NotNull Percent commission, @NotNull Money limit)
    {
        bankConfiguration.setWithdrawalCommission(new Pair<>(commission, limit));
        eventManager.notify("WithdrawalInfo", "Bank {Name} changed commission for withdrawal to {commission} if sum is more than {limit}");
    }

    /**
     * Changing of limit for precarious transactions
     * @param limit
     * @throws BankException
     */
    public void changeLimitForPrecariousTransactions(@NotNull Money limit) throws BankException {
        bankConfiguration.setLimitForPrecariousTransactions(limit);
    }

    /**
     * Check if {@link BankAccount} exists
     * @param id
     * @return true if {@link BankAccount} exists
     */
    private boolean accountExists(@NotNull UUID id) {
        return listOfBankAccounts.stream().anyMatch(entry -> entry.getId() == id);
    }

    /**
     * Find {@link BankAccount} by id
     * @param id
     * @return {@link BankAccount}
     */
    private BankAccount findAccount(@NotNull UUID id) {
        return listOfBankAccounts.stream().filter(entry -> entry.getId() == id).findFirst().orElse(null);
    }

}
