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
        System.out.println();

        while (true) {
            if (!scanner.hasNextLine()) {
                break; // Exit if no more input
            }

            String inputStrings[] = scanner.nextLine().trim().split(" ");
            String next = inputStrings[0];

            if(next.equals("bye")) {
                System.out.println(LINE);
                System.out.println("\tBye!! Don't come back!");
                System.out.println(LINE);
                break; // Exit

            } else if (next.equals("list")) {
                //List all tasks
                System.out.println(LINE);
                System.out.println("\tYou need do these: ");
                for (int i = 0; i < tasks.size(); i++) {
                    Task t = tasks.get(i);
                    System.out.println("\t" + (i + 1) + "." + t.getStatusIcon() + " " + t.getDescription());
                }
                System.out.println(LINE);

            } else if (next.equals("mark") || next.equals("unmark")) {
                //Mark or unmark task
                if (inputStrings.length < 2) {
                    //Check if valid task number is given
                    System.out.println(LINE);
                    System.out.println("\tGive a valid task number.");
                    System.out.println(LINE);
                    continue;

                } else {
                    int taskNum = Integer.parseInt(inputStrings[1]) - 1;
                    if (taskNum < 0 || taskNum >= tasks.size()) {
                        System.out.println(LINE);
                        System.out.println("\tGive a valid task number.");
                        System.out.println(LINE);
                        continue;

                    } else {
                        Task t = tasks.get(taskNum);
                        if (next.equals("mark")) {
                            t.markAsDone();
                            System.out.println(LINE);
                            System.out.println("\tOk this one marked as done: ");
                            System.out.println("\t  " + t.getStatusIcon() + " " + t.getDescription());
                            System.out.println(LINE);
                            
                        } else {
                            t.markAsNotDone();
                            System.out.println(LINE);
                            System.out.println("\tOk this one marked as not done: ");
                            System.out.println("\t  " + t.getStatusIcon() + " " + t.getDescription());
                            System.out.println(LINE);
                        }
                    }
                    
                }
                
            } else {
                //Add task to tasks
                System.out.println(LINE);
                Task t = new Task(next);
                tasks.add(t);
                System.out.println("\tOk you need to: " + next);
                System.out.println(LINE);
            }

        }

        scanner.close();
    }
}
