package ListGPT;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private String filePath;
    private TaskList list;

    public Storage(String filePath, TaskList list) {
        this.filePath = filePath;
        this.list = list;
    }

    public void store() {
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
            for (Task task : list.exportList()) {
                fw.write(Parser.reverseParse(task) + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error while storing tasks: " + e.getMessage());
        }
    }

    public void retrieve() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return;
            }

            Scanner s = new Scanner(file);
            while (s.hasNext()) {
                Parser.parse(s.nextLine(), list);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error while reading file: " + e.getMessage());
        }
    }
}
