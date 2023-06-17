package models;

import exceptions.BankValidationException;
import value_objects.Address;
import entities.Client;
import org.jetbrains.annotations.NotNull;
import value_objects.RussianPassportNumber;

/**
 * Builder for {@link Client}
 */
public class ClientBuilder {
    private String name;
    private String serName;
    private Address address;
    private RussianPassportNumber passportNumber;
    private MessageHandler messageHandler;

    public ClientBuilder()
    {
    }

    public ClientBuilder setName(@NotNull String name) throws BankValidationException {
        if (name.isBlank() || name.isEmpty())
        {
            throw new BankValidationException("Name can't be empty");
        }

        this.name = name;

        return this;
    }

    public ClientBuilder setSerName(@NotNull String serName) throws BankValidationException {
        if (serName.isBlank() || serName.isEmpty())
        {
            throw new BankValidationException("SerName can't be empty");
        }

        this.serName = serName;

        return this;
    }

    public ClientBuilder setAddress(Address address)
    {
        this.address = address;

        return this;
    }

    public ClientBuilder setPassportDate(RussianPassportNumber passportNumber)
    {
        this.passportNumber = passportNumber;

        return this;
    }

    public ClientBuilder setMassageHandler(MessageHandler massageHandler) {

        this.messageHandler = massageHandler;

        return this;
    }

    /**
     * Build instance of {@link Client}
     * @return {@link Client}
     * @throws BankValidationException
     */
    public Client build() throws BankValidationException {
        if ((name == null) || (serName == null))
        {
            throw new BankValidationException("Name and SerName must be initialized");
        }

        return new Client(name, serName, address, passportNumber, messageHandler);
    }
}
