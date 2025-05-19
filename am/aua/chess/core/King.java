package am.aua.chess.core;

import java.util.ArrayList;

/**
 * Represents a King chess piece.
 * Tracks whether the King has moved (important for castling logic).
 */
public class King extends Piece {

    /** Indicates whether the King has moved from its original position. */
    private boolean hasMoved;

    /**
     * Default constructor.
     * Initializes a white King that has not moved.
     */
    public King() {
        this(Chess.PieceColor.WHITE);
    }

    /**
     * Constructor with specified color.
     * Initializes a King with given color that has not moved.
     * @param color the color of the King
     */
    public King(Chess.PieceColor color) {
        this(color, false);
    }

    /**
     * Constructor with specified color and move status.
     * @param color the color of the King
     * @param hasMoved true if the King has already moved, false otherwise
     */
    public King(Chess.PieceColor color, boolean hasMoved) {
        super(color);
        this.hasMoved = hasMoved;
    }

    /**
     * Checks if the King has moved.
     * @return true if the King has moved; false otherwise
     */
    public boolean getHasMoved() {
        return this.hasMoved;
    }

    /**
     * Sets whether the King has moved.
     * @param hasMoved true if the King has moved
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * Returns a string representation of the King.
     * Uses 'K' for unmoved white king, 'L' for moved white king,
     * 'k' for unmoved black king, 'l' for moved black king.
     * @return string representation of the piece
     */
    public String toString() {
        if (this.getPieceColor() == Chess.PieceColor.WHITE)
            if (this.hasMoved)
                return "L";
            else
                return "K";
        else
        if (this.hasMoved)
            return "l";
        else
            return "k";
    }

    /**
     * Returns all legal destination positions for the King from its current location.
     * Does not currently include check detection or castling logic.
     *
     * @param chess the current chess board state
     * @param p the current position of the King
     * @return array of valid destination positions
     */
    public Position[] allDestinations(Chess chess, Position p) {
        // 8 possible surrounding squares (without check consideration)
        int[][] pattern = {
                {p.getRank() - 1, p.getFile() - 1},
                {p.getRank(),     p.getFile() - 1},
                {p.getRank() + 1, p.getFile() - 1},

                {p.getRank() - 1, p.getFile()},
                {p.getRank() + 1, p.getFile()},

                {p.getRank() - 1, p.getFile() + 1},
                {p.getRank(),     p.getFile() + 1},
                {p.getRank() + 1, p.getFile() + 1}
        };

        ArrayList<Position> result = new ArrayList<>();

        // Add legal positions that are either empty or contain opponent's piece
        for (int i = 0; i < pattern.length; i++) {
            Position potential = Position.generateFromRankAndFile(pattern[i][0], pattern[i][1]);
            if (potential != null && (chess.isEmpty(potential)
                    || chess.getPieceAt(potential).getPieceColor()
                    != chess.getPieceAt(p).getPieceColor()))
                result.add(potential);
        }

        return result.toArray(new Position[]{});
    }
}
