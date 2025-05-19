package am.aua.chess.ui;

import am.aua.chess.core.Chess;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a single square on the chessboard UI.
 * Inherits from {@link JButton} and handles color, piece icons, and highlights.
 */
public class BoardSquare extends JButton {

    /** Color used for light squares on the board. */
    private static final Color LIGHT_COLOR = Color.WHITE;

    /** Color used for dark squares on the board. */
    private static final Color DARK_COLOR = new Color(15, 64, 2);

    /** Row coordinate (rank). */
    private final int x;

    /** Column coordinate (file). */
    private final int y;

    /** The default color of the square (light or dark). */
    private Color color;

    /**
     * Constructs a new BoardSquare with specified color and coordinates.
     * @param isLightColor true if the square is light, false if dark
     * @param x the rank of the square
     * @param y the file of the square
     */
    public BoardSquare(boolean isLightColor, int x, int y){
        super();
        this.x = x;
        this.y = y;
        this.color = isLightColor ? LIGHT_COLOR : DARK_COLOR;
        setHighlitht(false);  // Initialize with no highlight
    }

    /**
     * Returns the board coordinates of this square.
     * @return an array where index 0 is x (rank) and index 1 is y (file)
     */
    public int[] getCordinates(){
        int[] cordinates = new int[2];
        cordinates[0] = this.x;
        cordinates[1] = this.y;
        return cordinates;
    }

    /**
     * Sets the piece icon on this square based on the character code.
     * @param piece the character representing the piece (e.g., 'P', 'k', 'Q', etc.)
     */
    public void setPiece(String piece){
        ImageIcon imageIcon = switch(piece) {
            case "Q" -> new ImageIcon("./gfx/QueenW.png");
            case "q" -> new ImageIcon("./gfx/QueenB.png");
            case "P" -> new ImageIcon("./gfx/PawnW.png");
            case "p" -> new ImageIcon("./gfx/PawnB.png");
            case "N" -> new ImageIcon("./gfx/KnightW.png");
            case "n" -> new ImageIcon("./gfx/KnightB.png");
            case "B" -> new ImageIcon("./gfx/BishopW.png");
            case "b" -> new ImageIcon("./gfx/BishopB.png");
            case "R", "S" -> new ImageIcon("./gfx/RookW.png");
            case "r", "s" -> new ImageIcon("./gfx/RookB.png");
            case "K", "L" -> new ImageIcon("./gfx/KingW.png");
            case "k", "l" -> new ImageIcon("./gfx/KingB.png");
            default -> null;
        };
        setIcon(imageIcon);
    }

    /**
     * Removes the piece icon from this square.
     */
    public void setPiece(){
        this.setIcon(null);
    }

    /**
     * Sets or removes the highlight on this square.
     * Highlights are shown in red.
     * @param isHighlighted true to highlight, false to restore original color
     */
    public void setHighlitht(boolean isHighlighted){
        if (isHighlighted) {
            setBackground(Color.RED);
        } else {
            setBackground(this.color);
        }
    }
}
