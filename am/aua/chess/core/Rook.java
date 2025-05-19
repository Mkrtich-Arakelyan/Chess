package am.aua.chess.core;

import java.util.ArrayList;

/**
 * Represents a Rook chess piece.
 * Tracks whether the rook has moved (important for castling).
 */
public class Rook extends Piece {

    /** Indicates whether the rook has moved (used for castling logic). */
    private boolean hasMoved;

    /**
     * Default constructor.
     * Initializes a white rook that has not moved.
     */
    public Rook() {
        this(Chess.PieceColor.WHITE);
    }

    /**
     * Constructor with specified color.
     * Initializes a rook with the given color that has not moved.
     * @param color the color of the rook (WHITE or BLACK)
     */
    public Rook(Chess.PieceColor color) {
        this(color, false);
    }

    /**
     * Constructor with specified color and move status.
     * @param color the color of the rook
     * @param hasMoved whether the rook has moved
     */
    public Rook(Chess.PieceColor color, boolean hasMoved) {
        super(color);
        this.hasMoved = hasMoved;
    }

    /**
     * Checks whether the rook has moved.
     * @return true if the rook has moved, false otherwise
     */
    public boolean getHasMoved() {
        return this.hasMoved;
    }

    /**
     * Sets the moved status of the rook.
     * @param hasMoved true if the rook has moved
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * Returns a string representation of the rook.
     * "R" = white, "r" = black, "S/s" = moved rook (used for castling checks).
     * @return the string representation of the rook
     */
    public String toString() {
        if (this.getPieceColor() == Chess.PieceColor.WHITE)
            if (this.hasMoved)
                return "S";
            else
                return "R";
        else
        if (this.hasMoved)
            return "s";
        else
            return "r";
    }

    /**
     * Returns all legal destination positions for the rook from the given position.
     * @param chess the current state of the chessboard
     * @param p the current position of the rook
     * @return array of valid destination positions
     */
    public Position[] allDestinations(Chess chess, Position p) {
        return Rook.reachablePositions(chess, p);
    }

    /**
     * Computes all reachable positions for a rook from a given square.
     * Rooks move horizontally and vertically until they hit a piece or edge.
     * Shared by Queen class, so kept package-private and static.
     *
     * @param chess the chessboard state
     * @param p the rook's position
     * @return array of reachable positions
     */
    static Position[] reachablePositions(Chess chess, Position p) {
        // Directions: up, down, right, left
        int[] rankOffsets = {1, -1, 0, 0};
        int[] fileOffsets = {0, 0, 1, -1};

        ArrayList<Position> result = new ArrayList<>();

        // Iterate over the 4 directions
        for (int d = 0; d < 4; d++) {
            int i = p.getRank() + rankOffsets[d];
            int j = p.getFile() + fileOffsets[d];

            // Traverse in the given direction until blocked
            while (i >= 0 && i < Chess.BOARD_RANKS && j >= 0 && j < Chess.BOARD_FILES) {
                Position current = Position.generateFromRankAndFile(i, j);

                if (chess.isEmpty(current)) {
                    // Empty square → valid move
                    result.add(current);
                } else {
                    // Occupied → check for opponent's piece
                    if (chess.getPieceAt(current).getPieceColor() != chess.getPieceAt(p).getPieceColor())
                        result.add(current); // Can capture
                    break; // Stop at first obstacle
                }

                // Move further in the current direction
                i += rankOffsets[d];
                j += fileOffsets[d];
            }
        }

        return result.toArray(new Position[]{});
    }
}
