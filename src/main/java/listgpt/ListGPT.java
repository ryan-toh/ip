package listgpt;

public class ListGPT {
    private static TaskList list = new TaskList();
    private static Ui userInterface = new Ui(list);
    private static Storage storage = new Storage("./data/ListGPT.txt", list);

    public static void main(String[] args) {
        // Retrieve before running
        storage.retrieve();

        userInterface.run();

        // Save on exit
        storage.store();
    }
}
