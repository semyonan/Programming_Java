package entities;

import exceptions.BankValidationException;
import org.jetbrains.annotations.NotNull;
import value_objects.Money;
import value_objects.Pair;
import value_objects.Percent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * {@link Bank}'s info for {@link DepositAccount}s
 */
public class DepositAccountInfo {
    private ArrayList<Pair<Money, Percent>> interestsPerDay;

    public DepositAccountInfo(@NotNull ArrayList<Pair<Money, Percent>> interests) throws BankValidationException {
        if (!interests.get(0).getElem1().equals(new Money(new BigDecimal(0))))
        {
            throw new BankValidationException("Invalid deposit info arguments");
        }

        interestsPerDay = new ArrayList<Pair<Money, Percent>>();

        for (Pair<Money, Percent> interest : interests) {
            interestsPerDay.add(new Pair<Money, Percent>(interest.getElem1(), interest.getElem2().del(365)));
        }

        interestsPerDay.sort(new Comparator<Pair<Money, Percent>>() {
            @Override
            public int compare(Pair<Money, Percent> o1, Pair<Money, Percent> o2) {
                return o1.getElem1().compareTo(o2.getElem1());
            }
        });
    }

    public List<Pair<Money, Percent>> getInterestsPerDay() {
        return Collections.unmodifiableList(interestsPerDay);
    }

    public void setInterests(@NotNull List<Pair<Money, Percent>> interests)
    {
        interestsPerDay.clear();
        interestsPerDay.addAll(interests);
    }
}
