package entities;

import exceptions.BankException;
import exceptions.BankLogicException;
import exceptions.TransactionException;
import models.*;
import org.jetbrains.annotations.NotNull;
import value_objects.Address;
import value_objects.Money;
import value_objects.RussianPassportNumber;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

/**
 * Main service for all {@link Bank}'s
 */
public class CentralBank {
    private ArrayList<Bank> listOfBanks;
    private ArrayList<Client> listOfClients;
    private ArrayList<Transaction> listOfTransactions;
    private TimeMachine timeMachine;

    public CentralBank(@NotNull TimeMachine timeMachine)
    {
        listOfBanks = new ArrayList<Bank>();
        listOfTransactions = new ArrayList<Transaction>();
        listOfClients = new ArrayList<Client>();
        timeMachine.setObserver(this);
        this.timeMachine = timeMachine;
    }

    public List<Bank> getListOfBanks() {
        return Collections.unmodifiableList(listOfBanks);
    }

    public List<Transaction> getListOfTransaction() {
        return Collections.unmodifiableList(listOfTransactions);
    }

    public List<Client> getListOfClients() {
        return Collections.unmodifiableList(listOfClients);
    }

    public TimeMachine getTimeMachine() {
        return timeMachine;
    }

    /**
     * Get {@link Bank} by name
     * @param bankName
     * @return {@link Bank}
     * @throws BankLogicException
     */
    public Bank getBank(@NotNull String bankName) throws BankLogicException {
        var bank = findBank(bankName);

        if (bank == null) {
            throw new BankLogicException("Bank doesn't exists");
        }

        return bank;
    }

    /**
     * Get {@link Client} by id
     * @param id
     * @return {@link Client}
     * @throws BankLogicException
     */
    public Client getClient(@NotNull UUID id) throws BankLogicException {
        var client = findClient(id);

        if (client == null) {
            throw new BankLogicException("Client doesn't exists");
        }

        return client;
    }

    /**
     * Get {@link BankAccount} by id
     * @param id
     * @return {@link BankAccount}
     * @throws BankLogicException
     */
    public BankAccount getAccount(@NotNull UUID id) throws BankLogicException {
        var account = findAccount(id);

        if (account == null) {
            throw new BankLogicException("Account doesn't exists");
        }

        return account;
    }

    /**
     * Add new {@link Bank}
     * @param name
     * @param bankConfiguration
     * @return {@link Bank}
     * @throws BankException
     */
    public Bank addBank(
            @NotNull String name,
            @NotNull BankConfiguration bankConfiguration) throws BankException {
        if (bankExists(name))
        {
            throw new BankLogicException("Bank already exists");
        }

        var bank = new Bank(name, bankConfiguration);

        listOfBanks.add(bank);

        return bank;
    }

    /**
     * Add new {@link Client}
     * @param name
     * @param serName
     * @param address
     * @param passportNumber
     * @param messageHandler
     * @return {@link Client}'s {@link UUID}
     * @throws BankException
     */
    public UUID addClient(
            @NotNull String name,
            @NotNull String serName,
            Address address,
            RussianPassportNumber passportNumber,
            MessageHandler messageHandler) throws BankException {

        if (clientExists(passportNumber))
        {
            throw new BankLogicException("Client already exists");
        }

        var client = new ClientBuilder()
                .setName(name)
                .setSerName(serName)
                .setAddress(address)
                .setPassportDate(passportNumber)
                .setMassageHandler(messageHandler)
                .build();

        listOfClients.add(client);

        return client.getId();
    }

    /**
     * Add new {@link DebitAccount}
     * @param clientId
     * @param bankName
     * @return {@link DebitAccount}
     * @throws BankException
     */
    public UUID addDebitAccount(@NotNull UUID clientId, @NotNull String bankName) throws BankException {

        var selectedClient = getClient(clientId);
        var selectedBank = getBank(bankName);

        var account = new DebitAccount(selectedClient, selectedBank);

        selectedBank.addAccount(account);
        selectedClient.addAccount(account);

        return account.getId();
    }

    /**
     * Add new {@link DepositAccount}
     * @param clientId
     * @param bankName
     * @param term
     * @return {@link DepositAccount}
     * @throws BankException
     */
    public UUID addDepositAccount(@NotNull UUID clientId, @NotNull String bankName, @NotNull Duration term) throws BankException {

        var selectedClient = getClient(clientId);
        var selectedBank = getBank(bankName);

        var account = new DepositAccount(selectedClient, selectedBank, timeMachine.getCurDateTime(), term);
        selectedBank.addAccount(account);

        return account.getId();
    }

