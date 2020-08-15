package life;

public class Cell{
    public static final Cell ALIVE = new Cell('O', true);
    public static final Cell DEAD = new Cell(' ', false);

    public char character;
    public boolean alive;

    private Cell(char character, boolean alive) {
        this.character = character;
        this.alive = alive;
    }

    public char getCharacter() {
        return character;
    }
}