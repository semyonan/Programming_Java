package entities;

import exceptions.TransactionException;
import models.BankAccount;
import org.jetbrains.annotations.NotNull;
import value_objects.Money;

/**
 * Credit implementation of {@link BankAccount}
 */
public class CreditAccount extends BankAccount {
    private Money limit;

    public CreditAccount(@NotNull Client owner, @NotNull Bank bank, @NotNull Money limit){
        super(owner, bank);
        this.limit = limit;
    }

    public Money getLimit() {
        return limit;
    }

    /**
     * Withdrawal operation
     * @param value
     * @throws TransactionException
     */
    @Override
    public void Withdrawal(@NotNull Money value) throws TransactionException {
        if (value.getSum().doubleValue() - super.getFund() < -limit.getSum().doubleValue())
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
        if (value.getSum().doubleValue() - getFund() < -limit.getSum().doubleValue())
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
