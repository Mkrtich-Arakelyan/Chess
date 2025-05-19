package am.aua.chess.core;

import java.util.ArrayList;

/**
 * Represents a Bishop chess piece.
 * Inherits behavior from the abstract Piece class.
 */
public class Bishop extends Piece {

    /**
     * Default constructor.
     * Initializes a Bishop without assigning a color.
     */
    public Bishop() {
        super();
    }

    /**
     * Constructor with color.
     * @param color the color of the Bishop (WHITE or BLACK)
     */
    public Bishop(Chess.PieceColor color) {
        super(color);
    }

    /**
     * Returns the string representation of the Bishop.
     * 'B' for white, 'b' for black.
     * @return "B" or "b"
     */
    public String toString() {
        if (this.getPieceColor() == Chess.PieceColor.WHITE)
            return "B";
        else
            return "b";
    }

    /**
     * Returns all valid destination positions this Bishop can move to
     * from the given position on the current chess board.
     * @param chess the chess game state
     * @param p the current position of the Bishop
     * @return an array of possible destination positions
     */
    public Position[] allDestinations(Chess chess, Position p) {
        return Bishop.reachablePositions(chess, p);
    }

    /**
     * Computes all reachable diagonal positions from the given position.
     * This method is also used by the Queen (hence it's static).
     * @param chess the current chess board state
     * @param p the starting position
     * @return an array of all reachable positions for a Bishop
     */
    static Position[] reachablePositions(Chess chess, Position p) {
        // Accessed by Queens, does not have to be public
        ArrayList<Position> result = new ArrayList<>();

        // Directions: top-right, top-left, bottom-right, bottom-left
        int[] rankOffsets = {1, -1, 1, -1};
        int[] fileOffsets = {1, 1, -1, -1};

        // Try each diagonal direction
        for (int d = 0; d < 4; d++) {
            int i = p.getRank() + rankOffsets[d];
            int j = p.getFile() + fileOffsets[d];

            // Traverse in the current diagonal direction
            while (i >= 0 && i < Chess.BOARD_RANKS
                    && j >= 0 && j < Chess.BOARD_FILES) {

                Position current = Position.generateFromRankAndFile(i, j);

                if (chess.isEmpty(current)) {
                    // The square is empty; Bishop can move here
                    result.add(current);
                } else {
                    // The square has a piece
                    // Check if it's an opponent's piece
                    if (chess.getPieceAt(current).getPieceColor()
                            != chess.getPieceAt(p).getPieceColor())
                        result.add(current); // Can capture
                    break; // Cannot move past another piece
                }

                // Move to the next square in this direction
                i += rankOffsets[d];
                j += fileOffsets[d];
            }
        }

        // Convert the result to an array and return
        return result.toArray(new Position[]{});
    }
}
