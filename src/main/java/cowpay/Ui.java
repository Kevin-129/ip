package cowpay;

import java.util.Scanner;

/**
 * Handles user interactions
 */
public class Ui {
    private static final String LINE = "\t____________________________________________________________";

    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Shows welcome message
     *
     * @param name name of bot
     */
    public void showWelcome(String name) {
        System.out.println(LINE);
        System.out.println("\tHi, I'm " + name + "! Nice to meet you!");
        System.out.println("\tWat u wan me do?");
        System.out.println(LINE);
        System.out.println();
    }

    /**
     * Shows goodbye message
     */
    public void showBye() {
        System.out.println("\tBye!! Don't come back!");
    }

    /**
     * Reads a command from the user
     *
     * @param scanner The scanner to read input from
     * @return The user's command
     */
    public String readCommand(Scanner scanner) {
        return scanner.nextLine();
    }
}
