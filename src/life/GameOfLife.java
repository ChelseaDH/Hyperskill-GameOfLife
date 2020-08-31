package life;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.Date;

public class GameOfLife extends JFrame {
    JPanel topPanel;
    JLabel generationLabel;
    JLabel aliveLabel;
    Grid mapPanel;

    public GameOfLife() {
        super("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        setVisible(true);
    }

    // Initialises the GUI layout
    public void initialLayout(Universe universe) {
        // Create top panel
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        add(topPanel);

        // Create generation counter label
        generationLabel = new JLabel();
        generationLabel.setName("GenerationLabel");
        generationLabel.setText("Generation #0");
        // Create alive cell counter label
        aliveLabel = new JLabel();
        aliveLabel.setName("AliveLabel");
        aliveLabel.setText("Alive: 0");
        // Add labels to topPanel
        topPanel.add(generationLabel);
        topPanel.add(aliveLabel);

        // Create mapPanel
        mapPanel = new Grid(400, 400, universe.currentGeneration.size);
        add(mapPanel);
    }

    // Add the updated universe information to GUI
    public void addGeneration (Universe universe) {
        // Update generation and alive counters
        generationLabel.setText(String.format("Generation #%d", universe.getGenerationNumber()));
        aliveLabel.setText(String.format("Alive: %s", universe.currentGeneration.aliveCells()));

        // Update the map within grid
        mapPanel.setMap(universe.currentGeneration);
        mapPanel.repaint();
    }

    // Class for displaying Maps
    public static class Grid extends JPanel {
        int width, height;

        Map map;
        int mapSize;

        int cellWidth, cellHeight;

        public Grid(int width, int height, int mapSize) {
            this.width = width;
            this.height = height;
            this.mapSize = mapSize;

            this.cellWidth = width / this.mapSize;
            this.cellHeight = height / this.mapSize;
            this.setSize(width, height);
        }

        public void setMap(Map map) {
            this.map = map;
        }

        @Override
        public void paintComponent(Graphics g) {
            // Fill grid
            for (int i = 0; i < this.mapSize; i++) {
                for (int j = 0; j < this.mapSize; j++) {
                    g.setColor(this.map.grid[i][j].alive ? Color.black : Color.white);
                    g.fillRect(i * this.cellWidth, j * this.cellHeight, this.cellWidth, this.cellHeight);
                }
            }

            // Print grid outline
            g.setColor(Color.lightGray);
            for (int i = 0; i <= this.mapSize; i++) {
                g.drawLine(0, i * cellHeight, width, i * cellHeight);
                g.drawLine(i * cellWidth, 0, i * cellWidth, height);
            }
        }
    }
}
