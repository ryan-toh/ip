package listgpt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * A utility class for persisting and retrieving task data to/from a file.
 */
public class Storage {
    private String filePath;
    private TaskList list;

    /**
     * Constructs a Storage object with the specified file path and task list.
     *
     * <p>Initializes the storage with the provided file path and task list.
     * Ensures that the filePath is not null or blank and TaskList is not null.
     *
     * @param filePath the path to the storage file; must not be null or blank
     * @param list the task list to be stored; must not be null
     */
    public Storage(String filePath, TaskList list) {
        assert filePath != null && !filePath.isBlank() : "filePath is null/blank";
        assert list != null : "Task list is null";

        this.filePath = filePath;
        this.list = list;
    }

    /**
     * Saves the current task list to the specified file path, overwriting the file each time.
     *
     * <p>This method ensures the parent directory exists and creates the file if necessary.
     * It writes the task list in a format that can be parsed by the application's loader.
     * The file is overwritten completely on each call to this method, ensuring the latest task list is stored.
     *
     * <p>Note: The method does not throw exceptions;
     * any I/O errors are printed to the console.
     * It assumes the file path and task list are valid (as verified by assertions at the start of the method).
     */
    public void store() {
        // Ensure filePath and list is still valid
        assert filePath != null && !filePath.isBlank();
        assert list != null;

        try {
            File file = new File(filePath);

            // Ensure parent directories exist
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            // Create the file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
            }

            // Write to the file
            FileWriter fw = new FileWriter(file, false); // false = overwrite, true = append
            var exportedList = list.exportList();
            assert exportedList != null : "exportList() returned null";

            for (Task task : exportedList) {
                assert task != null : "exported task is null";
                fw.write(Parser.reverseParse(task) + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error while storing tasks: " + e.getMessage());
        }
    }

    /**
     * Reads tasks from the file at filePath and adds them to the task list.
     * If the file does not exist, nothing is loaded. Prints an error if the file cannot be opened.
     */
    public void retrieve() {
        assert filePath != null && !filePath.isBlank() : "filePath is null/blank";
        assert list != null : "Task list is null";

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return;
            }

            Scanner s = new Scanner(file);
            while (s.hasNext()) {
                String line = s.nextLine();
                assert line != null : "null line read";
                assert !line.isBlank() : "Blank line in storage file";

                Parser.parse(line, list);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error while reading file: " + e.getMessage());
        }
    }
}
