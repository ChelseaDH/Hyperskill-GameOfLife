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

        // Run the simulation
        while (universe.getGenerationNumber() < 10) {
            // For each generation, print the generation number, number of alive cells, and state of the universe
            System.out.printf("Generation #%d\n", universe.getGenerationNumber());
            System.out.printf("Alive: %d\n", universe.currentGeneration.aliveCells());
            System.out.println(universe.currentGeneration);
            System.out.println();

            // Create the next generation
            universe.advance();

            // Sleep
            try {
                sleep(100);
            } catch (InterruptedException ignored) {}

            // Clear the console
            try {
                if (System.getProperty("os.name").contains("Windows"))
                    new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
                else
                    Runtime.getRuntime().exec("clear");
            }
            catch (IOException | InterruptedException ignored) {}
        }
    }
}