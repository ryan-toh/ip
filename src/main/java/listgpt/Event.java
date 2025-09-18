package listgpt;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Represents a event task with a start and end date.
 * Dates are provided as ISO-8601 strings and stored as a LocalDate.
 */
public class Event extends Task {

    /** Start date of the event. */
    private LocalDate startDate;

    /** End date of the event. */
    private LocalDate endDate;

    /**
     * Constructs an Event with the given name, start date, and end date.
     *
     *
     * @param name the task name.
     * @param startDate the start date string in yyyy-MM-dd format.
     * @param endDate the end date string in yyyy-MM-dd format.
     * @throws DateTimeException if either date string is not in the required format or
     *                           if the parsed year/month/day values are invalid.
     */
    public Event(String name, String startDate, String endDate) throws DateTimeException {
        super(name);
        String[] startParams = startDate.split("-");
        String[] endParams = endDate.split("-");

        if (startParams.length != 3 || endParams.length != 3) {
            throw new DateTimeException("Invalid date format.");
        }

        this.startDate = LocalDate.of(
                Integer.parseInt(startParams[0]),
                Integer.parseInt(startParams[1]),
                Integer.parseInt(startParams[2]));
        this.endDate = LocalDate.of(
                Integer.parseInt(endParams[0]),
                Integer.parseInt(endParams[1]),
                Integer.parseInt(endParams[2]));
    }

    /**
     * Constructs an Event with the given name, start date, and end date.
     *
     *
     * @param name the task name.
     * @param startDate the start date string in yyyy-MM-dd format.
     * @param endDate the end date string in yyyy-MM-dd format.
     * @param tag the tag assigned to the Event.
     * @throws DateTimeException if either date string is not in the required format or
     *                           if the parsed year/month/day values are invalid.
     */
    public Event(String name, String startDate, String endDate, String tag) throws DateTimeException {
        super(name, tag);
        String[] startParams = startDate.split("-");
        String[] endParams = endDate.split("-");

        if (startParams.length != 3 || endParams.length != 3) {
            throw new DateTimeException("Invalid date format.");
        }

        this.startDate = LocalDate.of(
                Integer.parseInt(startParams[0]),
                Integer.parseInt(startParams[1]),
                Integer.parseInt(startParams[2]));
        this.endDate = LocalDate.of(
                Integer.parseInt(endParams[0]),
                Integer.parseInt(endParams[1]),
                Integer.parseInt(endParams[2]));
    }

    /**
     * Returns the start date in ISO-8601 format.
     *
     * @return the start date string.
     */
    public String getFrom() {
        return this.startDate.toString();
    }

    /**
     * Returns the end date in ISO-8601 format.
     *
     * @return the end date string.
     */
    public String getTo() {
        return this.endDate.toString();
    }

    /**
     * Returns the display string for this event task.
     * The format is "[E]<TaskString> (from: yyyy-MM-dd to:yyyy-MM-dd)".
     *
     * @return a string representation of this event.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.getFrom() + " to: " + this.getTo() + ")";
    }
}
