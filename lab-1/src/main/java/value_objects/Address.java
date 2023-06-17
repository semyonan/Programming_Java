package value_objects;

import entities.Client;
import exceptions.BankValidationException;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * Represents address of {@link Client}
 */
@Data
public class Address {
    private final static int MAX_NUMBER_OF_ADDRESS_COMPONENTS = 5;
    private final static int MIN_NUMBER_OF_ADDRESS_COMPONENTS = 4;
    private final static int MAX_INDEX_NUMBER = 999999;
    private final static int MIN_INDEX_NUMBER = 100000;

    private int index;
    private String city;
    private String street;
    private String building;
    private String etc;

    public Address(@NotNull String address) throws BankValidationException {
        String[] subs = address.split(",");
        if ((subs.length < MIN_NUMBER_OF_ADDRESS_COMPONENTS)
                || (subs.length > MAX_NUMBER_OF_ADDRESS_COMPONENTS)
                || Integer.parseInt(subs[0]) > MAX_INDEX_NUMBER
                || Integer.parseInt(subs[0]) < MIN_INDEX_NUMBER
                || subs[1].isEmpty()
                || subs[1].isBlank()
                || subs[2].isEmpty()
                || subs[2].isBlank()
                || subs[3].isEmpty()
                || subs[3].isBlank()
                || ((subs.length == MAX_NUMBER_OF_ADDRESS_COMPONENTS) && (subs[4].isEmpty()
                || subs[4].isBlank())))
        {
            throw new BankValidationException("Invalid address arguments");
        }

        this.index = Integer.parseInt(subs[0]);
        this.city = subs[1];
        this.street = subs[2];
        this.building = subs[3];
        if (subs.length == MAX_NUMBER_OF_ADDRESS_COMPONENTS)
        {
            this.etc = subs[4];
        }
    }
}
