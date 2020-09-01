package life;

import java.util.Scanner;
import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Main thread
        Thread mainThread = Thread.currentThread();

        // Get map size from the user
        int mapSize = scanner.nextInt();

        // Create the universe
        Universe universe = new Universe(mapSize);

        // Create and initialise the board
        GameOfLife gameOfLife = new GameOfLife();
        gameOfLife.initialLayout(universe, mainThread);

        // Run the universe simulation from the gui
        gameOfLife.runSimulation();
    }
}

