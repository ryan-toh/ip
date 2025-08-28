package ListGPT;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Deadline extends Task {
    private LocalDate dueDate;


    public Deadline(String name, String dueDate) throws DateTimeException {
        super(name);
        String[] params = dueDate.split("-");

        if (params.length != 3) {
            throw new DateTimeException("Invalid date format. ");
        }

        this.dueDate = LocalDate.of(Integer.parseInt(params[0]), Integer.parseInt(params[1]),
                Integer.parseInt(params[2]));

    }

    public String getBy() {
        return this.dueDate.toString();
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.getBy() + ")";
    }
}
