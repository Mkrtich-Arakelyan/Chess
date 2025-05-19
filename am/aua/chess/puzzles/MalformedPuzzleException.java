package am.aua.chess.puzzles;

/**
 * Exception thrown when a chess puzzle is malformed.
 * This could mean that the puzzle input string is incorrectly formatted,
 * incomplete, or violates expected structure.
 */
public class MalformedPuzzleException extends Exception {

    /**
     * Default constructor.
     * Initializes the exception with a standard error message.
     */
    public MalformedPuzzleException() {
        super("Malformed puzzle.");
    }

    /**
     * Constructor with custom message.
     * Allows specification of a detailed error message.
     * @param message the error message describing the puzzle issue
     */
    public MalformedPuzzleException(String message) {
        super(message);
    }
}
