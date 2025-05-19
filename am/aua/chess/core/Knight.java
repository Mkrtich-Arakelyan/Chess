package am.aua.chess.core;

import java.util.ArrayList;

/**
 * Represents a Knight chess piece.
 * Inherits from the abstract {@link Piece} class.
 */
public class Knight extends Piece {

    /**
     * Default constructor.
     * Initializes a Knight with no color (to be set later).
     */
    public Knight() {
        super();
    }

    /**
     * Constructor with color.
     * Initializes a Knight with the specified color.
     * @param color the color of the Knight (WHITE or BLACK)
     */
    public Knight(Chess.PieceColor color) {
        super(color);
    }

    /**
     * Returns the string representation of the Knight.
     * "N" for white, "n" for black.
     * @return string representing the Knight
     */
    public String toString() {
        if (this.getPieceColor() == Chess.PieceColor.WHITE)
            return "N";
        else
            return "n";
    }

    /**
     * Returns all legal destination positions for the Knight from its current location.
     * @param chess the current chess board state
     * @param p the Knight's current position
     * @return array of valid destination positions
     */
    public Position[] allDestinations(Chess chess, Position p) {
        // 8 possible L-shaped moves (2 by 1 or 1 by 2)
        int[][] pattern = {
                {p.getRank() + 2, p.getFile() + 1},
                {p.getRank() + 2, p.getFile() - 1},
                {p.getRank() - 2, p.getFile() + 1},
                {p.getRank() - 2, p.getFile() - 1},

                {p.getRank() + 1, p.getFile() + 2},
                {p.getRank() + 1, p.getFile() - 2},
                {p.getRank() - 1, p.getFile() + 2},
                {p.getRank() - 1, p.getFile() - 2}
        };

        ArrayList<Position> result = new ArrayList<>();

        // Check each potential destination
        for (int i = 0; i < pattern.length; i++) {
            Position potential = Position.generateFromRankAndFile(pattern[i][0], pattern[i][1]);

            // Add position if it's on the board and either empty or occupied by opponent
            if (potential != null &&
                    (chess.isEmpty(potential)
                            || chess.getPieceAt(potential).getPieceColor()
                            != chess.getPieceAt(p).getPieceColor()))
                result.add(potential);
        }

        return result.toArray(new Position[]{});
    }
}
