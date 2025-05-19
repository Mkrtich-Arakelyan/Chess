package am.aua.chess.core;

/**
 * Represents a chess move consisting of an origin and a destination position.
 * This class is immutable from the outside — both origin and destination
 * are returned as new copies to preserve encapsulation.
 */
public class Move {

    /** The starting position of the move. */
    private Position origin;

    /** The ending position of the move. */
    private Position destination;

    /**
     * Constructs a new move from the given origin to destination positions.
     * @param origin the starting position
     * @param destination the ending position
     */
    public Move(Position origin, Position destination) {
        // Create deep copies to ensure immutability
        this.origin = new Position(origin);
        this.destination = new Position(destination);
    }

    /**
     * Copy constructor.
     * Creates a new move identical to another.
     * @param other the move to copy
     */
    public Move(Move other) {
        this.origin = new Position(other.origin);
        this.destination = new Position(other.destination);
    }

    /**
     * Returns a copy of the origin position of the move.
     * @return a new Position representing the origin
     */
    public Position getOrigin() {
        return new Position(this.origin);
    }

    /**
     * Returns a copy of the destination position of the move.
     * @return a new Position representing the destination
     */
    public Position getDestination() {
        return new Position(this.destination);
    }

    /**
     * Returns a string representation of the move.
     * Format: "origin destination"
     * @return the move as a string
     */
    public String toString() {
        return this.origin.toString() + " " + this.destination.toString();
    }
}
