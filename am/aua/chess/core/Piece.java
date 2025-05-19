package am.aua.chess.core;

/**
 * Abstract base class for all chess pieces.
 * Provides common functionality such as color tracking and cloning.
 */
public abstract class Piece implements Cloneable {

    /** The color of the piece (WHITE or BLACK). */
    private Chess.PieceColor color;

    /**
     * Constructs a piece with the specified color.
     * @param color the color of the piece
     */
    public Piece(Chess.PieceColor color) {
        this.color = color;
    }

    /**
     * Default constructor.
     * Initializes a white piece by default.
     */
    public Piece() {
        this(Chess.PieceColor.WHITE);
    }

    /**
     * Abstract method to be implemented by subclasses.
     * Computes all legal destinations for the piece from a given position.
     * @param chess the current chess board state
     * @param p the current position of the piece
     * @return array of valid destination positions
     */
    public abstract Position[] allDestinations(Chess chess, Position p);

    /**
     * Returns the color of the piece.
     * @return the color (WHITE or BLACK)
     */
    public final Chess.PieceColor getPieceColor() {
        return this.color;
    }

    /**
     * Creates a shallow copy of the piece using {@code Object.clone()}.
     * @return a cloned Piece object, or null if cloning is not supported
     */
    public Piece clone() {
        try {
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
