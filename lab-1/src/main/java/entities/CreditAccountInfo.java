package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import value_objects.Money;
import value_objects.Percent;

/**
 * {@link Bank}'s info for {@link CreditAccount}s
 */
@Data
@AllArgsConstructor
public class CreditAccountInfo {
    private Percent commission;
    private Money limit;
}
