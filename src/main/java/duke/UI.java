package duke;

import java.util.Scanner;

public class UI {
    private final String name = "ListGPT";
    private final String linebreak = "__________________________________";
    private TaskList list;

    public UI(TaskList list) {
        this.list = list;
    }

    public String message(String text) {
        return "   " + linebreak + "\n" + text + "\n   " + linebreak + "\n";
    }

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

    private void bye() {
        System.out.println(message("   Bye. Hope to see you again soon!"));
    }
}
