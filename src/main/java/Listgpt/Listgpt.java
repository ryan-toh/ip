package Listgpt;

public class Listgpt {
    private static TaskList list = new TaskList();
    private static UI userInterface = new UI(list);
    private static Storage storage = new Storage("./data/Listgpt.txt", list);

    public static void main(String[] args) {
        // Retrieve before running
        storage.retrieve();

        userInterface.run();

        // Save on exit
        storage.store();
    }
}
