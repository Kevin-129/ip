import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHelper {

    /**
     * Loads tasks from ./data/cowpay.txt
     * If the folder or file does not exist, create them and return empty list
     *
     * @return ArrayList<Task> containing all tasks loaded from file
     */
    public static ArrayList<Task> loadFromFile() {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            File dir = new File("data");
            dir.mkdirs(); // create ./data if doesn't exist

            File file = new File(dir, "cowpay.txt");
            if (!file.exists()) {
                file.createNewFile(); // create file if doesn't exist
                return tasks;
            }

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

                Task t;

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
    public static void saveAll(ArrayList<Task> tasks) {
        try {
            File dir = new File("data");
            dir.mkdirs();

            File file = new File(dir, "cowpay.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file, false); // overwrite the whole file

            for (Task t : tasks) {
                fw.write(taskToLine(t) + System.lineSeparator());
            }

            fw.close();
        } catch (IOException e) {
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
        int isDone = t.isDone() ? 1 : 0;

        if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return "D|" + isDone + "|" + d.getDescription() + "|" + d.getBy();
        } else if (t instanceof Event) {
            Event e = (Event) t;
            return "E|" + isDone + "|" + e.getDescription() + "|" + e.getFrom() + "|" + e.getTo();
        } else {
            return "T|" + isDone + "|" + t.getDescription();
        }
    }

}
