package am.aua.chess;

import am.aua.chess.cli.ChessConsole;
import am.aua.chess.ui.ChessUI;

/**
 * Entry point of the chess application.
 * Launches either the console-based interface or the GUI based on command-line arguments.
 */
public class Main {

    /**
     * The main method that starts the application.
     * If the user runs the program with "-console", the CLI version will launch.
     * Otherwise, the GUI version will launch by default.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // Launch console interface if "-console" flag is passed
        if (args.length == 1 && args[0].equals("-console")) {
            ChessConsole chess = new ChessConsole();
            chess.run();
        }

        // Launch GUI interface if no arguments are passed
        if (args.length == 0) {
            new ChessUI();
        }
    }
}
