package entities;

import exceptions.BankException;
import exceptions.TimeMachineException;
import models.TimeMachine;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * {@link TimeMachine} that can rewind time
 */
public class RewindTimeMachine extends TimeMachine {
    public RewindTimeMachine() {
        super();
    }

    /**
     * Method rewinding time for the specified period
     * @param years
     * @param months
     * @param days
     * @throws TimeMachineException
     */
    public void rewindTime(@NotNull int years, @NotNull int months, @NotNull int days) throws BankException {

        if (getObserver() == null)
        {
            throw new TimeMachineException("Observer must be set");
        }

        if ((days < 0)
                || (months < 0)
                || (years < 0))
        {
            throw new TimeMachineException("Invalid arguments for rewind time operation");
        }

        var newDateTime = getCurDateTime().plusDays(days).plusMonths(months).plusYears(years);

        var duration = Period.between(getCurDateTime(), newDateTime);

        for (int i = 0; i < duration.getDays(); i++)
        {
            setCurDateTime(getCurDateTime().plusDays(1));

            if (getCurDateTime().getDayOfMonth() == getStart().getDayOfMonth())
            {
                getObserver().payInterests();
            }

            getObserver().checkTermableAccounts(getCurDateTime());

            getObserver().recountInterests();
        }
    }
}
