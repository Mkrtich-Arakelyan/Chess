package am.aua.chess.cli;

import am.aua.chess.core.Chess;
import am.aua.chess.core.Move;
import am.aua.chess.core.Position;
import am.aua.chess.puzzles.Puzzle;
import am.aua.chess.puzzles.PuzzleDatabase;

import java.util.Scanner;

/**
 * Provides a console-based interface for interacting with the chess game engine.
 * Users can play a new game, load puzzles, or add puzzles from a file.
 */
public class ChessConsole {
    private Chess game;
    private PuzzleDatabase database;

    /**
     * Constructs a new ChessConsole and initializes the puzzle database.
     */
    public ChessConsole() {
        database = new PuzzleDatabase();
    }

    /**
     * Runs the console interface. Allows users to choose between playing chess,
     * listing puzzles, adding new puzzles, or playing a specific puzzle.
     */
    public void run() {
        Scanner sc = new Scanner(System.in);
        String inputLine;

        System.out.println("Welcome to Chess Console!");
        printInstructions();
        inputLine = sc.nextLine();
        while (!inputLine.equals("q")) {
            try {
                if (inputLine.equals("p")) {
                    game = new Chess();
                    play(); // Play a new standard game
                }
                else if (inputLine.equals("l")) {
                    // List all puzzles in the database
                    int databaseSize = database.getSize();
                    for (int i = 0; i < databaseSize; i++)
                        System.out.println(i + ": " + database.getPuzzle(i));
                }
                else if (inputLine.startsWith("a "))
                    // Add puzzles from a file
                    database.addPuzzlesFromFile(inputLine.substring(2));
                else if (inputLine.startsWith("p ")) {
                    // Play a puzzle by number
                    int puzzleNumber = Integer.parseInt(inputLine.substring(2));
                    Puzzle puzzle = database.getPuzzle(puzzleNumber);
                    game = new Chess(puzzle.getArrangement(), puzzle.getTurn());
                    play();
                }
                else
                    System.out.println("Unknown instruction. Please try again.");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            printInstructions();
            inputLine = sc.nextLine();
        }
        database.save(); // Save puzzles on exit
    }

    /**
     * Manages a single session of playing the chess game.
     * Handles user input and performs moves accordingly.
     */
    public void play() {
        Scanner sc = new Scanner(System.in);
        String inputLine;

        print(); // Print initial board

        while (!game.isGameOver()) {
            if (game.getTurn() == Chess.PieceColor.WHITE)
                System.out.println("White's move: ");
            else
                System.out.println("Black's move: ");

            inputLine = sc.nextLine();
            String[] input = inputLine.split(" ");

            Position p1 = null, p2 = null;

            if (input.length >= 1) {
                if (input[0].equals("resign")) {
                    System.out.println(game.getTurn() + " has resigned.");
                    return;
                }

                if (input[0].equals("debug")) {
                    debug();
                    print();
                    continue;
                }

                p1 = Position.generateFromString(input[0]);

                if (p1 == null || game.getPieceAt(p1) == null) {
                    System.out.println("Invalid position. Please try again.");
                    continue;
                }

                if (input.length == 1) {
                    // Highlight reachable squares if only one position given
                    if (game.getPieceAt(p1).getPieceColor() != game.getTurn())
                        System.out.println("That piece belongs to the opponent.");
                    print(p1);
                }
                else if (input.length == 2) {
                    if (game.getPieceAt(p1).getPieceColor() != game.getTurn()) {
                        System.out.println("That piece belongs to the opponent.");
                        continue;
                    }

                    boolean success = false;

                    p2 = Position.generateFromString(input[1]);

                    if (p2 != null) {
                        Move m = new Move(p1, p2);
                        success = game.performMove(m);
                    }
                    if (!success)
                        System.out.println("Invalid move. Please try again.");

                    print();
                }
            }
        }
    }

    /**
     * Prints the current game board, highlighting reachable positions
     * from the provided origin position.
     *
     * @param origin the origin position for highlighting, or null
     */
    public void print(Position origin) {
        Position[] highlights = null;
        if (origin != null)
            highlights = game.reachableFrom(origin);

        for (int i = 0; i < Chess.BOARD_RANKS; i++) {
            System.out.print((Chess.BOARD_RANKS - i) + " ");

            for (int j = 0; j < Chess.BOARD_FILES; j++) {
                boolean isHighlighted = false;

                // Highlight the origin square
                if (origin != null
                        && origin.getRank() == i && origin.getFile() == j)
                    isHighlighted = true;

                // Highlight reachable destinations
                for (int k = 0; highlights != null && k < highlights.length; k++)
                    if (highlights[k].getRank() == i
                            && highlights[k].getFile() == j) {
                        isHighlighted = true;
                        break;
                    }

                if (isHighlighted)
                    System.out.print("\u001b[31m"); // red color

                System.out.print("[");

                Position current = Position.generateFromRankAndFile(i, j);
                if (game.isEmpty(current))
                    System.out.print(" ");
                else
                    System.out.print(game.getPieceAt(current));

                System.out.print("]");

                if (isHighlighted)
                    System.out.print("\u001b[0m"); // reset color
            }
            System.out.println();
        }
        System.out.println("   A  B  C  D  E  F  G  H ");
        System.out.println();
    }

    /**
     * Prints the game board without any highlights.
     */
    public void print() {
        print(null);
    }

    /**
     * Provides debug functionality for development/testing purposes.
     */
    private void debug() {
        System.out.println("This method facilitates tests.");
        // Example for testing available destinations (commented out)
        //System.out.println(game.getAllDestinationsByColor(Chess.PieceColor.WHITE).length);
    }

    /**
     * Prints the available console instructions to the user.
     */
    private void printInstructions() {
        System.out.println("Input 'p' to play chess.");
        System.out.println("Input 'l' to list the puzzles in the database.");
        System.out.println("Input 'a <filename>' to add new puzzles into the"
                + " database.");
        System.out.println("Input 'p <number>' to play a puzzle.");
        System.out.println("If you want to end the program, input 'q'.");
    }
}
