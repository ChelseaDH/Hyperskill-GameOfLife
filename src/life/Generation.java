package life;

public class Generation{

    public static Map evolve(Map currentGeneration) {
        Map nextGeneration = new Map(currentGeneration.size);

        for (int x = 0; x < currentGeneration.size; x++) {
            for (int y = 0; y < currentGeneration.size; y++) {
                int aliveNeighbours = currentGeneration.noOfAliveNeighbours(x, y);

                // If the current cell is alive
                // It survives if it has two or three alive neighbours
                // Otherwise, it dies of boredom (<2) or overpopulation (>3)
                if (currentGeneration.grid[x][y] == Cell.ALIVE) {
                    if (aliveNeighbours == 2 || aliveNeighbours == 3) {
                        nextGeneration.grid[x][y] = Cell.ALIVE;
                    } else {
                        nextGeneration.grid[x][y] = Cell.DEAD;
                    }
                // A dead cell is reborn if it has exactly three alive neighbors
                } else {
                    if (aliveNeighbours == 3) {
                        nextGeneration.grid[x][y] = Cell.ALIVE;
                    } else {
                        nextGeneration.grid[x][y] = Cell.DEAD;
                    }
                }
            }
        }
        return nextGeneration;
    }
}