import java.lang.reflect.Array;
import java.util.*;

public class CowPay {

    public static final String name = " CowPay";
    private static final String LINE = "\t____________________________________________________________"; 
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<String> tasks = new ArrayList<>();

        System.out.println(LINE);
        System.out.println("\tI'm " + name + "! Nice to meet you!");
        System.out.println("\tWat u wan me do?");
        System.out.println();

        while (true) {
            if (!scanner.hasNextLine()) {
                break; // Exit if no more input
            }

            String next = scanner.nextLine().trim();

            if(next.equals("bye")) {
                System.out.println(LINE);
                System.out.println("\tBye!! Don't come back!");
                System.out.println(LINE);
                break; // Exit

            } else if (next.equals("list")) {
                //List all tasks
                System.out.println(LINE);
                System.out.println("\tOk I will do all these: ");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println("\t" + (i + 1) + ". " + tasks.get(i));
                }
                System.out.println(LINE);

            } else {
                //Add task to tasks
                System.out.println(LINE);
                tasks.add(next);
                System.out.println("\tOk I will: " + next);
                System.out.println(LINE);
            }

        }

        scanner.close();
    }
}
