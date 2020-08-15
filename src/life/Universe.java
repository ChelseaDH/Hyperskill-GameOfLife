package life;

public class Universe {
    public Map currentGeneration;
    // public Map nextGeneration;
    public int numberOfGenerations;
    private int generationNumber;

    public Universe(int size, long generationSeed, int numberOfGenerations) {
        this.currentGeneration = new Map(size, generationSeed);
        this.numberOfGenerations = numberOfGenerations;
        this.generationNumber = 0;
    }

    public void advance() {
        this.currentGeneration = Generation.evolve(currentGeneration);
        this.generationNumber++;
    }

    public void run() {
        while (this.generationNumber < this.numberOfGenerations) {
            advance();
        }
    }

    public int getGenerationNumber() {
        return generationNumber;
    }
}