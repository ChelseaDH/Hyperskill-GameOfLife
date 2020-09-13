package life;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

import static java.lang.Thread.sleep;

public class GameOfLife extends JFrame {
    JPanel topPanel;
    JPanel interactivePanel;
    Grid mapPanel;

    // interactivePanel components
    JLabel generationLabel;
    JLabel aliveLabel;
    JTextField mapSizeField;
    JToggleButton playPause;
    JButton resetButton;

    GridBagConstraints constraints;

    // Current universe
    Universe universe;

    // Main thread
    Thread mainThread;

    // Mapsize
    int mapSize;

    public GameOfLife() {
        super("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(500, 400));

        constraints = new GridBagConstraints();

        // Set fields
        this.mainThread = Thread.currentThread();
        this.mapSize = 0;

        // Initialise panels
        createTopPanel();
        createInteractivePanel();
        createMapPanel();

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

        // Add components
        addInteractivePanelComponents();

        // Set play/pause and reset buttons to be initially inactive
        playPause.setEnabled(false);
        resetButton.setEnabled(false);
    }

    private void addInteractivePanelComponents() {
        // Create label for mapSizeField
        JLabel mapSizeFieldLabel = new JLabel("Universe size");
        mapSizeFieldLabel.setLabelFor(mapSizeField);
        interactivePanel.add(mapSizeFieldLabel);

        // Text field to set universe size
        mapSizeField = new JTextField();
        mapSizeField.setMaximumSize(new Dimension(200, 50));
        // Add ActionListener
        mapSizeField.addCaretListener(e -> {
            // Boolean for valid input
            boolean isValid = false;
            boolean isEmpty = false;

            String text = mapSizeField.getText();
            int newMapSize = 0;

            if (text.equals("")) {
                isEmpty = true;
            } else {
                // Grab input
                try {
                    newMapSize = Integer.parseInt(text);

                    // Don't accept non-positive integers
                    if (newMapSize > 0) {
                        isValid = true;
                    }

                } catch (NumberFormatException ne) {
                }
            }

            // If we have an active universe, buttons are always enabled
            // Else, enable only if the input is valid
            playPause.setEnabled(isValid || universe != null);
            resetButton.setEnabled(isValid || universe != null);

            mapSizeField.setBackground(isValid || isEmpty ? Color.white : Color.red);

            mapSize = newMapSize;
        });
        interactivePanel.add(mapSizeField);

        // Add play/pause button
        playPause = new JToggleButton("Play");
        playPause.addItemListener(e -> {
            int state = e.getStateChange();

            // Pause -> play
            if (state == ItemEvent.SELECTED) {
                if (universe == null) {
                    this.universe = new Universe(mapSize);
                    updateDisplay();
                }

                playPause.setText("Pause");
            } else { // Play -> pause
                playPause.setText("Play");
            }
            mainThread.interrupt();
        });
        interactivePanel.add(playPause);

        // Add reset button
        resetButton = new JButton("Reset");
        resetButton.setName("ResetButton");
        resetButton.addActionListener(e -> {
            // Pause the simulation
            playPause.getModel().setSelected(false);

            // Check for different valid universe size
            if (universe.mapSize != mapSize && mapSize > 0) {
                this.universe = new Universe(mapSize);
            } else {
                this.universe.reset();
            }

            updateDisplay();
            mainThread.interrupt();
        });
        interactivePanel.add(resetButton);
    }

    private boolean isSimulationRunning() {
        return playPause.getModel().isSelected();
    }

    private void createMapPanel() {
        // Create mapPanel
        mapPanel = new Grid();

        // Set constraints and add to board
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(mapPanel, constraints);
    }

    // Add the updated universe information to GUI
    public void updateDisplay() {
        // Update generation and alive counters
        generationLabel.setText(String.format("Generation #%d", this.universe.getGenerationNumber()));
        aliveLabel.setText(String.format("Alive: %s", this.universe.currentGeneration.aliveCells()));

        // Update the map within grid
        mapPanel.setMap(this.universe.currentGeneration);
        if (this.isVisible()) {
            mapPanel.repaint();
        }
    }

    // Run the universe simulation
    public void runSimulation() {
        while (true) {
            if (isSimulationRunning()) {
                try {
                    // Add the current generation to the board
                    this.updateDisplay();

                    // Create the next generation
                    this.universe.advance();

                    // Sleep
                    sleep(100);
                } catch (InterruptedException e) {
                    continue;
                }
            } else {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    continue;
                }
            }
        }
    }


    // Class for displaying Maps
    public static class Grid extends JPanel {
        Map map;

        public void setMap(Map map) {
            this.map = map;
        }

        @Override
        public void paintComponent(Graphics g) {
            if (this.map == null) {
                return;
            }

            int gridSize = Math.min(this.getWidth(), this.getHeight());
            double cellSize = (double) gridSize / this.map.size;

            // Fill grid
            for (int i = 0; i < this.map.size; i++) {
                for (int j = 0; j < this.map.size; j++) {
                    g.setColor(this.map.grid[i][j].alive ? Color.black : Color.white);
                    g.fillRect((int) (i * cellSize), (int) (j * cellSize), (int) cellSize, (int) cellSize);
                }
            }

            // Print grid outline
            g.setColor(Color.lightGray);
            for (int i = 0; i <= this.map.size; i++) {
                g.drawLine(0, (int) (i * cellSize), gridSize, (int) (i * cellSize));
                g.drawLine((int) (i * cellSize), 0, (int) (i * cellSize), gridSize);
            }
        }
    }
}
