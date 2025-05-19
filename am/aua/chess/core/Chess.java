package am.aua.chess.core;

import java.util.ArrayList;

/**
 * Core class representing the state and logic of a chess game.
 */
public class Chess implements Cloneable {

    /** Enum representing the two possible colors of chess pieces. */
    public enum PieceColor {WHITE, BLACK}

    /** Number of ranks and files on a standard chessboard. */
    public static final int BOARD_RANKS = 8;
    public static final int BOARD_FILES = 8;

    /** Starting rank index for white and black pawns. */
    public static final int WHITE_PAWN_STARTING_RANK = 6;
    public static final int BLACK_PAWN_STARTING_RANK = 1;

    /** 2D array representing the chessboard. */
    private Piece[][] board;

    /** Number of moves made since the beginning of the game. Used to determine turn. */
    private int numberOfMoves;

    /**
     * Default constructor that initializes the board to the standard starting arrangement.
     */
    public Chess() throws IllegalArrangementException {
        // Must declare the exception even if it cannot occur here
        this("rnbqkbnr" +
                        "pppppppp" +
                        "--------" +
                        "--------" +
                        "--------" +
                        "--------" +
                        "PPPPPPPP" +
                        "RNBQKBNR",
                PieceColor.WHITE);
    }

    /**
     * Verifies that a given board arrangement string is valid.
     * Ensures that there is exactly one king per color.
     * @param s the arrangement string
     * @throws IllegalArrangementException if invalid length or number of kings
     */
    public static void verifyArrangement(String s) throws IllegalArrangementException {
        boolean whiteKingPresent = false, blackKingPresent = false;
        if (s.length() != 64)
            throw new IllegalArrangementException("The length of the arrangement must be 64");
        for (int i = 0; i < 64; i++) {
            if (s.charAt(i) == 'K' || s.charAt(i) == 'L')
                if (!whiteKingPresent)
                    whiteKingPresent = true;
                else
                    throw new InvalidNumberOfKingsException();
            else if (s.charAt(i) == 'k' || s.charAt(i) == 'l')
                if (!blackKingPresent)
                    blackKingPresent = true;
                else
                    throw new InvalidNumberOfKingsException();
        }
        if (!whiteKingPresent || !blackKingPresent)
            throw new InvalidNumberOfKingsException();
    }

    /**
     * Initializes the board using a custom arrangement string and turn.
     * @param arrangement string representation of 64-piece positions
     * @param turn whose turn it is (WHITE or BLACK)
     * @throws IllegalArrangementException if the arrangement is invalid
     */
    public Chess(String arrangement, PieceColor turn) throws IllegalArrangementException {
        verifyArrangement(arrangement);
        this.numberOfMoves = turn.ordinal();
        this.board = new Piece[BOARD_RANKS][BOARD_FILES];

        // Map characters to corresponding pieces and place them on the board
        for (int i = 0; i < arrangement.length(); i++) {
            switch (arrangement.charAt(i)) {
                case 'R': this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Rook(PieceColor.WHITE); break;
                case 'r': this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Rook(PieceColor.BLACK); break;
                case 'S': this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Rook(PieceColor.WHITE, true); break;
                case 's': this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Rook(PieceColor.BLACK, true); break;
                case 'N': this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Knight(PieceColor.WHITE); break;
                case 'n': this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Knight(PieceColor.BLACK); break;
                case 'B': this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Bishop(PieceColor.WHITE); break;
                case 'b': this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Bishop(PieceColor.BLACK); break;
                case 'K': this.board[i/BOARD_RANKS][i%BOARD_FILES] = new King(PieceColor.WHITE); break;
                case 'k': this.board[i/BOARD_RANKS][i%BOARD_FILES] = new King(PieceColor.BLACK); break;
                case 'L': this.board[i/BOARD_RANKS][i%BOARD_FILES] = new King(PieceColor.WHITE, true); break;
                case 'l': this.board[i/BOARD_RANKS][i%BOARD_FILES] = new King(PieceColor.BLACK, true); break;
                case 'Q': this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Queen(PieceColor.WHITE); break;
                case 'q': this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Queen(PieceColor.BLACK); break;
                case 'P': this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Pawn(PieceColor.WHITE); break;
                case 'p': this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Pawn(PieceColor.BLACK); break;
            }
        }
    }

    /**
     * Returns a deep copy of the current board.
     * @return a cloned 2D array of the board.
     */
    public Piece[][] getBoard() {
        Piece[][] boardCopy = new Piece[BOARD_RANKS][BOARD_FILES];
        for (int i = 0; i < BOARD_RANKS; i++)
            for (int j = 0; j < BOARD_FILES; j++)
                if (this.board[i][j] != null)
                    boardCopy[i][j] = (Piece) this.board[i][j].clone();
        return boardCopy;
    }

