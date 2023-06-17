package models;

import entities.*;
import exceptions.BankValidationException;
import org.jetbrains.annotations.NotNull;
import value_objects.Money;
import value_objects.Pair;
import value_objects.Percent;

import java.math.BigDecimal;

/**
 * Builder for {@link BankConfiguration}
 */
public class BankConfigurationBuilder {
    private CreditAccountInfo creditAccountInfo;
    private DepositAccountInfo depositAccountInfo;
    private DebitAccountInfo debitAccountInfo;
    private Money limitForPrecariousTransactions;
    private Percent anotherBankRemittanceCommission;
    private Pair<Percent, Money> withdrawalCommission;

    public BankConfigurationBuilder()
    {
        creditAccountInfo = null;
        depositAccountInfo = null;
        debitAccountInfo = null;
        anotherBankRemittanceCommission = null;
        withdrawalCommission = null;
        limitForPrecariousTransactions = null;
    }

    public BankConfigurationBuilder setCreditAccountInfo(@NotNull CreditAccountInfo creditAccountInfo) {

        this.creditAccountInfo = creditAccountInfo;

        return this;
    }

    public BankConfigurationBuilder setDepositAccountInfo(@NotNull DepositAccountInfo depositAccountInfo) {

        this.depositAccountInfo = depositAccountInfo;

        return this;
    }

    public BankConfigurationBuilder setDebitAccountInfo(@NotNull DebitAccountInfo debitAccountInfo) {

        this.debitAccountInfo = debitAccountInfo;

        return this;
    }

    public BankConfigurationBuilder setLimitForPrecariousTransactions(@NotNull Money limit)
    {
        this.limitForPrecariousTransactions = limit;

        return this;
    }

    public BankConfigurationBuilder setAnotherBankRemittanceCommission(@NotNull Percent anotherBankRemittanceCommission)
    {
        this.anotherBankRemittanceCommission = anotherBankRemittanceCommission;

        return this;
    }

    public BankConfigurationBuilder setWithdrawalCommission(@NotNull Percent withdrawalCommission, Money withdrawalCommissionLimit)
    {
        this.withdrawalCommission = new Pair<Percent, Money>(withdrawalCommission, withdrawalCommissionLimit);

        return this;
    }

    /**
     * Build instance of {@link BankConfiguration}
     * @return {@link BankConfiguration}
     * @throws BankValidationException
     */
    public BankConfiguration build() throws BankValidationException {
        if (creditAccountInfo == null
                || debitAccountInfo == null
                || depositAccountInfo == null)
        {
            throw new BankValidationException("Credit, Debit and Deposit info must be");
        }

        if (limitForPrecariousTransactions == null)
        {
            limitForPrecariousTransactions = new Money(new BigDecimal(0));
        }

        return new BankConfiguration(
                depositAccountInfo,
                debitAccountInfo,
                creditAccountInfo,
                limitForPrecariousTransactions,
                anotherBankRemittanceCommission,
                withdrawalCommission);
    }
}
