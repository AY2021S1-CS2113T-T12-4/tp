package seedu.duke.event;

import java.time.LocalDate;
import java.time.LocalTime;

public class Personal extends Event {
    protected Boolean hasDate;
    protected Boolean hasTime;

    public Personal(String description, LocalDate date, LocalTime time, int repeatCount) {
        super(description);
        setDate(date);
        setTime(time);
        hasDate = true;
        hasTime = true;
    }

    public Personal(String description, LocalDate date, LocalTime time) {
        this(description, date, time, 0);
        hasDate = true;
        hasTime = true;
    }

    public Personal(String description, LocalDate date) {
        this(description, date, null, 0);
        hasDate = true;
        hasTime = false;
    }

    public Personal(String description) {
        this(description, null, null);
        hasDate = false;
        hasTime = false;
    }

    public void setHasDate(Boolean hasDate) {
        this.hasDate = hasDate;
    }

    public void setHasTime(Boolean hasTime) {
        this.hasTime = hasTime;
    }

    @Override
    public Personal clone() throws CloneNotSupportedException {
        return (Personal) super.clone();
    }

    @Override
    public String toString() {
        if (hasTime && hasDate) {
            return "[P]" + super.toString() + " on " + date + ", " + time;
        } else if (!hasTime && hasDate) {
            return "[P]" + super.toString() + " on " + date;
        } else {
            return "[P]" + super.toString();
        }
    }

    @Override
    public String toCalendarString() {
        return "P | " + super.toCalendarString();
    }
}
