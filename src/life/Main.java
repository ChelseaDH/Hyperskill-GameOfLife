package life;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get map size, generation seed and generation limit from the user
        int mapSize = scanner.nextInt();
        int numberOfGenerations = scanner.nextInt();

        // Create the universe
        Universe universe = new Universe(mapSize, numberOfGenerations);

        //
    }
}