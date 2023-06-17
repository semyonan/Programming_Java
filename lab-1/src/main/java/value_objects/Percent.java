package value_objects;

import exceptions.BankLogicException;
import exceptions.BankValidationException;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * Represents percent value
 */
@Data
public class Percent {
    private final static double MAX_PERCENT_VALUE = 100;
    private final static double MIN_PERCENT_VALUE = 0;
    private double value;

    public Percent(@NotNull double value) throws BankValidationException {
        if (value <= MIN_PERCENT_VALUE || value >= MAX_PERCENT_VALUE) {
            throw new BankValidationException("Invalid percent arguments");
        }

        this.value = value / 100;
    }

    /**
     * Division operation
     * @param del
     * @return {@link Percent}
     * @throws BankValidationException
     */
    public Percent del(@NotNull int del) throws BankValidationException {
        if (del <= 0) {
            throw new BankValidationException("Del must be more than a zero");
        }

        return new Percent(value * 100 / del);
    }

    /**
     * Multiplication of {@link Percent} and double
     * @param multiplier
     * @return result of multiplication
     */
    public double multiplication(@NotNull double multiplier) {
        return value * multiplier;
    }

    /**
     * Create {value}% string
     * @return {@link String}
     */
    @Override
    public String toString() {
        return value + "%";
    }
}

