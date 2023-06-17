package models;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

/**
 * Interface for {@link BankAccount} with expiration dates
 */
public interface TermableAccount {

    public LocalDate getStart();

    public Duration getTerm();

    public boolean isOpened();

    /**
     * Open when term ended
     */
    public void open();
}
