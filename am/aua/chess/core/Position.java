package am.aua.chess.core;

/**
 * Represents a position on a chessboard using (rank, file) coordinates.
 * Provides utilities for converting between board coordinates and chess notation.
 */
public class Position {

    /** Row index on the board (0 = top, 7 = bottom). */
    private int rank;

    /** Column index on the board (0 = 'A', 7 = 'H'). */
    private int file;

    /**
     * Default constructor.
     * Initializes the position to (0, 0).
     */
    public Position() {
        this.rank = 0;
        this.file = 0;
    }

    /**
     * Copy constructor.
     * @param other another Position to copy
     */
    public Position(Position other) {
        this.setRank(other.rank);
        this.setFile(other.file);
    }

    /**
     * Private constructor with specified rank and file.
     * @param rank the rank (row)
     * @param file the file (column)
     */
    private Position(int rank, int file) {
        this.setRank(rank);
        this.setFile(file);
    }

    /**
     * Gets the rank (row index) of this position.
     * @return the rank
     */
    public int getRank() {
        return this.rank;
    }

    /**
     * Gets the file (column index) of this position.
     * @return the file
     */
    public int getFile() {
        return this.file;
    }

    /**
     * Sets the rank if within valid board bounds.
     * @param rank the rank to set
     */
    public void setRank(int rank) {
        if (rank >= 0 && rank < Chess.BOARD_RANKS)
            this.rank = rank;
    }

    /**
     * Sets the file if within valid board bounds.
     * @param file the file to set
     */
    public void setFile(int file) {
        if (file >= 0 && file < Chess.BOARD_FILES)
            this.file = file;
    }

    /**
     * Returns the string representation of the position in chess notation (e.g., "E2").
     * @return the position as a string
     */
    public String toString() {
        return "" + (char)('A' + this.file) + (Chess.BOARD_RANKS - this.rank);
    }

    /**
     * Generates a Position object from a chess notation string (e.g., "E2").
     * Returns null if the input is invalid.
     * @param s the string representing a position
     * @return a Position object or null
     */
    public static Position generateFromString(String s) {
        s = s.toLowerCase();
        if (s.length() != 2
                || (s.charAt(0) < 'a' || s.charAt(0) >= 'a' + Chess.BOARD_FILES)
                || (s.charAt(1) < '1' || s.charAt(1) >= '1' + Chess.BOARD_RANKS)
        )
            return null;

        // Convert character notation to internal indices
        int file = s.charAt(0) - 'a';
        int rank = Chess.BOARD_RANKS - (s.charAt(1) - '0');
        return generateFromRankAndFile(rank, file);
    }

    /**
     * Generates a Position object from rank and file values.
     * Returns null if the coordinates are out of bounds.
     * @param rank the rank (0–7)
     * @param file the file (0–7)
     * @return a valid Position or null
     */
    public static Position generateFromRankAndFile(int rank, int file) {
        Position result = null;
        if (rank >= 0 && rank < Chess.BOARD_RANKS
                && file >= 0 && file < Chess.BOARD_FILES)
            result = new Position(rank, file);
        return result;
    }

    /**
     * Appends additional positions to an array.
     * (Deprecated: use collections instead)
     * @param arr the original array
     * @param elements additional positions to append
     * @return a new array containing all elements
     */
    @Deprecated
    public static Position[] appendPositionsToArray(Position[] arr, Position... elements) {
        Position[] result = new Position[arr.length + elements.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i];
        for (int i = 0; i < elements.length; i++)
            result[arr.length + i] = elements[i];

        return result;
    }

    /**
     * Checks if this position is equal to another.
     * @param other the object to compare
     * @return true if both positions have the same rank and file
     */
    public boolean equals(Object other) {
        if (other == null || other.getClass() != Position.class)
            return false;
        Position otherPosition = (Position) other;
        return this.rank == otherPosition.rank && this.file == otherPosition.file;
    }
}