    /**
     * Add new {@link CreditAccount}
     * @param clientId
     * @param bankName
     * @return {@link CreditAccount}
     * @throws BankException
     */
    public UUID addCreditAccount(@NotNull UUID clientId, @NotNull String bankName) throws BankException {

        var selectedClient = getClient(clientId);
        var selectedBank = getBank(bankName);

        var account = new CreditAccount(selectedClient, selectedBank, selectedBank.getBankConfiguration().getCreditAccountInfo().getLimit());
        selectedBank.addAccount(account);

        return account.getId();
    }

    /**
     * Creating {@link WithdrawalTransaction}
     * @param accountId
     * @param sum
     * @return {@link WithdrawalTransaction}'s {@link UUID}
     * @throws BankException
     */
    public UUID withdrawTransaction(@NotNull UUID accountId, @NotNull Money sum) throws BankException {
        var selectedAccount = getAccount(accountId);

        if (!accountIsValid(selectedAccount) && (sum.getSum().compareTo(selectedAccount.getBank().getBankConfiguration().getLimitForPrecariousTransactions().getSum()) >= 0))
        {
            throw new TransactionException("Account is invalid");
        }

        double commission = 0;

        if (selectedAccount instanceof CreditAccount creditAccount
                && creditAccount.getFund() < 0)
        {
            commission += sum.getSum().doubleValue() * selectedAccount.getBank().getBankConfiguration()
                    .getCreditAccountInfo().getCommission().getValue();
        }

        if (selectedAccount.getBank().getBankConfiguration().getWithdrawalCommission() != null &&
                sum.getSum().compareTo(selectedAccount.getBank().getBankConfiguration().getWithdrawalCommission().getElem2().getSum()) >= 0)
        {
            commission += sum.getSum().doubleValue() * selectedAccount.getBank().getBankConfiguration().getWithdrawalCommission().getElem1().getValue();
        }

        selectedAccount.Withdrawal(new Money(new BigDecimal(sum.getSum().doubleValue() + commission)));

        var transaction = new WithdrawalTransaction(selectedAccount, sum);

        listOfTransactions.add(transaction);

        return transaction.getId();
    }

    /**
     * Creating {@link ReplenishmentTransaction}
     * @param accountId
     * @param sum
     * @return {@link ReplenishmentTransaction}'s {@link UUID}
     * @throws BankException
     */
    public UUID replenishmentTransaction(@NotNull UUID accountId, @NotNull Money sum) throws BankException {
        var selectedAccount = getAccount(accountId);

        selectedAccount.Replenishment(sum);

        var transaction = new ReplenishmentTransaction(selectedAccount, sum);

        listOfTransactions.add(transaction);

        return transaction.getId();
    }

    /**
     * Creating {@link RemittanceTransaction}
     * @param senderAccountId
     * @param receiverAccountId
     * @param sum
     * @return {@link RemittanceTransaction}'s {@link UUID}
     * @throws BankException
     */
    public UUID remittanceTransaction(
            @NotNull UUID senderAccountId,
            @NotNull UUID receiverAccountId,
            @NotNull Money sum) throws BankException {

        var selectedReceiverAccount = getAccount(receiverAccountId);
        var selectedSenderAccount = getAccount(senderAccountId);

        if (!accountIsValid(selectedSenderAccount) && (sum.getSum().compareTo(selectedSenderAccount.getBank()
                .getBankConfiguration().getLimitForPrecariousTransactions().getSum())>=0))
        {
            throw new TransactionException("Account is invalid");
        }

        double commission = 0;

        RemittanceTransaction transaction;

        if (selectedSenderAccount.getBank().getBankConfiguration().getAnotherBankRemittanceCommission() != null
                && !selectedSenderAccount.getBank().equals(selectedReceiverAccount.getBank()))
        {
            commission = sum.getSum().doubleValue() * selectedSenderAccount.getBank().getBankConfiguration()
                    .getAnotherBankRemittanceCommission().getValue();
        }

        selectedSenderAccount.SendRemittance(new Money(new BigDecimal(sum.getSum().doubleValue() + commission)));

        try
        {
            selectedReceiverAccount.ReceiveRemittance(sum);
        }
        catch (Exception exception)
        {
            selectedSenderAccount.SpecificBankReplenishment(new Money(new BigDecimal(sum.getSum().doubleValue() + commission)));
            throw new TransactionException("Transaction canceled");
        }

        transaction = new RemittanceTransaction(selectedSenderAccount, selectedReceiverAccount, sum);

        listOfTransactions.add(transaction);

        return transaction.getId();
    }

