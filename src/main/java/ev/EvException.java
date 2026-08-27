package ev;

// Represents an error caused by input E.V. can't accept
public class EvException extends Exception {
    // Extending Exception instead of RuntimeException to make it a checked exception
    public EvException(String message) {
        super(message);
    }
}