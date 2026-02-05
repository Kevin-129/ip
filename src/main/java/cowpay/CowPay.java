package cowpay;

import java.util.Scanner;

import cowpay.task.Deadline;
import cowpay.task.Event;
import cowpay.task.Task;

/**
 * The main class for the CowPay chatbot
 */
public class CowPay {


    private static final String NAME = "CowPay";

    private String filePath = "data/cowpay.txt";
    private Storage storage = new Storage(filePath);
    private TaskList tasks = new TaskList(storage.load());

    /**
     * Gets response from CowPay for a given user input
     *
     * @param input user input
     * @return response from CowPay
     */
    public String getResponse(String input) {

        String[] inputStrings = input.trim().split(" ", 2);
        //Split on first space only
        String command = inputStrings[0].toLowerCase();
        String details = inputStrings.length < 2 ? "" : inputStrings[1].trim();

        switch (command) {
        case "list":
            return listTasks();
        case "find":
            return findTask(details);
        case "mark":
            return markTaskAsDone(details);
        case "unmark":
            return markTaskAsNotDone(details);
        case "event":
            return addEvent(details);
        case "deadline":
            return addDeadline(details);
        case "todo":
            return addTask(details);
        case "delete":
            return deleteTask(details);
        default:
            return invalidCommand(input.toString());
        }

    }

    /**
     * @return Welcome message
     */
    public String welcome() {
        return "Hello! I'm CowPay. Wat u wan? \nCommands: todo, deadline, event, list, mark, unmark, delete";
    }

    /**
     * @return Lists of all tasks (String)
     */
    private String listTasks() {
        if (this.tasks.isEmpty()) {
            return "No tasks!! STOP SKIVING!!";
        }

        StringBuilder taskListSb = new StringBuilder("You need to do these: \n");

        for (int i = 0; i < this.tasks.size(); i++) {
            Task t = this.tasks.get(i);
            taskListSb.append(i + 1)
                .append(". ")
                .append(taskToLineSb(t))
                .append("\n");
        }

        return taskListSb.toString();
    }

    /**
     * Finds tasks
     *
     * @param keyword the keyword to search for
     * @return matching tasks (String)
     */
    private String findTask(String keyword) {
        if (keyword.isEmpty()) {
            return "Find needs a 'keyword'. E.g. Enter: find book";
        }

        StringBuilder matchedTasksSb = new StringBuilder("Matching Tasks:\n");
        int shownCount = 1;

        for (int i = 0; i < this.tasks.size(); i++) {
            Task t = this.tasks.get(i);
            if (t.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchedTasksSb
                    .append(shownCount)
                    .append(". ")
                    .append(taskToLineSb(t))
                    .append("\n");
                shownCount++;
            }
        }
        if (shownCount == 1) {
            return "No matching tasks found...";
        }
        return matchedTasksSb.toString();
    }

