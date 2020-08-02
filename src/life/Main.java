package life;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get map size and random seed from the user
        int mapSize = scanner.nextInt();
        int seed = scanner.nextInt();

        // Create the map
        Map map = new Map(mapSize, seed);

        // Print the map
        System.out.print(map);
    }
}