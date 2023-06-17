package models;

import entities.Bank;
import entities.CentralBank;
import exceptions.BankException;
import exceptions.BankLogicException;
import exceptions.TransactionException;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import value_objects.Money;

import java.util.UUID;

/**
 * Represents information about a monetary transaction
 */
@Data
public abstract class Transaction {
    private UUID id;
    private Money sum;

    public Transaction(@NotNull Money sum) {
        this.id = UUID.randomUUID();
        this.sum = sum;
    }
    /**
     * Method cancel transaction in {@link CentralBank}
     * @param  centralBank
     * @throws BankException
     */
    public abstract void cancel(@NotNull CentralBank centralBank) throws BankException;
}
