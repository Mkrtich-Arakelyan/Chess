package am.aua.chess.core;

import java.util.ArrayList;

/**
 * Represents a Queen chess piece.
 * Combines the movement logic of both the Rook and the Bishop.
 */
public class Queen extends Piece {

    /**
     * Default constructor.
     * Initializes a white Queen by default.
     */
    public Queen() {
        super();
    }

    /**
     * Constructor with specified color.
     * Initializes a Queen with the given color.
     * @param color the color of the Queen (WHITE or BLACK)
     */
    public Queen(Chess.PieceColor color) {
        super(color);
    }

    /**
     * Returns a string representation of the Queen.
     * "Q" for white, "q" for black.
     * @return string representing the piece
     */
    public String toString() {
        if (this.getPieceColor() == Chess.PieceColor.WHITE)
            return "Q";
        else
            return "q";
    }

    /**
     * Returns all legal destinations for the Queen from the given position.
     * The Queen moves like both a Rook and a Bishop.
     * @param chess the current chess board state
     * @param p the current position of the Queen
     * @return array of all reachable positions
     */
    public Position[] allDestinations(Chess chess, Position p) {
        ArrayList<Position> result = new ArrayList<>();

        // Get all rook-like (horizontal/vertical) moves
        Position[] rookPositions = Rook.reachablePositions(chess, p);
        for (Position rp : rookPositions)
            result.add(rp);

        // Get all bishop-like (diagonal) moves
        Position[] bishopPositions = Bishop.reachablePositions(chess, p);
        for (Position bp : bishopPositions)
            result.add(bp);

        return result.toArray(new Position[]{});
    }
}
