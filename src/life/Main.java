package life;

import java.io.IOException;
import java.util.Scanner;
import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get map size, generation seed and generation limit from the user
        int mapSize = scanner.nextInt();

        // Create the universe
        Universe universe = new Universe(mapSize);

        // Create and initialise the board
        GameOfLife gameOfLife = new GameOfLife();
        gameOfLife.initialLayout(universe);

        // Run the simulation
        while (universe.getGenerationNumber() < 20) {
            // Add the current generation to the board
            gameOfLife.addGeneration(universe);

            // Create the next generation
            universe.advance();

            // Sleep
            try {
                sleep(100);
            } catch (InterruptedException ignored) {}
        }
    }
}