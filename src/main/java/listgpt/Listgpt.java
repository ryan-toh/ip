package listgpt;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry point of application for CLI and GUI.
 */
public class Listgpt extends Application {

    private static TaskList list = new TaskList();
    private static CommandLine messageInterface = new CommandLine(list);
    private static GraphicalInterface userInterface;
    private static Storage storage = new Storage("./data/Listgpt.txt", list);

    /**
     * CLI Interface for ListGPT
     * @param args
     */
    public static void main(String[] args) {
        // Retrieve before running
        storage.retrieve();

        messageInterface.run();

        // Save on exit
        storage.store();
    }

    /**
     * Creates an instance of the user interface
     * @param stage The stage to display the user interface on.
     */
    @Override
    public void start(Stage stage) {
        userInterface = new GraphicalInterface(list, messageInterface, storage);
        Scene scene = userInterface.build();

        stage.setTitle("ListGPT");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);
        stage.setMaxHeight(800.0);
        stage.setMaxWidth(600.0);
        stage.show();
    }

    @Override
    public void stop() {
        if (userInterface != null) {
            userInterface.onStop();
        }
    }
}
