package entities;

import exceptions.TransactionException;
import models.BankAccount;
import models.InterestsContaining;
import models.InterestsCounter;
import org.jetbrains.annotations.NotNull;
import value_objects.Money;

/**
 * Debit implementation of {@link BankAccount}
 */
public class DebitAccount extends BankAccount implements InterestsContaining {

    private InterestsCounter interestsCounter;

    public DebitAccount(@NotNull Client owner, @NotNull Bank bank) {
        super(owner, bank);
        this.interestsCounter = new InterestsCounter();
    }

    public InterestsCounter getInterestsCounter() {
        return interestsCounter;
    }

    /**
     * Withdrawal operation
     * @param value
     * @throws TransactionException
     */
    @Override
    public void Withdrawal(@NotNull Money value) throws TransactionException {
        if (getFund() < value.getSum().doubleValue())
        {
            throw new TransactionException("Insufficient funds");
        }

        super.setFund(getFund() - value.getSum().doubleValue());
    }

    /**
     * Replenishment operation
     * @param value
     */
    @Override
    public void Replenishment(@NotNull Money value) {
        super.setFund(getFund() + value.getSum().doubleValue());
    }

    /**
     * Sending Remittance
     * @param value
     * @throws TransactionException
     */
    @Override
    public void SendRemittance(@NotNull Money value) throws TransactionException {
        if (getFund() < value.getSum().doubleValue())
        {
            throw new TransactionException("Insufficient funds");
        }

        super.setFund(getFund() - value.getSum().doubleValue());
    }

    /**
     * Receive Remittance
     * @param value
     */
    @Override
    public void ReceiveRemittance(@NotNull Money value) {
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
