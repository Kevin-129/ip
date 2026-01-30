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

    /**
     * Runs the CowPay chatbot
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

