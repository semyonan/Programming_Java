package value_objects;

import exceptions.BankValidationException;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * Represents passport data
 */
@Data
public class RussianPassportNumber {
    private final static int MAX_PASSPORT_NUMBER = 999999;
    private final static int MIN_PASSPORT_NUMBER = 100000;
    private final static int MAX_SERIES = 9999;
    private final static int MIN_SERIES = 1000;

    private int series;
    private int number;

    public RussianPassportNumber(@NotNull int series, @NotNull int number) throws BankValidationException {
        if ((series < MIN_SERIES)
                || (series > MAX_SERIES)
                || (number < MIN_PASSPORT_NUMBER)
                || (number > MAX_PASSPORT_NUMBER))
        {
            throw new BankValidationException("Invalid Passport Number arguments");
        }

        this.series = series;
        this.number = number;
    }
}
