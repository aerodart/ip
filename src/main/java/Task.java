public class Task {
    protected String description;
    protected boolean isDone;
    protected String type;  // "T" for todo, "D" for deadline and "E" for event
    protected String by;    // For deadlines, else null
    protected String from;  // For events only, else null
    protected String to;    // For events only, else null    

    // todo creation without date or time attached
    public Task(String type, String description) {
        this.type = type;
        this.description = description;
        this.isDone = false;
    }

    // Deadline creation
    public Task(String type, String description, String by) {
        this(type, description);
        this.by = by;
    }

    // Event creation with from and to times
    public Task(String type, String description, String from, String to) {
        this(type, description);
        this.from = from;
        this.to = to;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    @Override
    public String toString() {
        String details = "";
        if (type.equals("D")) {
            details = " (by: " + by + ")";
        } else if (type.equals("E")) {
            details = " (from: " + from + " to: " + to + ")";
        }
        return "[" + type + "][" + getStatusIcon() + "] " + description + details;
    }
}