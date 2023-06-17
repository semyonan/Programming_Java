package entities;

import exceptions.BankValidationException;
import org.jetbrains.annotations.NotNull;
import value_objects.Percent;

/**
 * {@link Bank}'s info for {@link DebitAccount}s
 */
public class DebitAccountInfo {
    private Percent interestsPerDay;

    public DebitAccountInfo(@NotNull Percent interests) throws BankValidationException {
        this.interestsPerDay = interests.del(365);
    }

    public Percent getInterestsPerDay() { return interestsPerDay; }

    public void setInterests(@NotNull Percent newInterests) throws Exception {
        interestsPerDay = newInterests.del(365);
    }
}
