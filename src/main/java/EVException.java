// Represents an error caused by input E.V. can't accept
public class EVException extends Exception {
    // Extending Exception instead of RuntimeException to make it a checked exception
    public EVException(String message) {
        super(message);
    }
}