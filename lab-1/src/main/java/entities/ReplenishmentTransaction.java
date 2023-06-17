package entities;

import exceptions.BankException;
import exceptions.TransactionException;
import models.BankAccount;
import models.Transaction;
import org.jetbrains.annotations.NotNull;
import value_objects.Money;

/**
 * Replenishment implementation of {@link Transaction}
 */
public class ReplenishmentTransaction extends Transaction {
    private BankAccount bankAccount;

    public ReplenishmentTransaction(@NotNull BankAccount bankAccount, @NotNull Money sum)
    {
        super(sum);
        this.bankAccount = bankAccount;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    /**
     * Method cancel replenishment transaction in {@link CentralBank}
     * @param  centralBank
     * @throws BankException
     */
    @Override
    public void cancel(@NotNull CentralBank centralBank) throws BankException {
        centralBank.specialWithdrawal(bankAccount.getId(), getSum());
    }
}
