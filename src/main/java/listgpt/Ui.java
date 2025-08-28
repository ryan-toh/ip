package listgpt;

import java.util.Scanner;

public class Ui {
    private final String appName = "listgpt";
    private final String lineBreak = "__________________________________";
    private TaskList list;

    public Ui(TaskList list) {
        this.list = list;
    }

    public String formatMessage(String text) {
        return "   " + lineBreak + "\n" + text + "\n   " + lineBreak + "\n";
    }

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

    private void sayBye() {
        System.out.println(formatMessage("   Bye. Hope to see you again soon!"));
    }
}
