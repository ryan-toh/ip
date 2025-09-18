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

    public Storage(String filePath, TaskList list) {
        assert filePath != null && !filePath.isBlank() : "filePath is null/blank";
        assert list != null : "Task list is null";

        this.filePath = filePath;
        this.list = list;
    }

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
            assert exportedList != null: "exportList() returned null";

            for (Task task : exportedList) {
                assert task != null : "exported task is null";
                fw.write(Parser.reverseParse(task) + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error while storing tasks: " + e.getMessage());
        }
    }

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
