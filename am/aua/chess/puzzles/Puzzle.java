package am.aua.chess.puzzles;

import am.aua.chess.core.Chess;

/**
 * Represents a chess puzzle consisting of a board arrangement, a description,
 * a difficulty level, and the player to move.
 * Implements Comparable to allow sorting by difficulty, turn, and position.
 */
public final class Puzzle implements Comparable<Puzzle> {

    /**
     * Enum representing puzzle difficulty levels.
     */
    public enum Difficulty {EASY, MEDIUM, HARD, UNSPECIFIED}

    /** Textual description of the puzzle, such as the objective or hint. */
    private String description;

    /** The FEN-like board arrangement string (64 characters). */
    private String arrangement;

    /** The puzzle's difficulty rating. */
    private Difficulty difficulty;

    /** The color of the player to move (WHITE or BLACK). */
    private Chess.PieceColor turn;

    /**
     * Constructs a Puzzle from a CSV-style input string and a description.
     * Format: "arrangement,TURN,DIFFICULTY"
     *
     * @param details     the comma-separated arrangement, turn, and difficulty
     * @param description the description or hint for the puzzle
     * @throws MalformedPuzzleException if the puzzle format is incorrect
     */
    public Puzzle(String details, String description) throws MalformedPuzzleException {
        this.description = description;

        try {
            String[] components = details.split(",");
            Chess.verifyArrangement(components[0]); // Validate board string
            this.arrangement = components[0];
            this.turn = Chess.PieceColor.valueOf(components[1]);     // Parse turn
            this.difficulty = Difficulty.valueOf(components[2]);     // Parse difficulty
        } catch (Exception e) {
            throw new MalformedPuzzleException(); // Wrap parsing errors
        }
    }

    /**
     * Copy constructor for Puzzle.
     * @param other the Puzzle to copy
     */
    public Puzzle(Puzzle other) {
        this.difficulty = other.difficulty;
        this.turn = other.turn;
        this.description = other.description;
        this.arrangement = other.arrangement;
    }

    /**
     * Compares two puzzles for sorting.
     * Priority: Difficulty → Turn → Arrangement.
     * @param other the other puzzle to compare to
     * @return negative if this < other, 0 if equal, positive if this > other
     */
    public int compareTo(Puzzle other) {
        if (this.difficulty != other.difficulty)
            return this.difficulty.ordinal() - other.difficulty.ordinal();
        if (this.turn != other.turn)
            return this.turn.ordinal() - other.turn.ordinal();
        return this.arrangement.compareTo(other.arrangement);
    }

    /**
     * Checks equality based on arrangement, difficulty, and turn.
     * @param other the object to compare with
     * @return true if both puzzles are equal in arrangement, difficulty, and turn
     */
    public boolean equals(Object other) {
        if (other != null && other.getClass() == Puzzle.class) {
            Puzzle otherPuzzle = (Puzzle) other;
            return otherPuzzle.arrangement.equals(this.arrangement)
                    && otherPuzzle.difficulty == this.difficulty
                    && otherPuzzle.turn == this.turn;
        }
        return false;
    }

    /** @return the puzzle's description */
    public String getDescription() {
        return description;
    }

    /** @return the board arrangement string */
    public String getArrangement() {
        return arrangement;
    }

    /** @return the puzzle's difficulty */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /** @return the color of the player to move */
    public Chess.PieceColor getTurn() {
        return turn;
    }

    /**
     * Returns a string representation of the puzzle, including its arrangement,
     * turn, difficulty, and description.
     * @return string representation of the puzzle
     */
    public String toString() {
        return arrangement + "," + turn + "," + difficulty + "\n" + description;
    }
}
