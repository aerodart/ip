package ev.task;

/**
 * Represents a task that runs from one date or time to another.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Constructs an event with the given description and time range.
     *
     * @param description the description of the event.
     * @param from the date or time the event starts.
     * @param to the date or time the event ends.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
