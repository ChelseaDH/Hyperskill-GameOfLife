package life;

public class Main {
    public static void main(String[] args) {
        // Create and initialise the board
        GameOfLife gameOfLife = new GameOfLife();

        // Run the universe simulation from the gui
        gameOfLife.runSimulation();
    }
}

