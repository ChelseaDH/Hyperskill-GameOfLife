package life;

public class Universe {
    public Map currentGeneration;
    int mapSize;
    private int generationNumber;

    // Initialises a universe with a randomly generated map
    public Universe(int mapSize) {
        this.mapSize = mapSize;
        this.currentGeneration = new Map(mapSize, false);
        this.generationNumber = 1;
    }

    public int getGenerationNumber() {
        return generationNumber;
    }

    // Creates the next generation of the universe
    public void advance() {
        this.currentGeneration = Generation.evolve(currentGeneration);
        this.generationNumber++;
    }

    // Resets the universe
    // Initialises a random map of the same size
    public void reset() {
        this.currentGeneration = new Map(mapSize, false);
        this.generationNumber = 1;
    }
}