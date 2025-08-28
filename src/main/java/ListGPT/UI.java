package ListGPT;

import java.util.Scanner;

/**
 * Console-based user interface for the ListGPT application.
 */
public class UI {

    /** Display name used in greetings. */
    private final String name = "ListGPT";

    /** Horizontal line used as a visual separator around messages. */
    private final String linebreak = "__________________________________";

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
    public String message(String text) {
        return "   " + linebreak + "\n" + text + "\n   " + linebreak + "\n";
    }

    /**
     * Runs the interactive loop, reading user commands from standard input and
     * writing responses to standard output. The loop terminates when the user
     * enters "bye"
     */
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println(message("   Hello! I'm " + name + "!\n   What can I do for you?"));

        while (true) {
            String input = sc.nextLine();
            if (input.equals("bye")) {
                this.bye();
                return;
            }
            String response = Parser.parse(input, list);
            System.out.print(message(response) + "\n");
        }
    }

    /**
     * Prints the farewell message to standard output.
     */
    private void bye() {
        System.out.println(message("   Bye. Hope to see you again soon!"));
    }
}
