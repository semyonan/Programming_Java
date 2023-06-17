package models;

import entities.Bank;
import entities.Client;
import exceptions.TransactionException;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import value_objects.Money;

import java.util.UUID;

/**
 * {@link Client}'s account for money transactions
 */
@Data
public abstract class BankAccount {
    private UUID id;
    private Client owner;
    private Bank bank;
    private double fund;

    public BankAccount(@NotNull Client owner, @NotNull Bank bank) {
        this.id = UUID.randomUUID();
        this.owner = owner;
        this.bank = bank;
        this.fund = 0;
    }

    /**
     * Withdrawal operation
     * @param value
     * @throws TransactionException
     */
    public abstract void Withdrawal(@NotNull Money value) throws TransactionException;

    /**
     * Replenishment operation
     * @param value
     * @throws TransactionException
     */
    public abstract void Replenishment(@NotNull Money value) throws TransactionException;

    /**
     * Sending Remittance
     * @param value
     * @throws TransactionException
     */
    public abstract void SendRemittance(@NotNull Money value) throws TransactionException;

    /**
     * Receive Remittance
     * @param value
     * @throws TransactionException
     */
    public abstract void ReceiveRemittance(@NotNull Money value) throws TransactionException;

    /**
     * Specific Bank Withdrawal
     * @param value
     */
    public abstract void SpecificBankWithdrawal(@NotNull Money value);

    /**
     * Specific Bank Replenishment
     * @param value
     */
    public abstract void SpecificBankReplenishment(@NotNull Money value);
}
