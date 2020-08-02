package life;

public class Cell{
    public State state;

    public Cell(State state) {
        this.state = state;
    }

    public char getChar() {
        switch (state) {
            case ALIVE:
                return 'O';
            default:
                return ' ';
        }
    }
}
