package life;

import javax.swing.*;
import java.awt.*;

public class GameOfLife extends JFrame {
    JPanel topPanel;
    JPanel interactivePanel;
    Grid mapPanel;
    JLabel generationLabel;
    JLabel aliveLabel;

    GridBagConstraints constraints;

    // Main thread
    Thread mainThread;

    public GameOfLife() {
        super("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(500, 400));

        constraints = new GridBagConstraints();
    }

    // Initialises the GUI layout
    public void initialLayout(Universe universe, Thread mainThread) {
        this.mainThread = mainThread;

        createTopPanel();
        createInteractivePanel();

        pack();
        setVisible(true);
    }

    private void createTopPanel() {
        topPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        add(topPanel, constraints);

        // Add generation counter
        generationLabel = new JLabel();
        generationLabel.setName("GenerationLabel");
        generationLabel.setText("Generation #0");
        generationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(generationLabel);

        // Add alive cell counter
        aliveLabel = new JLabel();
        aliveLabel.setName("AliveLabel");
        aliveLabel.setText("Alive: 0");
        aliveLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(aliveLabel);

        // Reset unused constraints
        constraints.gridwidth = 1;
    }

    private void createInteractivePanel() {
        interactivePanel = new JPanel();
        interactivePanel.setLayout(new BoxLayout(interactivePanel, BoxLayout.Y_AXIS));
        interactivePanel.setPreferredSize(new Dimension(100, 400));
        interactivePanel.setBackground(Color.lightGray);

        // Set constraints and add to board
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.VERTICAL;
        add(interactivePanel, constraints);
    }

    private void createMapPanel(Universe universe) {
        // Create mapPanel
        mapPanel = new Grid(400, 400, universe.currentGeneration.size);

        // Set constraints and add to board
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(mapPanel, constraints);
    }

    // Add the updated universe information to GUI
    public void addGeneration (Universe universe) {
        // Update generation and alive counters
        generationLabel.setText(String.format("Generation #%d", universe.getGenerationNumber()));
        aliveLabel.setText(String.format("Alive: %s", universe.currentGeneration.aliveCells()));

        // Update the map within grid
        mapPanel.setMap(universe.currentGeneration);
        if (this.isVisible()) {
            mapPanel.repaint();
        }
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
            this.setPreferredSize(new Dimension(width, height));
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
