package cowpay;

import cowpay.task.Deadline;
import cowpay.task.Event;

/**
 * Parses user input into task objects
 */
public class Parser {

    private static final String EVENT_USAGE_MESSAGE =
        "An event has a description, /from and /to.\n"
        + "E.g. Enter: event SLEEEEEP /from 28/1/2026 2359 /to 29/1/2026 2359\n"
        + "Use the exact format! - \"/from\" and \"/to\"";

    private static final String DEADLINE_USAGE_MESSAGE =
        "A deadline has a description, /by.\n"
        + "E.g. Enter: deadline submit file /by 29/1/2026 2359\n"
        + "Use the exact format! - \"/by\"";

    /**
     * Parses the given input into a Event object
     *
     * @param input Event details
     * @return Event object
     * @throws IllegalArgumentException If the input format is invalid
     */
    public static Event parseEvent(String input) {
<<<<<<< HEAD
        try {
            String[] split1 = input.trim().split(" /from ", 2);
            String[] split2 = split1[1].trim().split(" /to ", 2);

            String description = split1[0].trim();
            String from = split2[0].trim();
            String to = split2[1].trim();

            //Description, from, and to cannot be empty
            assert !description.isEmpty() : "Event description cannot be empty!!";
            assert !from.isEmpty() : "Event 'from' time cannot be empty!!";
            assert !to.isEmpty() : "Event 'to' time cannot be empty!!";

            if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new IllegalArgumentException();
            }

            return new Event(description, from, to);

        } catch (Exception e) {
            throw new IllegalArgumentException(
                "An event has a description, /from and /to.\n"
                    + "E.g. Enter: event SLEEEEEP /from 28/1/2026 2359 /to 29/1/2026 2359\n"
                    + "Use the exact format! - \"/from\" and \"/to\""
            );
=======
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(EVENT_USAGE_MESSAGE);
>>>>>>> refs/rewritten/branch-A-CodeQuality
        }

        String trimmed = input.trim();

        String[] split1 = trimmed.split(" /from ", 2);
        if (split1.length < 2) {
            throw new IllegalArgumentException(EVENT_USAGE_MESSAGE);
        }

        String description = split1[0].trim();

        String[] split2 = split1[1].trim().split(" /to ", 2);
        if (split2.length < 2) {
            throw new IllegalArgumentException(EVENT_USAGE_MESSAGE);
        }

        String from = split2[0].trim();
        String to = split2[1].trim();

        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new IllegalArgumentException(EVENT_USAGE_MESSAGE);
        }

        return new Event(description, from, to);
    }

    /**
     * Parses the given input into a Deadline object
     *
     * @param input Deadline details
     * @return Deadline object
     * @throws IllegalArgumentException If the input format is invalid
     */
    public static Deadline parseDeadline(String input) {
<<<<<<< HEAD
        try {
            String[] split = input.trim().split(" /by ", 2);

            String description = split[0].trim();
            String by = split[1].trim();

            //Description and by cannot be empty
            assert !description.isEmpty() : "Deadline description cannot be empty!!";
            assert !by.isEmpty() : "Deadline 'by' time cannot be empty!!";

            if (description.isEmpty() || by.isEmpty()) {
                throw new IllegalArgumentException();
            }

            return new Deadline(description, by);

        } catch (Exception e) {
            throw new IllegalArgumentException(
                "A deadline has a description, /by.\n"
                    + "E.g. Enter: deadline submit file /by 29/1/2026 2359\n"
                    + "Use the exact format! - \"/by\""
            );
=======
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(DEADLINE_USAGE_MESSAGE);
>>>>>>> refs/rewritten/branch-A-CodeQuality
        }

        String trimmed = input.trim();

        String[] split = trimmed.split(" /by ", 2);
        if (split.length < 2) {
            throw new IllegalArgumentException(DEADLINE_USAGE_MESSAGE);
        }

        String description = split[0].trim();
        String by = split[1].trim();

        if (description.isEmpty() || by.isEmpty()) {
            throw new IllegalArgumentException(DEADLINE_USAGE_MESSAGE);
        }

        return new Deadline(description, by);
    }

    /**
     * Parses the task index from the given details string.
     * @param details Contains task number
     * @param taskCount Total number of tasks
     * @return Zero-based task index
     * @throws IllegalArgumentException If the task number is invalid
     */
    public static int parseTaskIndex(String details, int taskCount) {
        String message = invalidTaskNumberMessage(taskCount);

        if (details == null || details.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }

        int oneBasedTaskNum = -1;
        try {
            oneBasedTaskNum = Integer.parseInt(details.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(message);
        }

        int zeroBasedIndex = oneBasedTaskNum - 1;
        if (zeroBasedIndex < 0 || zeroBasedIndex >= taskCount) {
            throw new IllegalArgumentException(message);
        }

        return zeroBasedIndex;
    }

    private static String invalidTaskNumberMessage(int taskCount) {
        return "Give a valid task number! 1 to " + taskCount
                + "\nUse 'list' to see all tasks.";
    }

}
