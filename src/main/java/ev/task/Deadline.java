package ev.task;

/**
 * Represents a task that must be done before a given date or time.
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Constructs a deadline with the given description and due time.
     *
     * @param description the description of the task.
     * @param by the date or time the task is due by.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toFileString() {
        return "D | " + super.toFileString() + " | " + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
