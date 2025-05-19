package am.aua.chess.core;

import java.util.ArrayList;

/**
 * Represents a Pawn chess piece.
 * Implements movement and capturing rules for both white and black pawns.
 */
public class Pawn extends Piece {

    /**
     * Default constructor.
     * Initializes a white pawn.
     */
    public Pawn() {
        this(Chess.PieceColor.WHITE);
    }

    /**
     * Constructor with color.
     * Initializes a pawn with the specified color.
     * @param color the color of the pawn (WHITE or BLACK)
     */
    public Pawn(Chess.PieceColor color) {
        super(color);
    }

    /**
     * Returns a string representation of the pawn.
     * "P" for white, "p" for black.
     * @return string representation
     */
    public String toString() {
        if (this.getPieceColor() == Chess.PieceColor.WHITE)
            return "P";
        else
            return "p";
    }

    /**
     * Computes all legal destinations for a pawn from the given position.
     * This includes:
     * - One-step forward movement
     * - Two-step jump from starting rank
     * - Diagonal captures (left and right)
     * En passant and promotion are not implemented.
     *
     * @param chess the current state of the chessboard
     * @param p the current position of the pawn
     * @return array of valid destination positions
     */
    public Position[] allDestinations(Chess chess, Position p) {
        ArrayList<Position> result = new ArrayList<>();
        Chess.PieceColor myColor = chess.getPieceAt(p).getPieceColor();

        // Potential destination positions
        Position front = null, jump = null, left = null, right = null;

        if (myColor == Chess.PieceColor.WHITE) {
            // White pawns move "up" (decreasing rank)
            front = Position.generateFromRankAndFile(p.getRank() - 1, p.getFile());

            // Check if at starting rank for potential 2-step move
            if (p.getRank() == Chess.WHITE_PAWN_STARTING_RANK)
                jump = Position.generateFromRankAndFile(Chess.WHITE_PAWN_STARTING_RANK - 2, p.getFile());

            // Diagonal captures
            left = Position.generateFromRankAndFile(p.getRank() - 1, p.getFile() - 1);
            right = Position.generateFromRankAndFile(p.getRank() - 1, p.getFile() + 1);
        } else {
            // Black pawns move "down" (increasing rank)
            front = Position.generateFromRankAndFile(p.getRank() + 1, p.getFile());

            // Check if at starting rank for potential 2-step move
            if (p.getRank() == Chess.BLACK_PAWN_STARTING_RANK)
                jump = Position.generateFromRankAndFile(Chess.BLACK_PAWN_STARTING_RANK + 2, p.getFile());

            // Diagonal captures
            left = Position.generateFromRankAndFile(p.getRank() + 1, p.getFile() - 1);
            right = Position.generateFromRankAndFile(p.getRank() + 1, p.getFile() + 1);
        }

        // Add valid forward move if the square is empty
        if (front != null && chess.isEmpty(front))
            result.add(front);

        // Add diagonal captures if enemy piece is present
        if (left != null && chess.getPieceAt(left) != null
                && chess.getPieceAt(left).getPieceColor() != this.getPieceColor())
            result.add(left);

        if (right != null && chess.getPieceAt(right) != null
                && chess.getPieceAt(right).getPieceColor() != this.getPieceColor())
            result.add(right);

        // Add jump move if both front and jump squares are empty
        if (front != null && chess.isEmpty(front) && jump != null && chess.isEmpty(jump))
            result.add(jump);

        // Return all legal destinations
        return result.toArray(new Position[]{});
    }
}
