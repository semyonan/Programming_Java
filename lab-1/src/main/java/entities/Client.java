package entities;

import exceptions.BankValidationException;
import lombok.Data;
import models.BankAccount;
import models.EmptyMessageHandler;
import models.MessageHandler;
import org.jetbrains.annotations.NotNull;
import value_objects.Address;
import value_objects.RussianPassportNumber;

import java.util.*;

/**
 * Client that is owner of {@link BankAccount}
 */
@Data
public class Client {
    private ArrayList<BankAccount> listOfAccounts;
    private String name;
    private String serName;
    private Address address;
    private RussianPassportNumber passportNumber;
    public MessageHandler messageHandler;
    private UUID id;

    public Client(
            @NotNull String name,
            @NotNull String serName,
            Address address,
            RussianPassportNumber passportNumber,
            MessageHandler messageHandler) throws BankValidationException {
        if (name.isBlank() || name.isEmpty()
                || serName.isBlank() || serName.isEmpty())
        {
            throw new BankValidationException("Invalid client arguments");
        }

        this.name = name;
        this.serName = serName;
        this.address = address;
        this.passportNumber = passportNumber;

        this.messageHandler = Objects.requireNonNullElseGet(messageHandler, EmptyMessageHandler::new);

        this.id = UUID.randomUUID();
        listOfAccounts = new ArrayList<BankAccount>();
    }

    public List<BankAccount> getListOfAccounts(){
        return Collections.unmodifiableList(listOfAccounts);
    }

    public void setPassportNumber(@NotNull RussianPassportNumber passportNumber) throws BankValidationException {
        if (this.passportNumber != null)
        {
            throw new BankValidationException("You can't change passport number twice");
        }

        this.passportNumber = passportNumber;
    }

    /**
     * Add some {@link BankAccount} to the {@link Client}
     * @param bankAccount
     */
    public void addAccount(@NotNull BankAccount bankAccount)
    {
        listOfAccounts.add(bankAccount);
    }
}