    /**
     * {@link Transaction}'s canceling
     * @param transactionId
     * @throws BankException
     */
    public void cancelTransaction(@NotNull UUID transactionId) throws BankException {

        var selectedTransaction = listOfTransactions.stream().filter(entry -> entry.getId()
                == transactionId).findFirst().orElse(null);

        if (selectedTransaction == null)
        {
            throw new TransactionException("Transaction doesn't exists");
        }

        selectedTransaction.cancel(this);
    }

    /**
     * Creating a special withdrawal
     * @param accountId
     * @param sum
     * @throws BankException
     */
    public void specialWithdrawal(@NotNull UUID accountId, @NotNull Money sum) throws BankException {

        var selectedAccount = getAccount(accountId);

        selectedAccount.SpecificBankWithdrawal(sum);
    }

    /**
     * Creating a special replenishment
     * @param accountId
     * @param sum
     * @throws BankException
     */
    public void specialReplenishment(@NotNull UUID accountId, @NotNull Money sum) throws BankException {

        var selectedAccount = getAccount(accountId);

        selectedAccount.SpecificBankReplenishment(sum);
    }

    /**
     * Notify {@link Bank} to recount interests
     */
    public void recountInterests()
    {
        for(int i = 0; i < listOfBanks.size(); i++)
        {
            listOfBanks.get(i).recountInterests();
        }
    }

    /**
     * Notify {@link Bank}s to pay interest
     * @throws BankException
     */
    public void payInterests() throws BankException {

        for (Bank listOfBank : listOfBanks) {
            listOfBank.payInterests();
        }
    }

    /**
     * Notify {@link Bank}s to check all of {@link TermableAccount} if their term is ended
     * @param curDateOnly
     */
    public void checkTermableAccounts(@NotNull LocalDate curDateOnly)
    {
        for (Bank listOfBank : listOfBanks) {
            listOfBank.checkTermableAccounts(curDateOnly);
        }
    }

    /**
     * Check if {@link Bank} exists
     * @param bankName
     * @return true if {@link Bank} exists
     */
    private boolean bankExists(@NotNull String bankName) {
        return listOfBanks.stream().anyMatch(entry -> entry.getName().equals(bankName));
    }

    /**
     * Check if {@link Client} exists by passport number
     * @param passportNumber
     * @return true if {@link Client} exists
     */
    private boolean clientExists(RussianPassportNumber passportNumber) {
        return (passportNumber != null) &&
                listOfClients.stream().anyMatch(
                        entry -> entry.getPassportNumber() != null && entry.getPassportNumber().equals(passportNumber));
    }

    /**
     * Check if {@link Client} exists by id
     * @param id
     * @return true if {@link Client} exists
     */
    private boolean clientExists(@NotNull UUID id) {
        return listOfClients.stream().anyMatch(entry -> entry.getId() == id);
    }

    /**
     * Find {@link Bank} by name
     * @param bankName
     * @return {@link Bank}
     */
    public Bank findBank(@NotNull String bankName) {
        return listOfBanks.stream().filter(entry -> entry.getName().equals(bankName)).findFirst().orElse(null);
    }

    /**
     * Find {@link Client} by id
     * @param id
     * @return {@link Client}
     */
    private Client findClient(@NotNull UUID id) {
        return listOfClients.stream().filter(entry -> entry.getId() == id).findFirst().orElse(null);
    }

    /**
     * Find {@link BankAccount} by id
     * @param id
     * @return {@link BankAccount}
     */
    private BankAccount findAccount(@NotNull UUID id) {
        return listOfBanks.stream().map(Bank::getListOfBankAccounts).flatMap(List::stream).toList()
                .stream().filter(entry -> entry.getId() == id).findFirst().orElse(null);
    }



    /**
     * Check if {@link BankAccount} is valid
     * @param bankAccount
     * @return true if {@link BankAccount} is valid
     */
    private boolean accountIsValid(@NotNull BankAccount bankAccount)
    {
        return (bankAccount.getOwner().getPassportNumber() != null) && (bankAccount.getOwner().getAddress() != null);
    }
}
