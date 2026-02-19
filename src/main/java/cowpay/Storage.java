package cowpay;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import cowpay.task.Deadline;
import cowpay.task.Event;
import cowpay.task.Task;

/**
 * Handles loading and saving tasks to a file
 */
public class Storage {

    private final String filePath;

    /**
     * Creates a Storage that loads from and saves to the given file path
     *
     * @param filePath e.g., data/cowpay.txt
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Creates the storage file if it does not exist
     *
     * @return File object representing the storage file
     * @throws IOException if there is an error creating the file
     */
    private File getOrCreateStorageFile() throws IOException {
        File file = new File(this.filePath);

        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }

    /**
     * Loads tasks from storage file.
     * If the folder or file does not exist, create them and return empty list.
     *
     * @return ArrayList<Task> containing all tasks loaded from file
     */
    public ArrayList<Task> loadTasksFromFile() {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            File file = getOrCreateStorageFile();
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|");
                // Expected:
                // T|0|desc
                // D|1|desc|by
                // E|0|desc|from|to

                if (parts.length < 3) {
                    continue; // skip corrupted line
                }

                String type = parts[0];
                int done = Integer.parseInt(parts[1]);
                String desc = parts[2];

                Task t;

                if ("T".equals(type)) {
                    t = new Task(desc);

                } else if ("D".equals(type)) {
                    if (parts.length < 4) {
                        continue; // corrupted deadline line
                    }
                    String by = parts[3];
                    t = new Deadline(desc, by);

                } else if ("E".equals(type)) {
                    if (parts.length < 5) {
                        continue; // corrupted event line
                    }
                    String from = parts[3];
                    String to = parts[4];
                    t = new Event(desc, from, to);

                } else {
                    continue; // unknown type
                }

                if (done == 1) {
                    t.markAsDone();
                }

                tasks.add(t);
            }

            fileScanner.close();

        } catch (Exception e) {
            System.out.println("Error loading tasks from file: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Saves all tasks to storage file.
     * Overwrites existing file content so the file always matches the current task list.
     *
     * @param tasks List of tasks to be saved
     */
    public void saveTasksToFile(ArrayList<Task> tasks) {
        try {
            File file = getOrCreateStorageFile();
            FileWriter fw = new FileWriter(file, false); // overwrite the whole file

            for (Task t : tasks) {
                fw.write(taskToLine(t));
                fw.write(System.lineSeparator());
            }

            fw.close();

        } catch (Exception e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Format the task for saving to the file
     *
     * @param t The task to convert
     * @return Correct format of task for saving
     */
    private static String taskToLine(Task t) {
        // T|0|desc
        // D|1|desc|by
        // E|0|desc|from|to

        assert t != null : "Task should not be null!!";

        int isDone = t.isDone() ? 1 : 0;

        if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return "D|" + isDone + "|" + d.getDescription() + "|"
                + d.getByInputFormat();

        } else if (t instanceof Event) {
            Event e = (Event) t;
            return "E|" + isDone + "|" + e.getDescription() + "|"
                + e.getFromInputFormat() + "|" + e.getToInputFormat();

        } else {
            return "T|" + isDone + "|" + t.getDescription();
        }
    }
}
