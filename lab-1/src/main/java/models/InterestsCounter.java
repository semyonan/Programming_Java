package models;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import value_objects.Percent;

/**
 * Class that calculates interest for the {@link BankAccount}
 */
@Data
public class InterestsCounter {

    private double interests;
    public InterestsCounter()
    {
        this.interests = 0;
    }
    /**
     * Increasing interests
     * @param percent
     * @param currentFund
     */
    public void increase(@NotNull Percent percent, @NotNull double currentFund)
    {
        interests += percent.multiplication(currentFund);
    }

    /**
     * Reset interests value
     */
    public void reset()
    {
        interests = 0;
    }
}
