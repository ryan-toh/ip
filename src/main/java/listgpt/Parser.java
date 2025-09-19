package listgpt;

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
        assert task != null : "task is null";

        if (task instanceof ToDo) {
            // e.g. "todo read book"
            assert task.getDescription() != null
                    && !task.getDescription().isBlank() : "ToDo has empty description";

            String tag = task.getTag();
            if (tag.isEmpty()) {
                return "todo " + task.getDescription();
            }

            return "todo " + task.getDescription() + " --tag " + tag;
        } else if (task instanceof Deadline) {
            // e.g. "deadline return book /by Sunday"
            assert task.getDescription() != null
                    && !task.getDescription().isBlank() : "Deadline has empty description";
            Deadline deadline = (Deadline) task;

            String tag = deadline.getTag();
            if (tag.isEmpty()) {
                return "deadline "
                        + deadline.getDescription()
                        + " /by "
                        + deadline.getDueDate();
            }

            return "deadline "
                    + deadline.getDescription()
                    + " /by "
                    + deadline.getDueDate()
                    + " --tag "
                    + tag;

        } else if (task instanceof Event) {
            // e.g. "event project meeting /from Mon 2pm /to 4pm"
            assert task.getDescription() != null
                    && !task.getDescription().isBlank() : "Event has empty description";
            Event event = (Event) task;

            String tag = event.getTag();
            if (tag.isEmpty()) {
                return "event "
                        + event.getDescription()
                        + " /from "
                        + event.getFrom()
                        + " /to "
                        + event.getTo();
            }
            return "event "
                    + event.getDescription()
                    + " /from " + event.getFrom()
                    + " /to " + event.getTo()
                    + " --tag "
                    + tag;

        } else {
            throw new IllegalArgumentException("Unknown task type: " + task.getClass().getName());
        }
    }

    /**
     * Takes in an input command from the user, executes the action, and outputs a response.
     * @param input the user input.
     * @param list the tasklist to be modified.
     * @return the response to the user.
     */
    public static String parse(String input, TaskList list) {
        assert list != null : "list is null";
        assert input != null : "input is null";
        input = input.trim();

        if (input.equals("list")) {
            return handleList(list);
        }
        if (input.startsWith("find")) {
            return handleFind(input, list);
        }
        if (input.matches("^mark \\d+$")) {
            return handleMark(input, list);
        }
        if (input.matches("^unmark \\d+$")) {
            return handleUnmark(input, list);
        }
        if (input.matches("^delete \\d+$")) {
            return handleDelete(input, list);
        }
        if (input.startsWith("todo")) {
            return handleTodo(input, list);
        }
        if (input.startsWith("deadline")) {
            return handleDeadline(input, list);
        }
        if (input.startsWith("event")) {
            return handleEvent(input, list);
        }

        return unknownCommand();
    }

    // ===== Command handlers =====

    private static String handleList(TaskList list) {
        return list.toString();
    }

    private static String handleFind(String input, TaskList list) {
        String rest = input.length() >= 5 ? input.substring(5) : "";
        return list.find(rest);
    }

    private static String handleMark(String input, TaskList list) {
        int idx = parseIndex(input, "^mark (\\d+)$");
        assert idx > 0 : "Regex matched but index <= 0";
        if (list.contains(idx - 1)) {
            return list.markDone(idx - 1);
        } else {
            return "The task with the specified index " + idx + " does not exist.";
        }
    }

    private static String handleUnmark(String input, TaskList list) {
        int idx = parseIndex(input, "^unmark (\\d+)$");
        assert idx > 0 : "Regex matched but index <= 0";
        if (list.contains(idx - 1)) {
            return list.markUndone(idx - 1);
        } else {
            return "The task with the specified index " + idx + " does not exist.";
        }
    }

    private static String handleDelete(String input, TaskList list) {
        int idx = parseIndex(input, "^delete (\\d+)$");
        assert idx > 0 : "Regex matched but index <= 0";
        if (!list.contains(idx - 1)) {
            return "   OOPS!! The task specified does not exist.";
        } else {
            return "   Noted. I've removed this task:\n    "
                    + list.remove(idx - 1) + "\n"
                    + list.getPrettyCount();
        }
    }

    // Add todo: "todo <desc> [--tag <tag>]"
    private static String handleTodo(String input, TaskList list) {
        String rest = input.length() >= 5 ? input.substring(5) : "";

        String[] parts = rest.split("\\s+--tag\\s+", 2);
        String desc = parts[0].trim();
        String tag = (parts.length == 2) ? parts[1].trim() : null;

        try {
            ToDo todo = (tag == null) ? new ToDo(desc) : new ToDo(desc, tag);
            return list.add(todo) + "\n" + list.getPrettyCount();
        } catch (InvalidTaskException e) {
            return e.getMessage();
        }
    }

    // Add deadline: "deadline <desc> /by <when> [--tag <tag>]"
    private static String handleDeadline(String input, TaskList list) {
        String rest = input.length() >= 9 ? input.substring(9) : "";
        String[] parts = rest.split(" /by ", 2);

        if (parts.length != 2) {
            return "Usage: deadline <description> /by <YYYY-MM-DD>";
        }

        String task = parts[0];
        String right = parts[1];

        String[] dateAndTag = right.split("\\s+--tag\\s+", 2);
        String deadlineDate = dateAndTag[0].trim();
        String tag = (dateAndTag.length == 2) ? dateAndTag[1].trim() : null;

        try {
            Deadline deadline = (tag == null)
                    ? new Deadline(task, deadlineDate)
                    : new Deadline(task, deadlineDate, tag);
            return list.add(deadline) + "\n" + list.getPrettyCount();
        } catch (InvalidTaskException | DateTimeException e) {
            return "    OOPS: " + e.getMessage();
        }
    }

    // Add event: "event <desc> /from <start> /to <end> [--tag <tag>]"
    private static String handleEvent(String input, TaskList list) {
        String rest = input.length() >= 6 ? input.substring(6) : "";
        String[] parts = rest.split(" /from | /to ", 3);

        if (parts.length != 3) {
            return "Usage: event <description> /from <YYYY-MM-DD> /to <YYYY-MM-DD>";
        }

        String task = parts[0];
        String startDate = parts[1];

        String[] endDateAndTag = parts[2].trim().split("\\s+--tag\\s+", 2);
        String endDate = endDateAndTag[0].trim();
        String tag = (endDateAndTag.length == 2) ? endDateAndTag[1].trim() : null;

        try {
            Event event = (tag == null)
                    ? new Event(task, startDate, endDate)
                    : new Event(task, startDate, endDate, tag);
            return list.add(event) + "\n" + list.getPrettyCount();
        } catch (InvalidTaskException | DateTimeException e) {
            return "    OOPS: " + e.getMessage();
        }
    }

    private static String unknownCommand() {
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
        Matcher matcher = Pattern.compile(regex).matcher(input);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return -1;
    }
}
