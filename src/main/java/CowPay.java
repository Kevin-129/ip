import java.util.Scanner;

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
            String next = inputStrings[0];

            ui.showLine();

            if (next.equals("bye")) {
                ui.showBye();
                break; // Exit

            } else if (next.equals("list")) {
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
                    // 1.[T][ ] taskDescription
                    // 2.[D][X] taskDescription (by: time)
                    // 3.[E][ ] taskDescription (from: time1 to: time2)
                }

            } else if (next.equals("mark") || next.equals("unmark")) {
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
                if (next.equals("mark")) {
                    t.markAsDone();
                    storage.save(tasks.asArrayList());
                    System.out.println("\tOk, this one marked as done: ");
                } else {
                    t.markAsNotDone();
                    storage.save(tasks.asArrayList());
                    System.out.println("\tOk, this one marked as not done: ");
                }
                System.out.println("\t  " + t.getStatusIcon() + " " + t.getDescription());

            } else if (next.equals("event")) {
                //Event task
                String description = "";
                String from = "";
                String to = "";

                //Second part of input split into further parts
                try {
                    //HANDLE MISSING PARAMS, INVALID FORMAT
                    String[] split1 = inputStrings[1].trim().split(" /from ", 2);
                    String[] split2 = split1[1].trim().split(" /to ", 2);
                    description = split1[0];
                    from = split2[0];
                    to = split2[1];
                } catch (Exception e) {
                    System.out.println("\tAn event has a description, /from and /to.");
                    System.out.println("\tE.g. Enter: event SLEEEEEP /from 28/1/2026 2359 /to 29/1/2026 2359");
                    System.out.println("\tUse the exact format! - \"/from\" and \"/to\"");
                    ui.showLine();
                    continue;
                }

                Event e = new Event(description, from, to);
                tasks.add(e);
                storage.save(tasks.asArrayList());

                System.out.println("\tOk, you need to: ");
                System.out.println("\t  [E][ ] " + description + " (from: " + e.getFrom() + " to: " + e.getTo() + ")");

                System.out.println("\tYou have " + tasks.size() + " tasks.");

            } else if (next.equals("deadline")) {
                //Deadline task
                String description = "";
                String by = "";

                //Second part of input split into further parts
                try {
                    //HANDLE MISSING PARAMS, INVALID FORMAT
                    String[] split = inputStrings[1].trim().split(" /by ", 2);
                    description = split[0];
                    by = split[1];
                } catch (Exception e) {
                    System.out.println("\tAn deadline has a description, /by.");
                    System.out.println("\tE.g. Enter: deadline submit file /by 29/1/2026 2359");
                    System.out.println("\tUse the exact format! - \"/by\"");
                    ui.showLine();
                    continue;
                }
                Deadline d = new Deadline(description, by);
                tasks.add(d);
                storage.save(tasks.asArrayList());

                System.out.println("\tOk, you need to: ");
                System.out.println("\t  [D][ ] " + description + " (by: " + d.getBy() + ")");

                System.out.println("\tYou have " + tasks.size() + " tasks.");

            } else if (next.equals("todo")) {
                //Todo task
                if (inputStrings.length < 2) {
                    //HANDLE MISSING PARAMS
                    System.out.println("\tA todo has a description.");
                    System.out.println("\tE.g. Enter: todo SHOWERRR");
                } else {
                    String description = inputStrings[1];
                    Task t = new Task(description);
                    tasks.add(t);
                    storage.save(tasks.asArrayList());

                    System.out.println("\tOk, you need to: ");
                    System.out.println("\t  [T][ ] " + description);

                    System.out.println("\tYou have " + tasks.size() + " tasks.");
                }

            } else if (next.equals("delete")) {

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

