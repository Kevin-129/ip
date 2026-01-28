public class Event extends Task {
    protected String from;
    protected String to;

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
        this.from = from;
        this.to = to;
    }
    public String getFrom() {
        return this.from;
    }
    public String getTo() {
        return this.to;
    }
}
