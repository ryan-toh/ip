import java.util.Arrays;
import java.util.Scanner;

public class ListGPT {
    private static final String name = "ListGPT";
    private static final String linebreak = "__________________________________";
    private static TaskList list = new TaskList();
    private static Storage store = new Storage("./data/ListGPT.txt", list);

    public static String message(String text) {
        return "   " + linebreak + "\n" + text + "\n   " + linebreak + "\n";
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        store.retrieve();

        System.out.println(message("   Hello! I'm " + name + "!\n   What can I do for you?"));

        while (true) {
            String input = sc.nextLine();
            if (input.equals("bye")) {
                break;
            }
            String response = Parser.parse(input, list);
            System.out.print(message(response) + "\n");
        }

        // Save on exit
        store.store();
        System.out.println(message("   Bye. Hope to see you again soon!"));
    }
}
