package ListGPT;

import java.time.DateTimeException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class for parsing user input commands and converting tasks back to their original command format.
 */
public class Parser {
    /**
     * Converts a task object back into its original command string format.
     *
     * @param task The task object to be reverse-parsed.
     * @return The original command string representation of the task.
     * @throws IllegalArgumentException If the task type is unknown.
     */
    public static String reverseParse(Task task) throws IllegalArgumentException {
        if (task instanceof ToDo) {
            // e.g. "todo read book"
            return "todo " + task.getDescription();
        } else if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            // e.g. "deadline return book /by Sunday"
            return "deadline " + d.getDescription() + " /by " + d.getBy();
        } else if (task instanceof Event) {
            Event e = (Event) task;
            // e.g. "event project meeting /from Mon 2pm /to 4pm"
            return "event " + e.getDescription() + " /from " + e.getFrom() + " /to " + e.getTo();
        } else {
            throw new IllegalArgumentException("Unknown task type: " + task.getClass().getName());
        }
    }

    /**
     * Parses user input and executes the corresponding command on the task list.
     *
     * @param input The user input string to be parsed.
     * @param list  The task list to apply the command to.
     * @return A confirmation message or error message based on the command executed.
     */
    public static String parse(String input, TaskList list) {
        input = input.trim();

        // View list
        if (input.equals("list")) {
            return list.toString();
        }

        // Mark done
        if (input.matches("^mark \\d+$")) {
            int idx = parseIndex(input, "^mark (\\d+)$");
            if (list.contains(idx - 1)) {
                return list.markDone(idx - 1);
            } else {
                return "The task with the specified index " + idx + " does not exist.";
            }
        }

        // Mark undone
        if (input.matches("^unmark \\d+$")) {
            int idx = parseIndex(input, "^unmark (\\d+)$");
            if (list.contains(idx - 1)) {
                return list.markUndone(idx - 1);
            } else {
                return "The task with the specified index " + idx + " does not exist.";
            }
        }

        // Delete
        if (input.matches("^delete \\d+$")) {
            int idx = parseIndex(input, "^delete (\\d+)$");
            if (!list.contains(idx - 1)) {
                return "   OOPS!! The task specified does not exist.";
            } else {
                return "   Noted. I've removed this task:\n    " +
                        list.remove(idx - 1) + "\n" + list.getPrettyCount();
            }
        }

        // Add todo
        if (input.startsWith("todo")) {
            String rest = input.length() >= 5 ? input.substring(5) : "";
            try {
                return list.add(new ToDo(rest)) + "\n" + list.getPrettyCount();
            } catch (InvalidTaskException e) {
                return e.getMessage();
            }
        }

        // Add deadline: "deadline <desc> /by <when>"
        if (input.startsWith("deadline")) {
            String rest = input.length() >= 9 ? input.substring(9) : "";
            String[] parts = rest.split(" /by ", 2);
            if (parts.length == 2) {
                String task = parts[0];
                String deadline = parts[1];
                try {
                    return list.add(new Deadline(task, deadline)) + "\n" + list.getPrettyCount();
                } catch (InvalidTaskException | DateTimeException e) {
                    return "    OOPS: " + e.getMessage();
                }
            } else {
                return "Usage: deadline <description> /by <YYYY-MM-DD>";
            }
        }

        // Add event: "event <desc> /from <start> /to <end>"
        if (input.startsWith("event")) {
            String rest = input.length() >= 6 ? input.substring(6) : "";
            // Split on " /from " and " /to " keeping order
            String[] parts = rest.split(" /from | /to ", 3);
            if (parts.length == 3) {
                String task = parts[0];
                String startDate = parts[1];
                String endDate = parts[2];
                try {
                    return list.add(new Event(task, startDate, endDate)) + "\n" + list.getPrettyCount();
                } catch (InvalidTaskException | DateTimeException e) {
                    return "    OOPS: " + e.getMessage();
                }
            } else {
                return "Usage: event <description> /from <YYYY-MM-DD> /to <YYYY-MM-DD>";
            }
        }

        // Fallback
        return "   OOPS!! I'm sorry, but I don't know what that means :-(";
    }

    /**
     * Extracts the numeric index from a command string using a regex pattern.
     *
     * @param input  The input string containing the command.
     * @param regex  The regex pattern to match the index.
     * @return The parsed index as an integer, or -1 if no match is found.
     */
    private static int parseIndex(String input, String regex) {
        Matcher m = Pattern.compile(regex).matcher(input);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return -1;
    }
}