import java.util.Scanner;

public class CowPay {

    public static final String name = " CowPay";
    private static final String LINE = "\t____________________________________________________________"; 
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println(LINE);
        System.out.println("\tI'm " + name + "! Nice to meet you!");
        System.out.println("\tSay something...");
        System.out.println();

        while (true) {
            if (!scanner.hasNextLine()) {
                break; // Exit if no more inputhi
            }

            String next = scanner.nextLine().trim();

            if(next.equals("bye")) {
                System.out.println(LINE);
                System.out.println("\tBye! Don't come back!");
                System.out.println(LINE);
                break; // Exit
            } else {
                System.out.println(LINE);
                System.out.println("\t"+ next);
                System.out.println(LINE);
            }

        }

        scanner.close();
    }
}
