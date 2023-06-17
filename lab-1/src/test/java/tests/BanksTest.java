package tests;

import entities.*;
import models.*;
import value_objects.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BanksTest {
    @Test
    public void CreateClientAndMakeReplenishment() throws Exception {
        var centralBank = new CentralBank(new RewindTimeMachine());

        centralBank.addBank(
                "Сбербанк",
                new BankConfigurationBuilder()
                        .setDebitAccountInfo(new DebitAccountInfo(new Percent(10)))
                        .setDepositAccountInfo(new DepositAccountInfo(
                                new ArrayList<>(){{add(new Pair<>(new Money(new BigDecimal(0)), new Percent(10)));}}))
                        .setCreditAccountInfo(new CreditAccountInfo(new Percent(10), new Money(new BigDecimal(500000))))
                        .setLimitForPrecariousTransactions(new Money(new BigDecimal(1000)))
                        .build());

        var clientId = centralBank.addClient("Anna", "Семенова", null, null, null);

        var accountId = centralBank.addDebitAccount(clientId, "Сбербанк");

        centralBank.replenishmentTransaction(accountId, new Money(new BigDecimal(500)));

        assertEquals(500, centralBank.getClient(clientId).getListOfAccounts().get(0).getFund());
    }

    @Test
    public void RemittanceWithAnotherBankCommission() throws Exception {
        var centralBank = new CentralBank(new RewindTimeMachine());

        centralBank.addBank(
                "Сбербанк",
                new BankConfigurationBuilder()
                        .setDebitAccountInfo(new DebitAccountInfo(new Percent(10)))
                        .setDepositAccountInfo(new DepositAccountInfo(
                                new ArrayList<>(){{add(new Pair<>(new Money(new BigDecimal(0)), new Percent(10)));}}))
                        .setCreditAccountInfo(new CreditAccountInfo(new Percent(10), new Money(new BigDecimal(500000))))
                        .setLimitForPrecariousTransactions(new Money(new BigDecimal(1000)))
                        .setAnotherBankRemittanceCommission(new Percent(5))
                        .build());

        centralBank.addBank(
                "Тинькофф",
                new BankConfigurationBuilder()
                        .setDebitAccountInfo(new DebitAccountInfo(new Percent(10)))
                        .setDepositAccountInfo(new DepositAccountInfo(
                                new ArrayList<>(){{add(new Pair<>(new Money(new BigDecimal(0)), new Percent(10)));}}))
                        .setCreditAccountInfo(new CreditAccountInfo(new Percent(10), new Money(new BigDecimal(500000))))
                        .setLimitForPrecariousTransactions(new Money(new BigDecimal(1000)))
                        .build());

        var clientId = centralBank.addClient(
                "Anna",
                "Семенова",
                new Address("195426, Санкт-Петербург, ул.Ленская, д.313"),
                new RussianPassportNumber(2022, 102022), null);

        var debitAccountId1 = centralBank.addDebitAccount(clientId, "Сбербанк");
        var debitAccountId2 = centralBank.addDebitAccount(clientId, "Тинькофф");

        centralBank.replenishmentTransaction(debitAccountId1, new Money(new BigDecimal(1000)));
        centralBank.replenishmentTransaction(debitAccountId2, new Money(new BigDecimal(2000)));

        centralBank.remittanceTransaction(debitAccountId1, debitAccountId2, new Money(new BigDecimal(800)));

        assertEquals(160, centralBank.getAccount(debitAccountId1).getFund());
        assertEquals(2800, centralBank.getAccount(debitAccountId2).getFund());
    }

    @Test
    public void ChangeBankAndGetNotification() throws Exception {
        var centralBank = new CentralBank(new RewindTimeMachine());

        centralBank.addBank(
                "Сбербанк",
                new BankConfigurationBuilder()
                        .setDebitAccountInfo(new DebitAccountInfo(new Percent(10)))
                        .setDepositAccountInfo(new DepositAccountInfo(
                                new ArrayList<>(){{add(new Pair<>(new Money(new BigDecimal(0)), new Percent(10)));}}))
                        .setCreditAccountInfo(new CreditAccountInfo(new Percent(10), new Money(new BigDecimal(500000))))
                        .setLimitForPrecariousTransactions(new Money(new BigDecimal(1000)))
                        .setAnotherBankRemittanceCommission(new Percent(5))
                        .build());


        var clientId1 = centralBank.addClient(
                "Anna",
                "Семенова",
                new Address("195426, Санкт-Петербург, ул.Ленская, д.313"),
                new RussianPassportNumber(2022, 102022),
                new StorageMessageHandler());

        var clientId2 = centralBank.addClient(
                "Иван",
                "Иванов",
                new Address("195426, Санкт-Петербург, ул.Ленская, д.313"),
                new RussianPassportNumber(2021, 102022),
                new StorageMessageHandler());

        var debitAccountId1 = centralBank.addDebitAccount(clientId1, "Сбербанк");
        var debitAccountId2 = centralBank.addDebitAccount(clientId2, "Сбербанк");

        centralBank.getBank("Сбербанк").createSubscribe(clientId1, Bank.NotifyOption.DebitInfo);
        centralBank.getBank("Сбербанк").createSubscribe(clientId2, Bank.NotifyOption.Withdrawal);

        centralBank.getBank("Сбербанк").changeDebitAccountInfo(new Percent(15));
        centralBank.getBank("Сбербанк").changeWithdrawalCommission(new Percent(20), new Money(new BigDecimal(2000)));

        var handler1 = (StorageMessageHandler)centralBank.getClient(clientId1).getMessageHandler();
        var handler2 = (StorageMessageHandler)centralBank.getClient(clientId2).getMessageHandler();

        assertEquals(1, handler1.getMessages().size());
        assertEquals(1, handler2.getMessages().size());
    }

    @Test
    public void TryToWithdrawFromDepositAccountBeforeTermEnded() throws Exception {
        var centralBank = new CentralBank(new RewindTimeMachine());

        centralBank.addBank(
                "Сбербанк",
                new BankConfigurationBuilder()
                        .setDebitAccountInfo(new DebitAccountInfo(new Percent(10)))
                        .setDepositAccountInfo(new DepositAccountInfo(
                                new ArrayList<>(){{add(new Pair<>(new Money(new BigDecimal(0)), new Percent(10)));}}))
                        .setCreditAccountInfo(new CreditAccountInfo(new Percent(10), new Money(new BigDecimal(500000))))
                        .setLimitForPrecariousTransactions(new Money(new BigDecimal(1000)))
                        .setAnotherBankRemittanceCommission(new Percent(5))
                        .build());

        var clientId = centralBank.addClient(
                "Anna",
                "Семенова",
                new Address("195426, Санкт-Петербург, ул.Ленская, д.313"),
                new RussianPassportNumber(2022, 102022), null);

        var depositAccountId = centralBank.addDepositAccount(clientId, "Сбербанк", Duration.ofDays(366));

        centralBank.replenishmentTransaction(depositAccountId, new Money(new BigDecimal(500)));

        assertThrows(Exception.class, () -> centralBank.withdrawTransaction(depositAccountId, new Money(new BigDecimal(500))));
    }

    @Test
    public void RemittanceReceiverAccount_TrowsException() throws Exception {
        var centralBank = new CentralBank(new RewindTimeMachine());

        centralBank.addBank(
                "Сбербанк",
                new BankConfigurationBuilder()
                        .setDebitAccountInfo(new DebitAccountInfo(new Percent(10)))
                        .setDepositAccountInfo(new DepositAccountInfo(
                                new ArrayList<>(){{add(new Pair<>(new Money(new BigDecimal(0)), new Percent(10)));}}))
                        .setCreditAccountInfo(new CreditAccountInfo(new Percent(10), new Money(new BigDecimal(500000))))
                        .setLimitForPrecariousTransactions(new Money(new BigDecimal(1000)))
                        .setAnotherBankRemittanceCommission(new Percent(5))
                        .build());


        var clientId = centralBank.addClient(
                "Anna",
                "Семенова",
                new Address("195426, Санкт-Петербург, ул.Ленская, д.313"),
                new RussianPassportNumber(2022, 102022), null);

        var depositAccountId = centralBank.addDepositAccount(clientId, "Сбербанк", Duration.ofDays(366));
        var debitAccountId = centralBank.addDebitAccount(clientId, "Сбербанк");

        centralBank.replenishmentTransaction(debitAccountId, new Money(new BigDecimal(1000)));
        centralBank.replenishmentTransaction(depositAccountId, new Money(new BigDecimal(2000)));

        if (centralBank.getTimeMachine() instanceof RewindTimeMachine rewindTimeMachine)
        {
            rewindTimeMachine.rewindTime(1, 0, 0);
        }

        var fundBefore = centralBank.getAccount(debitAccountId).getFund();

        try
        {
            centralBank.remittanceTransaction(debitAccountId, depositAccountId, new Money(new BigDecimal(1000)));
        }
        catch (Exception e)
        {
            assertEquals(fundBefore, centralBank.getAccount(debitAccountId).getFund());
        }
    }
}