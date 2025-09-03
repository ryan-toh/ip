package Listgpt;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Represents a deadline task with a name and a due date.
 * The due date is supplied as a string in ISO-8601 format
 * and stored internally as a LocalDate.
 */
public class Deadline extends Task {
    private LocalDate dueDate;

    /**
     * Constructs a Deadline with the given task name and due date.
     *
     * @param name the task name.
     * @param dueDate the due date string in ISO-8601 format.
     * @throws DateTimeException if the date string is not in format,
     *                           or if the year/month/day values are invalid.
     */
    public Deadline(String name, String dueDate) throws DateTimeException {
        super(name);
        String[] params = dueDate.split("-");

        if (params.length != 3) {
            throw new DateTimeException("Invalid date format. ");
        }

        this.dueDate = LocalDate.of(
                Integer.parseInt(params[0]),
                Integer.parseInt(params[1]),
                Integer.parseInt(params[2]));

    }

    /**
     * Returns the due date as an ISO-8601 string.
     *
     * @return the due date string.
     */
    public String getDueDate() {
        return this.dueDate.toString();
    }

    /**
     * Returns the display string for this deadline task.
     * The format is "[D]<TaskString> (by:yyyy-MM-dd)".
     *
     * @return the string representation of this deadline.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.getDueDate() + ")";
    }
}
