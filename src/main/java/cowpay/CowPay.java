package cowpay;

import java.time.LocalDateTime;

import cowpay.task.Deadline;
import cowpay.task.Event;
import cowpay.task.Task;


/**
 * The main class for the CowPay chatbot
 */
public class CowPay {

    private static final String NAME = "CowPay";
    private static final String STORAGE_FILE_PATH = "data/cowpay.txt";

    private static final String COMMANDS = "todo, deadline, event, list, remind, find, mark, unmark, delete";
    private static final String NO_TASKS_MESSAGE = "No tasks!! STOP SKIVING!!";

    private final Storage storage;
    private final TaskList tasks;

    /**
     * Constructor for CowPay
     */
    public CowPay() {
        this.storage = new Storage(STORAGE_FILE_PATH);
        this.tasks = new TaskList(storage.loadTasksFromFile());
    }

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
        case "remind":
            return reminder();
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
        return "Hello! I'm " + NAME + ". Wat u wan?\nCommands: " + COMMANDS;
    }

    /**
     * @return Lists of all tasks (String)
     */
    private String listTasks() {

        //Task list should not be null
        assert this.tasks != null : "Task list should not be null!!";

        if (this.tasks.isEmpty()) {
            return NO_TASKS_MESSAGE;
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
     * @return Lists of all deadlines and events (String)
     */
    public String reminder() {

        if (this.tasks.isEmpty()) {
            return NO_TASKS_MESSAGE;
        }
        StringBuilder taskListSb = new StringBuilder("REMEMBER TO DO THESE: \n");
        LocalDateTime now = LocalDateTime.now();
        int shownCount = 1;

        try {
            for (int i = 0; i < this.tasks.size(); i++) {
                Task t = this.tasks.get(i);
                if (t.isDone()) {
                    continue;
                }
                if (t instanceof Deadline) {
                    Deadline d = (Deadline) t;
                    LocalDateTime by = Parser.parseDateTime(d.getByInputFormat());
                    if (!by.isBefore(now)) { // by >= now
                        taskListSb.append(shownCount).append(". ")
                            .append(taskToLineSb(t)).append("\n");
                        shownCount++;
                    }

                } else if (t instanceof Event) {
                    Event e = (Event) t;
                    LocalDateTime from = Parser.parseDateTime(e.getFromInputFormat());
                    if (!from.isBefore(now)) { // from >= now
                        taskListSb.append(shownCount).append(". ")
                            .append(taskToLineSb(t)).append("\n");
                        shownCount++;
                    }
                }
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        if (shownCount == 1) {
            return NO_TASKS_MESSAGE;
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

        //Keyword should not be null or empty
        assert keyword != null && !keyword.isEmpty() : "Search keyword should not be null or empty!!";

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


    // E.g.
    // [T][X] SHOWERRR
    // [D][X] submit file (by: Jan 29 2026 23:59)
    // [E][ ] SLEEEEEP (from: Jan 28 2026 23:59 to: Jan 29 2026 23:59)
    /**
     * Converts a task to string builder representation
     *
     * @param index index of the task
     * @param t task
     * @return string builder representation of the task
     */
    private StringBuilder taskToLineSb(Task t) {
        //Task should not be null
        assert t != null : "Task should not be null!!";

        StringBuilder sb = new StringBuilder();

        if (t instanceof Event) {
            Event e = (Event) t;
            sb.append("[E] ").append(t.getStatusIcon())
                .append(" ")
                .append(t.getDescription())
                .append(" (from: ").append(e.getFrom())
                .append(" to: ").append(e.getTo())
                .append(")");

        } else if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            sb.append("[D] ").append(t.getStatusIcon())
                .append(" ")
                .append(t.getDescription())
                .append(" (by: ").append(d.getBy())
                .append(")");

        } else {
            sb.append("[T] ").append(t.getStatusIcon())
                .append(" ")
                .append(t.getDescription());
        }
        return sb;
    }

    /**
     * Saves tasks to storage
     */
    private void saveTasksToStorage() {
        this.storage.saveTasksToFile(this.tasks.asArrayList());
    }

    /**
     * Marks a task as done
     *
     * @param details task number as string
     * @return response message
     */
    private String markTaskAsDone(String details) {

        if (this.tasks.isEmpty()) {
            return NO_TASKS_MESSAGE;
        }

        Task t;
        try {
            int taskIndex = Parser.parseTaskIndex(details, this.tasks.size());
            t = this.tasks.get(taskIndex);
        } catch (Exception e) {
            return e.getMessage();
        }

        t.markAsDone();
        saveTasksToStorage();

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

        if (this.tasks.isEmpty()) {
            return NO_TASKS_MESSAGE;
        }

        Task t;
        try {
            int taskIndex = Parser.parseTaskIndex(details, this.tasks.size());
            t = this.tasks.get(taskIndex);
        } catch (Exception e) {
            return e.getMessage();
        }

        t.markAsNotDone();
        saveTasksToStorage();

        return "Ok, this one marked as not done: \n  "
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

        //Event should not be null after parsing
        assert event != null : "Event should not be null!";

        this.tasks.add(event);
        saveTasksToStorage();

        return new StringBuilder()
            .append("Ok, you need to: \n  ")
            .append(taskToLineSb(event))
            .append("\nYou have ")
            .append(this.tasks.size())
            .append(" tasks.")
            .toString();
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

        //Deadline should not be null after parsing
        assert d != null : "Deadline should not be null!";

        this.tasks.add(d);
        saveTasksToStorage();

        return new StringBuilder()
            .append("Ok, you need to: \n  ")
            .append(taskToLineSb(d))
            .append("\nYou have ")
            .append(this.tasks.size())
            .append(" tasks.")
            .toString();
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

        //Task should not be null
        assert t != null : "Task should not be null!";

        this.tasks.add(t);
        saveTasksToStorage();

        return new StringBuilder()
            .append("Ok, you need to: \n  ")
            .append(taskToLineSb(t))
            .append("\nYou have ")
            .append(this.tasks.size())
            .append(" tasks.")
            .toString();
    }

    /**
     * Deletes a task
     *
     * @param details task number as string
     * @return response message
     */
    private String deleteTask(String details) {

        if (this.tasks.isEmpty()) {
            return NO_TASKS_MESSAGE;
        }

        Task t;
        int taskIndex;
        try {
            taskIndex = Parser.parseTaskIndex(details, this.tasks.size());
            t = this.tasks.get(taskIndex);
        } catch (Exception e) {
            return e.getMessage();
        }

        this.tasks.remove(taskIndex);
        saveTasksToStorage();

        return new StringBuilder()
            .append("Ok, I remove this task: \n  ")
            .append(taskToLineSb(t))
            .append("\nYou have ")
            .append(this.tasks.size())
            .append(" tasks.")
            .toString();
    }

    /**
     * Handles invalid commands
     *
     * @param command invalid command
     * @return Invalid command message
     */
    private String invalidCommand(String command) {
        return command + "??? Try a valid command!\nCommands: " + COMMANDS;
    }

}

