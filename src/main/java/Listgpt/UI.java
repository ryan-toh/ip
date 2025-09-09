package Listgpt;

import java.util.Scanner;

/**
 * Console-based user interface for the ListGPT application.
 */
public class UI {
    /** Display name used in greetings. */
    private final String appName = "Listgp";

    /** Horizontal line used as a visual separator around messages. */
    private final String lineBreak = "__________________________________";

    /** The task list that commands will operate on. */
    private TaskList list;

    /**
     * Constructs a UI bound to the given task list.
     *
     * @param list the TaskList to operate on; must not be null.
     */
    public UI(TaskList list) {
        this.list = list;
    }

    /**
     * Returns the given text wrapped with the standard separators and indentation.
     *
     * @param text the message body to format; may contain newlines.
     * @return a formatted message string.
     */
    public String formatMessage(String text) {
        return "   " + lineBreak + "\n" + text + "\n   " + lineBreak + "\n";
    }

    /**
     * Runs the interactive loop, reading user commands from standard input and
     * writing responses to standard output. The loop terminates when the user
     * enters "bye"
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(formatMessage("   Hello! I'm " + appName + "!\n   What can I do for you?"));

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                this.sayBye();
                return;
            }
            String response = Parser.parse(input, list);
            System.out.print(formatMessage(response) + "\n");
        }
    }

    /**
     * Prints the farewell message to standard output.
     */
    private void sayBye() {
        System.out.println(formatMessage("   Bye. Hope to see you again soon!"));
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        if (input.equals("hello")) {
            return formatMessage("   Hello! I'm " + appName + "!\n   What can I do for you?");
        }

        String response = Parser.parse(input, list);
        return formatMessage(response) + "\n";
    }
}
