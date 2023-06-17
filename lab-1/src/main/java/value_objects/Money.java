package value_objects;

import exceptions.BankValidationException;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * Represents the amount of money
 */
@Data
public class Money implements Comparable<Money>
{
    private BigDecimal sum;

    public Money(@NotNull BigDecimal sum) {
        this.sum = sum;
    }

    @Override
    public int compareTo(@NotNull Money o) {
        return this.sum.compareTo(o.sum);
    }

}
