package models;

import entities.Bank;
import entities.CreditAccountInfo;
import entities.DebitAccountInfo;
import entities.DepositAccountInfo;
import exceptions.BankValidationException;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import value_objects.Money;
import value_objects.Pair;
import value_objects.Percent;

import java.math.BigDecimal;

/**
 * Configuration for all {@link Bank}
 */
@Data
public class BankConfiguration
{
    private CreditAccountInfo creditAccountInfo;
    private DepositAccountInfo depositAccountInfo;
    private DebitAccountInfo debitAccountInfo;
    private Money limitForPrecariousTransactions;
    private Percent anotherBankRemittanceCommission;
    private Pair<Percent, Money> withdrawalCommission;

    public BankConfiguration(
            @NotNull DepositAccountInfo depositAccountInfo,
            @NotNull DebitAccountInfo debitAccountInfo,
            @NotNull CreditAccountInfo creditAccountInfo,
            Money limitForPrecariousTransactions,
            Percent anotherBankRemittanceCommission,
            Pair<Percent, Money> withdrawalCommission) throws BankValidationException {


        if (limitForPrecariousTransactions == null)
        {
            limitForPrecariousTransactions = new Money(new BigDecimal(0));
        }

        this.depositAccountInfo = depositAccountInfo;
        this.debitAccountInfo = debitAccountInfo;
        this.creditAccountInfo = creditAccountInfo;
        this.limitForPrecariousTransactions = limitForPrecariousTransactions;
        this.anotherBankRemittanceCommission = anotherBankRemittanceCommission;
        this.withdrawalCommission = withdrawalCommission;
    }

}
