package cowpay;

import cowpay.task.Deadline;
import cowpay.task.Event;

/**
 * Parses user input into task objects
 */
public class Parser {

    /**
     * Parses the given input into a Event object
     *
     * @param input Event details
     * @return Event object
     * @throws IllegalArgumentException If the input format is invalid
     */
    public static Event parseEvent(String input) {
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
        }
    }

    /**
     * Parses the given input into a Deadline object
     *
     * @param input Deadline details
     * @return Deadline object
     * @throws IllegalArgumentException If the input format is invalid
     */
    public static Deadline parseDeadline(String input) {
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
        }
    }

}
