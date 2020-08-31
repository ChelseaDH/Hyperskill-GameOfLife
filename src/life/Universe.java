package life;

public class Universe {
    public Map currentGeneration;
    public int numberOfGenerations;
    private int generationNumber;

    // Initialises a universe with a randomly generated map
    public Universe(int size) {
        this.currentGeneration = new Map(size, false);
        this.generationNumber = 1;
    }

    // Initialises a universe with a randomly generated map and a maximum number of generations
    public Universe(int size, int numberOfGenerations) {
        this.currentGeneration = new Map(size, false);
        this.numberOfGenerations = numberOfGenerations;
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

    public void run() {
        while (this.generationNumber < this.numberOfGenerations) {
            advance();
        }
    }
}