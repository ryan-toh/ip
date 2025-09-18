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
            assert task.getDescription() != null && !task.getDescription().isBlank(): "ToDo has empty description";

            String tag = task.getTag();
            if (tag.isEmpty()) {
                return "todo " + task.getDescription();
            }

            return "todo " + task.getDescription() + " --tag " + tag;
        } else if (task instanceof Deadline) {
            // e.g. "deadline return book /by Sunday"
            assert task.getDescription() != null && !task.getDescription().isBlank(): "Deadline has empty description";
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
            assert task.getDescription() != null && !task.getDescription().isBlank(): "Event has empty description";
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
     * Parses user input and executes the corresponding command on the task list.
     *
     * @param input The user input string to be parsed.
     * @param list  The task list to apply the command to.
     * @return A confirmation message or error message based on the command executed.
     */
    public static String parse(String input, TaskList list) {
        assert list != null : "list is null";
        assert input != null : "input is null";
        input = input.trim();

        // View list
        if (input.equals("list")) {
            return list.toString();
        }

        // Find
        if (input.startsWith("find")) {
            String rest = input.length() >= 5 ? input.substring(5) : "";
            return list.find(rest);
        }

        // Mark done
        if (input.matches("^mark \\d+$")) {
            int idx = parseIndex(input, "^mark (\\d+)$");
            assert idx > 0 : "Regex matched but index <= 0";

            if (list.contains(idx - 1)) {
                return list.markDone(idx - 1);
            } else {
                return "The task with the specified index " + idx + " does not exist.";
            }
        }

        // Mark undone
        if (input.matches("^unmark \\d+$")) {
            int idx = parseIndex(input, "^unmark (\\d+)$");
            assert idx > 0 : "Regex matched but index <= 0";

            if (list.contains(idx - 1)) {
                return list.markUndone(idx - 1);
            } else {
                return "The task with the specified index " + idx + " does not exist.";
            }
        }

        // Delete
        if (input.matches("^delete \\d+$")) {
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

        // Add todo "todo <desc> [--tag <tag>]"
        if (input.startsWith("todo")) {
            String rest = input.length() >= 5 ? input.substring(5) : "";

            // everything after "--tag" is the tag
            String[] parts = rest.split("\\s+--tag\\s+", 2);
            String desc = parts[0].trim();
            String tag = null;

            if (parts.length == 2) {
                tag = parts[1].trim();
            }

            try {
                ToDo todo;
                if (tag == null) {
                    todo = new ToDo(desc) ;
                } else {
                    todo = new ToDo(desc, tag);
                }
                return list.add(todo) + "\n" + list.getPrettyCount();

            } catch (InvalidTaskException e) {
                return e.getMessage();
            }
        }

        // Add deadline: "deadline <desc> /by <when> [--tag <tag>]"
        if (input.startsWith("deadline")) {
            String rest = input.length() >= 9 ? input.substring(9) : "";
            String[] parts = rest.split(" /by ", 2);

            if (parts.length == 2) {
                String task = parts[0];
                String right = parts[1];

                // everything after "--tag" is the tag
                String[] dateAndTag = right.split("\\s+--tag\\s+", 2);
                String deadlineDate = dateAndTag[0].trim();
                String tag = null;

                if (dateAndTag.length == 2) {
                    tag = dateAndTag[1].trim();
                }

                try {
                    Deadline deadline;
                    if (tag == null) {
                        deadline = new Deadline(task, deadlineDate);
                    } else {
                        deadline = new Deadline(task, deadlineDate, tag);
                    }
                    return list.add(deadline) + "\n" + list.getPrettyCount();

                } catch (InvalidTaskException | DateTimeException e) {
                    return "    OOPS: " + e.getMessage();
                }
            } else {
                return "Usage: deadline <description> /by <YYYY-MM-DD>";
            }
        }

        // Add event: "event <desc> /from <start> /to <end> [--tag <tag>]"
        if (input.startsWith("event")) {
            String rest = input.length() >= 6 ? input.substring(6) : "";
            // Split on " /from " and " /to " keeping order
            String[] parts = rest.split(" /from | /to ", 3);

            if (parts.length == 3) {
                String task = parts[0];
                String startDate = parts[1];

                // endDate may include an optional tag after "--tag"
                String[] endDateAndTag = parts[2].trim().split("\\s+--tag\\s+", 2);
                String endDate = endDateAndTag[0].trim();
                String tag = null;

                if (endDateAndTag.length == 2) {
                    tag = endDateAndTag[1].trim();
                }

                try {
                    Event event;
                    if (tag == null) {
                        event = new Event(task, startDate, endDate);
                    } else {
                        event = new Event(task, startDate, endDate, tag);
                    }
                    return list.add(event) + "\n" + list.getPrettyCount();
                } catch (InvalidTaskException | DateTimeException e) {
                    return "    OOPS: " + e.getMessage();
                }
            } else {
                return "Usage: event <description> /from <YYYY-MM-DD> /to <YYYY-MM-DD>";
            }
        }

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