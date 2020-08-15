package life;

import java.util.Arrays;
import java.util.Random;

public class Map {
    public Cell[][] grid;
    public int size;

    // Initialises either a completely dead or random map of a given size
    public Map(int size, Boolean dead) {
        if (dead) {
            this.size = size;
            this.grid = new Cell[this.size][this.size];
            for (Cell[] array: this.grid) {
                Arrays.fill(array, Cell.DEAD);
            }
        }
        else {
            this.size = size;
            initialiseGrid();
        }
    }

    private void initialiseGrid() {
        Random random = new Random();
        this.grid = new Cell[this.size][this.size];

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                Cell cell = random.nextBoolean() ? Cell.ALIVE : Cell.DEAD;
                this.grid[i][j] = cell;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                stringBuilder.append(this.grid[i][j].getCharacter());
            }
            if (i < this.size - 1) {
                stringBuilder.append('\n');
            }
        }

        return stringBuilder.toString();
    }

    public int noOfAliveNeighbours(int cellX, int cellY) {
        int aliveNeighbours = 0;

        for (Direction d : Direction.values()) {
            if (getNeighbour(cellX, cellY, d).alive) {
                aliveNeighbours++;
            }
        }

        return aliveNeighbours;
    }

    private Cell getNeighbour(int cellX, int cellY, Direction direction) {
        switch (direction) {
            case NORTH:
                return grid[cellX][(cellY + 1) % size];
            case NORTH_EAST:
                return grid[(cellX + 1) % size][(cellY + 1) % size];
            case EAST:
                return grid[(cellX + 1) % size][cellY];
            case SOUTH_EAST:
                return grid[(cellX + 1) % size][(cellY - 1 + size) % size];
            case SOUTH:
                return grid[cellX][(cellY - 1 + size) % size];
            case SOUTH_WEST:
                return grid[(cellX - 1 + size) % size][(cellY - 1 + size) % size];
            case WEST:
                return grid[(cellX - 1 + size) % size][cellY];
            case NORTH_WEST:
            default:
                return grid[(cellX - 1 + size) % size][(cellY + 1) % size];
        }
    }

    public int aliveCells() {
        int alive = 0;

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.grid[i][j].alive) {
                    alive++;
                }
            }
        }

        return alive;
    }
}