    /**
     * Converts a task to string builder representation
     *
     * @param index index of the task
     * @param t task
     * @return string builder representation of the task
     */
    private StringBuilder taskToLineSb(Task t) {

        // E.g.
        // [T][X] SHOWERRR
        // [D][X] submit file (by: Jan 29 2026 23:59)
        // [E][ ] SLEEEEEP (from: Jan 28 2026 23:59 to: Jan 29 2026 23:59)

        StringBuilder sb = new StringBuilder();

        if (t instanceof Event) {
            Event e = (Event) t;
            sb.append("[E] ")
                .append(t.getStatusIcon())
                .append(" ")
                .append(t.getDescription())
                .append(" (from: ")
                .append(e.getFrom())
                .append(" to: ")
                .append(e.getTo())
                .append(")");

        } else if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            sb.append("[D] ")
                .append(t.getStatusIcon())
                .append(" ")
                .append(t.getDescription())
                .append(" (by: ")
                .append(d.getBy())
                .append(")");

        } else {

            sb.append("[T] ")
                .append(t.getStatusIcon())
                .append(" ")
                .append(t.getDescription());
        }
        return sb;
    }

    /**
     * Marks a task as done
     *
     * @param details task number as string
     * @return response message
     */

    private String markTaskAsDone(String details) {

        Task t = null;
        try {
            if (this.tasks.isEmpty()) {
                return "No tasks!! STOP SKIVING!!";
            }
            int taskNum = Integer.parseInt(details) - 1;
            t = this.tasks.get(taskNum);
        } catch (Exception e) {
            return "Give a valid task number! 1 to " + this.tasks.size()
                + "\nUse 'list' to see all tasks.";
        }

        t.markAsDone();
        this.storage.save(this.tasks.asArrayList());

        return "Ok, this one marked as done: \n  "
            + t.getStatusIcon()
            + " "
            + t.getDescription();
    }

    /**
     * Marks a task as not done
     *
     * @param details task number as string
     * @return response message
     */
    private String markTaskAsNotDone(String details) {

        Task t = null;
        try {
            if (tasks.isEmpty()) {
                return "No tasks!! STOP SKIVING!!";
            }
            int taskNum = Integer.parseInt(details) - 1;
            t = this.tasks.get(taskNum);

        } catch (Exception e) {
            return "Give a valid task number! 1 to " + this.tasks.size()
                + "\nUse 'list' to see all tasks.";
        }

        t.markAsNotDone();
        this.storage.save(this.tasks.asArrayList());

        return "Ok, this one marked as notdone: \n  "
            + t.getStatusIcon()
            + " "
            + t.getDescription();
    }

    /**
     * Adds a new event task
     *
     * @param details event details
     * @return response message
     */
    private String addEvent(String details) {
        Event event = null;
        try {
            event = Parser.parseEvent(details);
        } catch (Exception e) {
            return e.getMessage();
        }

        this.tasks.add(event);
        this.storage.save(this.tasks.asArrayList());

        StringBuilder sb = new StringBuilder();
        sb.append("Ok, you need to: \n  ")
            .append(taskToLineSb(event))
            .append("\nYou have " + this.tasks.size() + " tasks.");

        return sb.toString();
    }

    /**
     * Adds a new deadline task
     *
     * @param details deadline details
     * @return response message
     */
    private String addDeadline(String details) {
        Deadline d = null;
        try {
            d = Parser.parseDeadline(details);
        } catch (Exception e) {
            return e.getMessage();
        }

        this.tasks.add(d);
        this.storage.save(this.tasks.asArrayList());

        StringBuilder sb = new StringBuilder();
        sb.append("Ok, you need to: \n  ")
            .append(taskToLineSb(d))
            .append("\nYou have " + this.tasks.size() + " tasks.");

        return sb.toString();
    }

    /**
     * Adds a new task
     *
     * @param details task description
     * @return response message
     */
    private String addTask(String details) {
        if (details.isEmpty()) {
            return "A todo has a description. \nE.g. Enter: todo SHOWERRR";
        }

        String description = details;
        Task t = new Task(description);
        this.tasks.add(t);
        this.storage.save(this.tasks.asArrayList());

        StringBuilder sb = new StringBuilder();
        sb.append("Ok, you need to: \n  ")
            .append(taskToLineSb(t))
            .append("\nYou have " + this.tasks.size() + " tasks.");

        return sb.toString();
    }

    /**
     * Deletes a task
     *
     * @param details task number as string
     * @return response message
     */
    private String deleteTask(String details) {
        Task t = null;
        int taskNum = -1;

        try {
            if (this.tasks.isEmpty()) {
                return "No tasks!! STOP SKIVING!!";
            }
            taskNum = Integer.parseInt(details) - 1;
            t = this.tasks.get(taskNum);
        } catch (Exception e) {
            return "Give a valid task number! 1 to " + this.tasks.size()
                + "\nUse 'list' to see all tasks.";
        }

        this.tasks.remove(taskNum);
        this.storage.save(this.tasks.asArrayList());

        StringBuilder sb = new StringBuilder();
        sb.append("Ok, I remove this task: \n  ")
            .append(taskToLineSb(t))
            .append("\nYou have " + this.tasks.size() + " tasks.");

        return sb.toString();
    }

    /**
     * Handles invalid commands
     *
     * @param command invalid command
     * @return Invalid command message
     */
    private String invalidCommand(String command) {
        return command + "??? Try a valid command! \nCommands: todo, deadline, event, list, mark, unmark, delete, bye";
    }

    /**
     * Runs the CowPay chatbot in terminal
     *
     * @param args not used
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Ui ui = new Ui();

        String filePath = "data/cowpay.txt";
        Storage storage = new Storage(filePath);

        TaskList tasks = new TaskList(storage.load());


        ui.showWelcome(NAME);

        while (true) {
            if (!scanner.hasNextLine()) {
                break; // Exit if no more input
            }
            String[] inputStrings = scanner.nextLine().trim().split(" ", 2);
            //Split on first space only
            String command = inputStrings[0].toLowerCase();
            String details = inputStrings.length < 2 ? "" : inputStrings[1].trim();

            ui.showLine();

            if (command.equals("bye")) {
                ui.showBye();
                break; // Exit

            } else if (command.equals("list")) {
                //List all tasks
                System.out.println("\tYou need do these: ");

                for (int i = 0; i < tasks.size(); i++) {
                    Task t = tasks.get(i);
                    System.out.print("\t" + (i + 1) + ".");

                    String time = "";

                    if (t instanceof Event) {
                        Event e = (Event) t;
                        System.out.print("[E]");
                        time = " (from: " + e.getFrom() + " to: " + e.getTo() + ")";

                    } else if (t instanceof Deadline) {
                        Deadline d = (Deadline) t;
                        System.out.print("[D]");
                        time = " (by: " + d.getBy() + ")";
                    } else {
                        System.out.print("[T]");
                    }
                    System.out.print(t.getStatusIcon() + " ");
                    System.out.println(t.getDescription() + time);
                    // E.g.
                    // 1.[T][X] SHOWERRR
                    // 2.[D][X] submit file (by: Jan 29 2026 23:59)
                    // 3.[E][ ] SLEEEEEP (from: Jan 28 2026 23:59 to: Jan 29 2026 23:59)
                }

            } else if (command.equals("find")) {
                if (details.isEmpty()) {
                    System.out.println("\tFind needs a 'keyword'.");
                    System.out.println("\tE.g. Enter: find book");
                    ui.showLine();
                    continue;
                }

                String keyword = details.toLowerCase();
                System.out.println("\tMatching Tasks:");

                int shownCount = 1;
                for (int i = 0; i < tasks.size(); i++) {
                    Task t = tasks.get(i);

                    if (!t.getDescription().toLowerCase().contains(keyword)) {
                        continue;
                    }

                    System.out.print("\t" + shownCount + ".");

                    String time = "";
                    if (t instanceof Event) {
                        Event e = (Event) t;
                        System.out.print("[E]");
                        time = " (from: " + e.getFrom() + " to: " + e.getTo() + ")";
                    } else if (t instanceof Deadline) {
                        Deadline d = (Deadline) t;
                        System.out.print("[D]");
                        time = " (by: " + d.getBy() + ")";
                    } else {
                        System.out.print("[T]");
                    }

                    System.out.print(t.getStatusIcon() + " ");
                    System.out.println(t.getDescription() + time);

                    shownCount++;
                }

                if (shownCount == 1) {
                    System.out.println("\tNo matching tasks...");
                }

            } else if (command.equals("mark") || command.equals("unmark")) {
                //Mark or unmark task
                Task t = null;
                try {
                    //HANDLE EMPTY LIST, INVALID NUMBERS,  OOB
                    if (tasks.size() < 1) {
                        System.out.println("\tNo tasks! Stop skiving!");
                        ui.showLine();
                        continue;
                    }
                    int taskNum = Integer.parseInt(inputStrings[1]) - 1;
                    t = tasks.get(taskNum);
                } catch (Exception e) {
                    System.out.println("\tGive a valid task number! 1 to " + tasks.size());
                    ui.showLine();
                    continue;
                }
                if (command.equals("mark")) {
                    t.markAsDone();
                    storage.save(tasks.asArrayList());
                    System.out.println("\tOk, this one marked as done: ");
                } else {
                    t.markAsNotDone();
                    storage.save(tasks.asArrayList());
                    System.out.println("\tOk, this one marked as not done: ");
                }
                System.out.println("\t  " + t.getStatusIcon() + " " + t.getDescription());

            } else if (command.equals("event")) {
                Event event = null;

                try {
                    event = Parser.parseEvent(details);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    ui.showLine();
                    continue;
                }

                tasks.add(event);
                storage.save(tasks.asArrayList());

                System.out.println("\tOk, you need to: ");
                System.out.println("\t  [E][ ] " + event.getDescription()
                    + " (from: " + event.getFrom() + " to: " + event.getTo() + ")");

                System.out.println("\tYou have " + tasks.size() + " tasks.");

            } else if (command.equals("deadline")) {
                Deadline d = null;

                try {
                    d = Parser.parseDeadline(details);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    ui.showLine();
                    continue;
                }

                tasks.add(d);
                storage.save(tasks.asArrayList());

                System.out.println("\tOk, you need to: ");
                System.out.println("\t  [D][ ] " + d.getDescription() + " (by: " + d.getBy() + ")");

                System.out.println("\tYou have " + tasks.size() + " tasks.");

            } else if (command.equals("todo")) {
                //Todo task
                if (details.isEmpty()) {
                    //HANDLE MISSING PARAMS
                    System.out.println("\tA todo has a description.");
                    System.out.println("\tE.g. Enter: todo SHOWERRR");
                } else {
                    String description = details;
                    Task t = new Task(description);
                    tasks.add(t);
                    storage.save(tasks.asArrayList());

                    System.out.println("\tOk, you need to: ");
                    System.out.println("\t  [T][ ] " + description);

                    System.out.println("\tYou have " + tasks.size() + " tasks.");
                }

            } else if (command.equals("delete")) {

                //Delete task
                Task t = null;
                int taskNum = -1;
                try {
                    //HANDLE EMPTY LIST, INVALID NUMBERS,  OOB
                    if (tasks.size() < 1) {
                        System.out.println("\tNo tasks! Stop skiving!");
                        ui.showLine();
                        continue;
                    }
                    taskNum = Integer.parseInt(inputStrings[1]) - 1;
                    t = tasks.get(taskNum);
                } catch (Exception e) {
                    System.out.println("\tGive a valid task number! 1 to " + tasks.size());
                    ui.showLine();
                    continue;
                }
                String time = "";

                System.out.println("\tOk, I remove this task: ");
                if (t instanceof Event) {
                    Event e = (Event) t;
                    System.out.print("\t  [E]");
                    time = " (from: " + e.getFrom() + " to: " + e.getTo() + ")";

                } else if (t instanceof Deadline) {
                    Deadline d = (Deadline) t;
                    System.out.print("\t  [D]");
                    time = " (by: " + d.getBy() + ")";
                } else {
                    System.out.print("\t  [T]");
                }

                tasks.remove(taskNum);
                storage.save(tasks.asArrayList());

                System.out.print(t.getStatusIcon() + " ");
                System.out.println(t.getDescription() + time);
                System.out.println("\tYou have " + tasks.size() + " tasks.");

            } else {
                System.out.println("\t" + "WYD?? Try a valid command!");
                System.out.println("\tCommands: todo, deadline, event, ");
                System.out.println("\t          list, mark, unmark, delete, bye");
            }

            ui.showLine();

        }

        scanner.close();
    }

}

