package life;

public class Universe {
    public Map currentGeneration;
    public int numberOfGenerations;
    private int generationNumber;

    public Universe(int size, int numberOfGenerations) {
        this.currentGeneration = new Map(size, false);
        this.numberOfGenerations = numberOfGenerations;
        this.generationNumber = 1;
    }

    public Universe(int size) {
        this.currentGeneration = new Map(size, false);
        this.generationNumber = 1;
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