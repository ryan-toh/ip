import java.util.Scanner;

public class ListGPT {
    private static final String name = "ListGPT";
    private static final String linebreak = "__________________________________";
    private static String[] items = new String[100];
    private static int itemCount = 0;

    public static String message(String text) {
        return "   " + linebreak + "\n" + text + "\n   " + linebreak + "\n";
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println(message("   Hello! I'm " + name + "!\n   What can I do for you?"));
        String input = sc.nextLine();
        while (!input.equals("bye")) {
            if (input.equals("list")) {
                input = "";
                for (int i = 0; i < itemCount; i++) {
                    int idx = i + 1;
                    input += ("   " + idx + ". " + items[i] + "\n");
                }
            } else {
                items[itemCount++] = input;
                input = "   added: " + input;
            }
            System.out.print(message(input) + "\n");
            input = sc.nextLine();
        }


        System.out.println(message("   Bye. Hope to see you again soon!"));
    }


}
