public class Deadline extends Task {
    protected String by;

    /**
     * Creates a new deadline task with given description and deadline.
     * Task is initialised as not done.
     *
     * @param description Description of task.
     * @param by Deadline of task.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public String getBy() {
        return this.by;
    }
}
