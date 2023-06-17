package models;

import entities.CentralBank;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Keeps track of time and reminds the {@link CentralBank} of events
 */
@Data
public abstract class TimeMachine {

    private LocalDate start;
    private LocalDate curDateTime;
    private CentralBank observer;

    public TimeMachine() {
        this.start = LocalDate.now();
        this.curDateTime = LocalDate.now();
    }
}
