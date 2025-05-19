package am.aua.chess.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import am.aua.chess.core.*;

/**
 * The main GUI for the chess game.
 * Displays the board, handles user interactions, and manages the game logic.
 */
public class ChessUI extends JFrame {

    /** The current game state. */
    private Chess game;

    /** 2D array of board square buttons. */
    private BoardSquare[][] boardSquares;

    /** Holds the origin of a selected move; null if no selection yet. */
    private Position preOrigin;

    /**
     * Constructs the UI window and initializes the game and board layout.
     */
    public ChessUI() {
        super("Chess game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 750);

        // Initialize the game logic
        try {
            game = new Chess();
        } catch (IllegalArrangementException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        // Create and configure the board panel
        JPanel boardPanel = new JPanel(new GridLayout(Chess.BOARD_RANKS, Chess.BOARD_FILES));
        boardSquares = new BoardSquare[Chess.BOARD_RANKS][Chess.BOARD_FILES];

        // Set up each square
        for (int row = 0; row < Chess.BOARD_RANKS; row++) {
            for (int col = 0; col < Chess.BOARD_FILES; col++) {
                boolean isLightColor = (row + col) % 2 == 0;
                boardSquares[row][col] = new BoardSquare(isLightColor, row, col);
                boardPanel.add(boardSquares[row][col]);

                // Place initial pieces
                Piece piece = game.getPieceAt(Position.generateFromRankAndFile(row, col));
                if (piece != null) {
                    boardSquares[row][col].setPiece(piece.toString());
                }

                // Action listener for click events
                int finalRow = row;  // Required to use within inner class
                int finalCol = col;
                boardSquares[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int[] array = new int[]{finalRow, finalCol};
                        boardClicked(array);
                    }
                });
            }
        }

        // Add the board panel to the frame
        getContentPane().add(boardPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * Handles user interaction when a board square is clicked.
     * Either selects a piece or attempts a move, then updates the board.
     *
     * @param cordinates the coordinates of the clicked square
     */
    private void boardClicked(int[] cordinates) {
        updatePieces(); // Always clear highlights first

        if (preOrigin == null) {
            // No piece selected yet — try selecting a piece
            preOrigin = Position.generateFromRankAndFile(cordinates[0], cordinates[1]);
            Piece piece = game.getPieceAt(preOrigin);

            // Only allow selection of the current player's piece
            if (piece != null && piece.getPieceColor() == game.getTurn()) {
                for (Position position : game.reachableFrom(preOrigin)) {
                    boardSquares[position.getRank()][position.getFile()].setHighlitht(true);
                }
            } else {
                preOrigin = null; // Invalid selection
            }

        } else {
            // Piece already selected — try to perform the move
            Position finalPosition = Position.generateFromRankAndFile(cordinates[0], cordinates[1]);
            Move move = new Move(preOrigin, finalPosition);

            if (game.performMove(move)) {
                updatePieces(); // Refresh board after valid move
            }

            preOrigin = null; // Reset selection
        }
    }

    /**
     * Updates all board squares: sets or removes piece icons and clears highlights.
     */
    private void updatePieces() {
        for (int rows = 0; rows < Chess.BOARD_RANKS; rows++) {
            for (int columns = 0; columns < Chess.BOARD_FILES; columns++) {
                int[] coordinates = boardSquares[rows][columns].getCordinates();
                Position pos = Position.generateFromRankAndFile(coordinates[0], coordinates[1]);
                Piece piece = this.game.getPieceAt(pos);

                if (piece != null) {
                    boardSquares[rows][columns].setPiece(piece.toString());
                } else {
                    boardSquares[rows][columns].setPiece(); // Clear icon
                }

                boardSquares[rows][columns].setHighlitht(false); // Clear highlights
            }
        }
    }
}
