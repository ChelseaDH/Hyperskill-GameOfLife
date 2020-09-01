package life;

public class Universe {
    public Map currentGeneration;
    private int generationNumber;

    // Initialises a universe with a randomly generated map
    public Universe(int size) {
        this.currentGeneration = new Map(size, false);
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
}