package duke;

public class ListGPT {
    private static TaskList list = new TaskList();
    private static UI ui = new UI(list);
    private static Storage store = new Storage("./data/ListGPT.txt", list);

    public static void main(String[] args) {
        // Retrieve before running
        store.retrieve();

        ui.run();

        // Save on exit
        store.store();
    }
}
