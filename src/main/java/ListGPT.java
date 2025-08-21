import java.util.Arrays;
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
            // To view the list
            if (input.equals("list")) {
                input = list.toString();

                // To mark a task as done
            } else if (input.matches("mark \\d+")) {
                int idx = Integer.parseInt(input.substring(5));
                if (list.contains(idx-1)) {
                    input = list.markDone(idx-1);
                } else {
                    input = "The task with the specified index " + idx + " does not exist.";
                }

                // To mark the task as undone
            } else if (input.matches("unmark \\d+")) {
                int idx = Integer.parseInt(input.substring(7));
                if (list.contains(idx-1)) {
                    input = list.markUndone(idx-1);
                } else {
                    input = "The task with the specified index " + idx + " does not exist.";
                }

                // Add a todo
            } else if (input.startsWith("todo")){
                String rest = input.substring(5);
                try {
                    input = list.add(new ToDo(rest)) + "\n" + list.getPrettyCount();
                } catch (InvalidTaskException e) {
                    input = e.getMessage();
                }
                // add a deadline
            } else if (input.startsWith("deadline")) {
                String rest = input.substring(9);
                String[] parts = rest.split(" /by ", 2);
                if (parts.length == 2) {
                    String task = parts[0];
                    String deadline = parts[1];
                    try {
                        input = list.add(new Deadline(task, deadline)) + "\n" + list.getPrettyCount();
                    } catch (InvalidTaskException e) {
                        input = e.getMessage();
                    }
                }

                // add a event
            } else if (input.startsWith("event")) {
                String rest = input.substring(6);
                String[] parts = rest.split(" /from | /to", 3);

                if (parts.length == 3) {
                    String task = parts[0];
                    String startDate = parts[1];
                    String endDate = parts[2];
                    try {
                        input = list.add(new Event(task, startDate, endDate)) + "\n" + list.getPrettyCount();
                    } catch (InvalidTaskException e) {
                        input = e.getMessage();
                    }

                }

            } else {
                input = "   OOPS!! I'm sorry, but I don't know what that means :-(";
            }
            System.out.print(message(input) + "\n");
            input = sc.nextLine();
        }

        System.out.println(message("   Bye. Hope to see you again soon!"));
    }


}
