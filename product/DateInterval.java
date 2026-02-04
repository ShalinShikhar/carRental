package product;

import java.time.LocalDate;

public class DateInterval {
    private final LocalDate from;
    private final LocalDate to;

    public DateInterval(LocalDate from, LocalDate to) {
        if(to.isBefore(from))
        {
            throw new IllegalArgumentException("End date cannot be before starting date");
        }
        this.from = from;
        this.to = to;
    }

    public LocalDate getTo() {
        return to;
    }

    public LocalDate getFrom() {
        return from;
    }

    public boolean overlaps(DateInterval other)
    {
        return !(this.to.isBefore(other.from) || this.from.isAfter(other.to));
    }
}
