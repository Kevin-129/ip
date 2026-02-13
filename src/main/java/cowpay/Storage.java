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
        if (parent != null) {
            parent.mkdirs();
        }

        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }

    /**
     * Loads tasks from ./data/cowpay.txt
     * If the folder or file does not exist, create them and return empty list
     *
     * @return ArrayList<Task> containing all tasks loaded from file
     */
    public ArrayList<Task> loadTasksFromFile() {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
<<<<<<< HEAD
            File file = new File(this.filePath);

            File parent = file.getParentFile();

            //Parent directory should exist
            assert parent != null && parent.exists() : "Parent directory for file does not exist!!";

            if (parent != null) {
                parent.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
                return tasks;
            }

=======
            File file = getOrCreateStorageFile();
>>>>>>> refs/rewritten/branch-A-CodeQuality
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|");
                String type = parts[0];
                int done = Integer.parseInt(parts[1]);
                String desc = parts[2];

                Task t = null;

                if (type.equals("T")) {
                    t = new Task(desc);
                } else if (type.equals("D")) {
                    String by = parts[3];
                    t = new Deadline(desc, by);
                } else {
                    String from = parts[3];
                    String to = parts[4];
                    t = new Event(desc, from, to);
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
     * Saves all tasks to ./data/cowpay.txt
     * Overwrites existing file content so the file always matches the current task list
     *
     * @param tasks List of tasks to be saved
     */
    public void saveTasksToFile(ArrayList<Task> tasks) {
        try {
<<<<<<< HEAD
            File file = new File(this.filePath);

            File parent = file.getParentFile();

            //Parent directory should exist
            assert parent != null && parent.exists() : "Parent directory for file does not exist!!";

            if (parent != null) {
                parent.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }

=======
            File file = getOrCreateStorageFile();
>>>>>>> refs/rewritten/branch-A-CodeQuality
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

        //Task should not be null
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
