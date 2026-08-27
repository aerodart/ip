package ev;

/**
 * Represents an error caused by input E.V. cannot accept.
 * Checked (extends {@code Exception}) so callers must handle invalid user input explicitly.
 */
public class EvException extends Exception {
    public EvException(String message) {
        super(message);
    }
}