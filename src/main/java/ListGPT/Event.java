package ListGPT;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Event extends Task {
    LocalDate startDate;
    LocalDate endDate;

    public Event(String name, String startDate, String endDate) throws DateTimeException {
        super(name);
        String[] startParams = startDate.split("-");
        String[] endParams = endDate.split("-");

        if (startParams.length != 3 || endParams.length != 3) {
            throw new DateTimeException("Invalid date format.");
        }

        this.startDate = LocalDate.of(Integer.parseInt(startParams[0]), Integer.parseInt(startParams[1]),
                Integer.parseInt(startParams[2]));
        this.endDate = LocalDate.of(Integer.parseInt(endParams[0]), Integer.parseInt(endParams[1]),
                Integer.parseInt(endParams[2]));
    }

    public String getFrom() {
        return this.startDate.toString();
    }

    public String getTo() {
        return this.endDate.toString();
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.getFrom() + " to:" + this.getTo() + ")";
    }


}
