package entities;

import exceptions.BankException;
import exceptions.TransactionException;
import models.BankAccount;
import models.Transaction;
import org.jetbrains.annotations.NotNull;
import value_objects.Money;

/**
 * Withdrawal implementation of {@link Transaction}
 */
public class WithdrawalTransaction extends Transaction {
    private BankAccount bankAccount;

    public WithdrawalTransaction(@NotNull BankAccount bankAccount, @NotNull Money sum)
    {
        super(sum);
        this.bankAccount = bankAccount;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    /**
     * Method cancel withdrawal transaction in {@link CentralBank}
     * @param  centralBank
     * @throws BankException
     */
    @Override
    public void cancel(@NotNull CentralBank centralBank) throws BankException {
        centralBank.specialReplenishment(bankAccount.getId(), getSum());
    }
}
