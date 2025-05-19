package am.aua.chess.core;

/**
 * Exception thrown when the number of kings in a chess arrangement is invalid.
 * This occurs if there is not exactly one white king and one black king.
 * Extends {@link IllegalArrangementException}.
 */
public class InvalidNumberOfKingsException extends IllegalArrangementException {

    /**
     * Default constructor.
     * Initializes the exception with a predefined message.
     */
    public InvalidNumberOfKingsException() {
        super("In a game of classic chess, there has to be exactly one king of each color.");
    }

    /**
     * Constructor with custom message.
     * Allows the caller to specify a custom explanation for the exception.
     * @param message the custom error message
     */
    public InvalidNumberOfKingsException(String message) {
        super(message);
    }
}
