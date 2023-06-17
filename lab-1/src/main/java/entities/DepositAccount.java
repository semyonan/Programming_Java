package entities;

import exceptions.BankValidationException;
import exceptions.TransactionException;
import models.BankAccount;
import models.InterestsContaining;
import models.InterestsCounter;
import models.TermableAccount;
import org.jetbrains.annotations.NotNull;
import value_objects.Money;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

/**
 * Deposit implementation of {@link BankAccount}
 */
public class DepositAccount extends BankAccount implements TermableAccount, InterestsContaining {
    private InterestsCounter interestsCounter;
    public LocalDate start;
    public Duration term;
    public boolean opened;

    public DepositAccount(@NotNull Client owner, @NotNull Bank bank, @NotNull LocalDate start, @NotNull Duration term) {
        super(owner, bank);
        this.start = start;
        this.term = term;
        this.opened = false;
        this.interestsCounter = new InterestsCounter();
    }
    public InterestsCounter getInterestsCounter() {
        return interestsCounter;
    }

    public LocalDate getStart() {
        return start;
    }

    public Duration getTerm() {
        return term;
    }

    public boolean isOpened() {
        return opened;
    }

    /**
     * Open when term ended
     */
    public void open() {
        opened = true;
    }

    /**
     * Withdrawal operation
     * @param value
     * @throws TransactionException
     */
    @Override
    public void Withdrawal(@NotNull Money value) throws TransactionException {

        if (!opened)
        {
            throw new TransactionException("Deposit term not ended yet");
        }

        if (getFund() < value.getSum().doubleValue())
        {
            throw new TransactionException("Insufficient funds");
        }

        super.setFund(getFund() - value.getSum().doubleValue());
    }

    /**
     * Replenishment operation
     * @param value
     * @throws TransactionException
     */
    @Override
    public void Replenishment(@NotNull Money value) throws TransactionException {

        if (opened)
        {
            throw new TransactionException("Deposit term ended yet");
        }

        super.setFund(getFund() + value.getSum().doubleValue());
    }

    /**
     * Sending Remittance
     * @param value
     * @throws TransactionException
     */
    @Override
    public void SendRemittance(@NotNull Money value) throws TransactionException {

        if (!opened)
        {
            throw new TransactionException("Deposit term not ended yet");
        }

        if (getFund() < value.getSum().doubleValue())
        {
            throw new TransactionException("Insufficient funds");
        }

        super.setFund(getFund() - value.getSum().doubleValue());
    }

    /**
     * Receive Remittance
     * @param value
     * @throws TransactionException
     */
    @Override
    public void ReceiveRemittance(@NotNull Money value) throws TransactionException {

        if (opened)
        {
            throw new TransactionException("Deposit term ended yet");
        }

        super.setFund(getFund() + value.getSum().doubleValue());
    }

    /**
     * Specific Bank Withdrawal
     * @param value
     */
    @Override
    public void SpecificBankWithdrawal(@NotNull Money value) {
        super.setFund(getFund() - value.getSum().doubleValue());
    }

    /**
     * Specific Bank Replenishment
     * @param value
     */
    @Override
    public void SpecificBankReplenishment(@NotNull Money value) {
        super.setFund(getFund() + value.getSum().doubleValue());
    }
}
