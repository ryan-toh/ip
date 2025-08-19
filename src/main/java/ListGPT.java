import java.util.Scanner;

public class ListGPT {
    private static final String name = "ListGPT";
    private static final String linebreak = "__________________________________";

    public static String message(String text) {
        return "   " + linebreak + "\n   " + text + "\n   " + linebreak + "\n";
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println(message("Hello! I'm " + name + "!\n   What can I do for you?"));
        String input = sc.nextLine();
        while (!input.equals("bye")) {
            System.out.print(message(input));
            input = sc.nextLine();
        }


        System.out.println(message("Bye. Hope to see you again soon!"));
    }


}
