import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    private static final DateTimeFormatter INPUT_FORMAT =
        DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT =
        DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Creates a new event task with given description, from, to.
     * Task is initialised as not done.
     *
     * @param description Description of task.
     * @param from Start time of event.
     * @param to End time of event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = LocalDateTime.parse(from, INPUT_FORMAT);
        this.to = LocalDateTime.parse(to, INPUT_FORMAT);

        if (this.from.isAfter(this.to)) {
            throw new IllegalArgumentException("Invalid time range: /from is after /to");
        }
    }
    public String getFrom() {
        return this.from.format(OUTPUT_FORMAT);
    }
    public String getTo() {
        return this.to.format(OUTPUT_FORMAT);
    }
    public String getFromInputFormat() {
        return this.from.format(INPUT_FORMAT);
    }
    public String getToInputFormat() {
        return this.to.format(INPUT_FORMAT);
    }
}
