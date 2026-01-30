package cowpay.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    private static final DateTimeFormatter INPUT_FORMAT =
        DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT =
        DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    protected LocalDateTime by;

    /**
     * Creates a new deadline task with given description and deadline.
     * Task is initialised as not done.
     *
     * @param description Description of task.
     * @param by Deadline of task.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDateTime.parse(by, INPUT_FORMAT);
    }

    public String getBy() {
        return this.by.format(OUTPUT_FORMAT);
    }

    public String getByInputFormat() {
        return this.by.format(INPUT_FORMAT);
    }
}

