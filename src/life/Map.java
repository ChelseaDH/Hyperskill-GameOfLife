package life;

import java.util.Random;

public class Map{
    public Cell[][] grid;
    public int size;
    public int seed;

    public Map(int size, int seed) {
        this.size = size;
        this.seed = seed;
        initialiseGrid();
    }

    private void initialiseGrid() {
        Random random = new Random(this.seed);
        this.grid = new Cell[this.size][this.size];

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                State state = random.nextBoolean() ? State.ALIVE : State.DEAD;
                this.grid[i][j] = new Cell(state);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                stringBuilder.append(this.grid[i][j].getChar());
            }
            stringBuilder.append('\n');
        }

        return stringBuilder.toString();
    }
}
