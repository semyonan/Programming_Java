package entities;

import exceptions.BankException;
import exceptions.TransactionException;
import models.BankAccount;
import models.Transaction;
import org.jetbrains.annotations.NotNull;
import value_objects.Money;

/**
 * Remittance implementation of {@link Transaction}
 */
public class RemittanceTransaction extends Transaction {
    private BankAccount senderAccount;
    private BankAccount receiverAccount;

    public RemittanceTransaction(@NotNull BankAccount senderAccount, @NotNull BankAccount receiverAccount, @NotNull Money sum)
    {
        super(sum);
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
    }

    public BankAccount getSenderAccount() { return senderAccount; }
    public BankAccount getReceiverAccount() { return receiverAccount; }

    /**
     * Method cancel remittance transaction in {@link CentralBank}
     * @param  centralBank
     * @throws BankException
     */
    @Override
    public void cancel(@NotNull CentralBank centralBank) throws BankException {
        centralBank.specialWithdrawal(receiverAccount.getId(), getSum());
        centralBank.specialReplenishment(senderAccount.getId(), getSum());
    }
}