    /**
     * Returns a deep clone of this Chess object.
     * @return cloned Chess object
     */
    public Chess clone() {
        try {
            Chess copy = (Chess) super.clone();
            copy.board = this.getBoard();
            return copy;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     * Returns whose turn it is based on the move count.
     * @return PieceColor.WHITE or PieceColor.BLACK
     */
    public PieceColor getTurn() {
        return PieceColor.values()[this.numberOfMoves % 2];
    }

    /**
     * Currently a placeholder — always returns false.
     * @return false (no checkmate/stalemate logic implemented)
     */
    public boolean isGameOver() {
        return false;
    }

    /**
     * Checks if a given position is empty.
     * @param p the position to check
     * @return true if no piece occupies the square
     */
    public boolean isEmpty(Position p) {
        return this.board[p.getRank()][p.getFile()] == null;
    }

    /**
     * Gets the piece at the given position.
     * @param p the position to query
     * @return the Piece at the position or null
     */
    public Piece getPieceAt(Position p) {
        return this.board[p.getRank()][p.getFile()];
    }

    /**
     * Returns all reachable positions from a given origin position.
     * @param origin the starting position
     * @return an array of legal destinations or null
     */
    public Position[] reachableFrom(Position origin) {
        if (origin == null || this.isEmpty(origin))
            return null;
        return board[origin.getRank()][origin.getFile()].allDestinations(this, origin);
    }

    /**
     * Attempts to perform a move on the board. Validates legality and prevents self-check.
     * @param m the move to perform
     * @return true if move was legal and performed
     */
    public boolean performMove(Move m) {
        Position o = m.getOrigin();
        Position d = m.getDestination();

        if (this.getPieceAt(o).getPieceColor() != this.getTurn())
            return false;

        Position[] reachable = this.reachableFrom(o);
        Piece[][] boardCopy = this.getBoard();

        for (int i = 0; i < reachable.length; i++)
            if (d.getRank() == reachable[i].getRank()
                    && d.getFile() == reachable[i].getFile()) {

                // Perform the move
                this.board[d.getRank()][d.getFile()] = this.board[o.getRank()][o.getFile()];
                this.board[o.getRank()][o.getFile()] = null;

                // Mark rook/king as moved (for castling logic)
                if (this.board[d.getRank()][d.getFile()] instanceof Rook)
                    ((Rook)this.board[d.getRank()][d.getFile()]).setHasMoved(true);
                else if (this.board[d.getRank()][d.getFile()] instanceof King)
                    ((King)this.board[d.getRank()][d.getFile()]).setHasMoved(true);

                // Check if move places own king in check
                if (isKingUnderAttack(this.getTurn())) {
                    this.board = boardCopy; // revert move
                    return false;
                }

                this.numberOfMoves++; // increment turn
                return true;
            }

        return false; // illegal move
    }

    /**
     * Determines whether the king of the given color is in check.
     * @param kingColor The color of the king in question.
     * @return True, if the king in question is under attack by the opponent.
     */
    public boolean isKingUnderAttack(PieceColor kingColor) {
        Position kingPosition = null;
        PieceColor opponentColor;
        Position[] p = null;

        // Find the king's position
        for (int i = 0; i < BOARD_RANKS; i++)
            for (int j = 0; j < BOARD_FILES; j++)
                if (board[i][j] != null
                        && board[i][j].getPieceColor() == kingColor
                        && board[i][j] instanceof King)
                    kingPosition = Position.generateFromRankAndFile(i, j);

        // Determine opponent color
        opponentColor = (kingColor == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;

        // Get all opponent moves
        p = getAllDestinationsByColor(opponentColor);

        // Check if any attack the king
        for (int i = 0; i < p.length; i++)
            if (p[i].equals(kingPosition))
                return true;

        return false;
    }

    /**
     * Collects all reachable positions by all pieces of a given color.
     * @param color the color of the pieces to consider
     * @return an array of all positions they can move to
     */
    public Position[] getAllDestinationsByColor(PieceColor color) {
        ArrayList<Position> result = new ArrayList<>();

        for (int i = 0; i < BOARD_RANKS; i++)
            for (int j = 0; j < BOARD_FILES; j++)
                if (board[i][j] != null && board[i][j].getPieceColor() == color) {
                    Position[] current = board[i][j].allDestinations(this,
                            Position.generateFromRankAndFile(i, j));
                    for (Position currentPosition : current)
                        if (!result.contains(currentPosition))
                            result.add(currentPosition);
                }

        return result.toArray(new Position[]{});
    }
}
