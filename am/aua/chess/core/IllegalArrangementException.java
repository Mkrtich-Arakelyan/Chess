package am.aua.chess.core;

/**
 * Exception thrown when a string representation of a chessboard arrangement is invalid.
 * This could include cases such as incorrect length or illegal piece placement.
 */
public class IllegalArrangementException extends Exception {

    /**
     * Default constructor.
     * Initializes the exception with a default error message.
     */
    public IllegalArrangementException() {
        super("The string does not represent a valid arrangement of pieces on a board.");
    }

    /**
     * Constructor with custom message.
     * Allows the caller to specify a custom error message.
     * @param message the custom error message describing the issue
     */
    public IllegalArrangementException(String message) {
        super(message);
    }
}
