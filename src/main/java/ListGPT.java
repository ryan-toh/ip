import java.util.Scanner;

public class ListGPT {
    private static final String name = "ListGPT";
    private static final String linebreak = "__________________________________";
    private static TaskList list = new TaskList();

    public static String message(String text) {
        return "   " + linebreak + "\n" + text + "\n   " + linebreak + "\n";
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println(message("   Hello! I'm " + name + "!\n   What can I do for you?"));
        String input = sc.nextLine();
        while (!input.equals("bye")) {
            if (input.equals("list")) {
                input = list.toString();
            } else if (input.matches("mark \\d+")) {
                int idx = Integer.parseInt(input.substring(5));
                if (list.contains(idx-1)) {
                    input = list.markDone(idx-1);
                } else {
                    input = "The task with the specified index " + idx + " does not exist.";
                }
            } else if (input.matches("unmark \\d+")) {
                int idx = Integer.parseInt(input.substring(7));
                if (list.contains(idx-1)) {
                    input = list.markUndone(idx-1);
                } else {
                    input = "The task with the specified index " + idx + " does not exist.";
                }
            } else {
                input = list.add(new Task(input));
            }
            System.out.print(message(input) + "\n");
            input = sc.nextLine();
        }

        System.out.println(message("   Bye. Hope to see you again soon!"));
    }


}
