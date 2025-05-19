package am.aua.chess.puzzles;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Manages a collection of chess puzzles.
 * Supports loading from and saving to a file, adding puzzles from external sources,
 * and accessing puzzles by index.
 */
public class PuzzleDatabase {

    /** Path to the file where puzzles are stored. */
    public static final String databasePath = "database.txt";

    /** List of all puzzles stored in memory. */
    private ArrayList<Puzzle> puzzles;

    /**
     * Constructs a new PuzzleDatabase and loads puzzles from the default file.
     */
    public PuzzleDatabase() {
        load();
    }

    /**
     * Loads puzzles from the database file into memory.
     * Ensures no duplicates and sorts puzzles by difficulty, turn, and arrangement.
     * Exits the program if the file is missing or a puzzle is malformed.
     */
    public void load() {
        try {
            Scanner sc = new Scanner(new FileInputStream(databasePath));
            int puzzleCount = sc.nextInt(); // Read the number of puzzles
            puzzles = new ArrayList<>(puzzleCount);
            sc.nextLine(); // Move to next line

            // Read each puzzle's details and description
            for (int i = 0; i < puzzleCount; i++) {
                Puzzle current = new Puzzle(sc.nextLine(), sc.nextLine());
                if (!contains(current))
                    puzzles.add(current);
            }

            Collections.sort(puzzles); // Sort puzzles for consistent order
            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the database file.");
            System.exit(0);
        } catch (MalformedPuzzleException e) {
            System.out.println("Malformed puzzle in the database.");
            System.exit(0);
        }
    }

    /**
     * Saves the current list of puzzles to the database file.
     * Exits the program if the file cannot be written.
     */
    public void save() {
        try {
            PrintWriter pw = new PrintWriter(databasePath);
            pw.println(puzzles.size()); // First line = number of puzzles
            for (int i = 0; i < puzzles.size(); i++)
                pw.println(puzzles.get(i)); // Each puzzle outputs two lines
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot save into the database file.");
            System.exit(0);
        }
    }

    /**
     * Loads puzzles from an external file and adds them to the database.
     * Ensures no duplicates and sorts the resulting list.
     * Each puzzle must span two lines: details and description.
     *
     * @param filename the path to the external file
     */
    public void addPuzzlesFromFile(String filename) {
        try {
            Scanner sc = new Scanner(new FileInputStream(filename));
            while (sc.hasNextLine()) {
                Puzzle current = new Puzzle(sc.nextLine(), sc.nextLine());
                if (!contains(current))
                    puzzles.add(current);
            }
            Collections.sort(puzzles); // Maintain sorted order
            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the specified file with puzzles.");
            System.exit(0);
        } catch (MalformedPuzzleException e) {
            System.out.println("Malformed puzzle in the database.");
            System.exit(0);
        }
    }

    /**
     * Returns the number of puzzles in the database.
     * @return the total number of stored puzzles
     */
    public int getSize() {
        return puzzles.size();
    }

    /**
     * Retrieves the puzzle at the specified index.
     * @param i index of the puzzle to retrieve
     * @return the puzzle at index {@code i}
     */
    public Puzzle getPuzzle(int i) {
        return puzzles.get(i);
    }

    /**
     * Checks if a given puzzle already exists in the database.
     * @param p the puzzle to check
     * @return true if the puzzle exists, false otherwise
     */
    private boolean contains(Puzzle p) {
        for (int i = 0; i < puzzles.size(); i++)
            if (puzzles.get(i).equals(p))
                return true;
        return false;
    }
}
