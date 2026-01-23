import java.util.*;

public class CowPay {

    public static final String name = "CowPay";
    private static final String LINE = "\t____________________________________________________________"; 
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println(LINE);
        System.out.println("\tHi baby, I'm " + name + "! Nice to meet you!");
        System.out.println("\tWat u wan me do?");
        System.out.println(LINE);
        System.out.println();

        while (true) {
            if (!scanner.hasNextLine()) {
                break; // Exit if no more input
            }
            String inputStrings[] = scanner.nextLine().trim().split(" ", 2);
            //Split on first space only
            String next = inputStrings[0];

            System.out.println(LINE);

            if(next.equals("bye")) {
                System.out.println("\tBye!! Don't come back!");
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
                        time = e.getFromAndTo();

                    } else if (t instanceof Deadline) {
                        Deadline d = (Deadline) t;
                        System.out.print("[D]");
                        time = d.getBy();
                    } else {
                        System.out.print("[T]");
                    }
                    System.out.print(t.getStatusIcon() + " ");
                    System.out.println(t.getDescription() + time);

                }

            } else if (next.equals("mark") || next.equals("unmark")) {
               
                //Mark or unmark task
                Task t;
                try {
                    //HANDLE EMPTY LIST, INVALID NUMBERS,  OOB
                    if (tasks.size() < 1) {
                        System.out.println("\tNo tasks! Stop skiving!");
                        System.out.println(LINE);
                        continue;
                    }
                    int taskNum = Integer.parseInt(inputStrings[1]) - 1;
                    t = tasks.get(taskNum);
                } catch (Exception e) {
                    System.out.println("\tGive a valid task number! 1 to " + tasks.size());
                    System.out.println(LINE);
                    continue;
                }
            
                if (next.equals("mark")) {
                    t.markAsDone();
                    System.out.println("\tOk this one marked as done: ");
                } else {
                    t.markAsNotDone();
                    System.out.println("\tOk this one marked as not done: ");                
                }
                System.out.println("\t  " + t.getStatusIcon() + " " + t.getDescription());
                

            } else if (next.equals("event")) {
                //Event task
                String description;
                String from;
                String to;

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
                    System.out.println("\tE.g. Enter: event project meeting /from Mon 2pm /to 4pm");
                    System.out.println("\tUse the exact format! - \"/from\" and \"/to\"");
                    System.out.println(LINE);
                    continue;
                }

                Event e = new Event(description, from, to);
                tasks.add(e);
                System.out.println("\tOk you need to: ");
                System.out.println("\t  [E][ ] " + description + e.getFromAndTo());

                System.out.println("\tYou have " + tasks.size() + " tasks.");

            } else if (next.equals("deadline")) {
                //Deadline task
                String description;
                String by;

                //Second part of input split into further parts
                try {
                    //HANDLE MISSING PARAMS, INVALID FORMAT
                    String[] split = inputStrings[1].trim().split(" /by ", 2);
                    description = split[0];
                    by = split[1];
                } catch (Exception e) {
                    System.out.println("\tAn deadline has a description, /by.");
                    System.out.println("\tE.g. Enter: deadline return book /by Sunday");
                    System.out.println("\tUse the exact format! - \"/by\"");
                    System.out.println(LINE);
                    continue;
                }
                
                Deadline d = new Deadline(description, by);
                tasks.add(d);
                System.out.println("\tOk you need to: ");
                System.out.println("\t  [D][ ] " + description + d.getBy());

                System.out.println("\tYou have " + tasks.size() + " tasks.");
                

            } else if (next.equals("todo")) {
                //Todo task
                if (inputStrings.length < 2) {
                    //HANDLE MISSING PARAMS
                    System.out.println("\tA todo has a description.");
                    System.out.println("\tE.g. Enter: todo borrow book");
                } else {
                    String description = inputStrings[1];
                    Task t = new Task(description);
                    tasks.add(t);
                    System.out.println("\tOk you need to: ");
                    System.out.println("\t  [T][ ] " + description);

                    System.out.println("\tYou have " + tasks.size() + " tasks.");
                }

            } else {
                System.out.println("\t" + "WYD?? Try a valid command!");
                System.out.println("\tCommands: todo, deadline, event, list, mark, unmark, bye");
            }

            System.out.println(LINE);

        }

        scanner.close();
    }
}
