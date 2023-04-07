package com.github.locxter.scrtcrtr.lib;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

// Character grid input class
public class CharacterInputGrid extends JScrollPane {
    private final ArrayList<ArrayList<JTextField>> inputGrid = new ArrayList<>();
    private final JPanel panel = new JPanel();
    private final GridBagConstraints constraints = new GridBagConstraints();
    // Attributes
    private int rowCount = 2;
    private int columnCount = 2;

    // Constructors
    public CharacterInputGrid() {
        super();
        // Configure the scroll pane
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.setLayout(new GridBagLayout());
        // Create the input grid and assemble the panel
        for (int i = 0; i < rowCount; i++) {
            ArrayList<JTextField> inputGridRow = new ArrayList<>();
            for (int j = 0; j < columnCount; j++) {
                JTextField input = createInput();
                inputGridRow.add(input);
            }
            inputGrid.add(inputGridRow);
        }
        assemblePanel();
    }

    public CharacterInputGrid(int rowCount, int columnCount) {
        this();
        setRowCount(rowCount);
        setColumnCount(columnCount);
    }

    // Helper method for creating an input
    private JTextField createInput() {
        JTextField input = new JTextField();
        Dimension dimension = new Dimension(24, 24);
        input.setMaximumSize(dimension);
        input.setMinimumSize(dimension);
        input.setPreferredSize(dimension);
        input.setHorizontalAlignment(JTextField.CENTER);
        input.setDocument(new LengthLimitedDocument(1));
        input.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        // Change selected input using the arrow keys
        input.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                int key = event.getKeyCode();
                int row = 0;
                int column = 0;
                for (int i = 0; i < rowCount; i++) {
                    ArrayList<JTextField> inputGridRow = inputGrid.get(i);
                    if (inputGridRow.contains(input)) {
                        column = inputGridRow.indexOf(input);
                        row = i;
                    }
                }
                switch (key) {
                    case KeyEvent.VK_UP -> {
                        if (row > 0) {
                            inputGrid.get(row - 1).get(column).requestFocus();
                        }
                    }
                    case KeyEvent.VK_DOWN -> {
                        if (row < rowCount - 1) {
                            inputGrid.get(row + 1).get(column).requestFocus();
                        }
                    }
                    case KeyEvent.VK_LEFT -> {
                        if (column > 0) {
                            inputGrid.get(row).get(column - 1).requestFocus();
                        }
                    }
                    case KeyEvent.VK_RIGHT -> {
                        if (column < columnCount - 1) {
                            inputGrid.get(row).get(column + 1).requestFocus();
                        }
                    }
                }
            }
        });
        return input;
    }

    // Helper method for assembling the panel
    private void assemblePanel() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                constraints.gridx = j;
                constraints.gridy = i;
                panel.add(inputGrid.get(i).get(j), constraints);
            }
        }
        setViewportView(panel);
    }

    // Getter and setter
    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        // Resize if needed
        if (this.rowCount != rowCount) {
            if (this.rowCount < rowCount) {
                // Add rows when currently too few exist
                for (int i = this.rowCount - 1; i < rowCount - 1; i++) {
                    ArrayList<JTextField> inputGridRow = new ArrayList<>();
                    for (int j = 0; j < columnCount; j++) {
                        JTextField input = createInput();
                        inputGridRow.add(input);
                    }
                    inputGrid.add(inputGridRow);
                }
            } else {
                // Delete rows when currently to many exist
                inputGrid.subList(rowCount, this.rowCount).clear();
            }
            // Rebuild the panel
            panel.removeAll();
            this.rowCount = rowCount;
            assemblePanel();
        }
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        // Resize if needed
        if (this.columnCount != columnCount) {
            if (this.columnCount < columnCount) {
                // Add columns when currently too few exist
                for (int i = 0; i < rowCount; i++) {
                    ArrayList<JTextField> inputGridRow = inputGrid.get(i);
                    for (int j = this.columnCount - 1; j < columnCount - 1; j++) {
                        JTextField input = createInput();
                        inputGridRow.add(input);
                    }
                }
            } else {
                // Delete columns when currently to many exist
                for (int i = 0; i < rowCount; i++) {
                    ArrayList<JTextField> inputGridRow = inputGrid.get(i);
                    if (this.columnCount > columnCount) {
                        inputGridRow.subList(columnCount, this.columnCount).clear();
                    }
                }
            }
            // Rebuild the panel
            panel.removeAll();
            this.columnCount = columnCount;
            assemblePanel();
        }
    }

    // Method to read the input grid
    public ArrayList<ArrayList<Character>> readCharacterGrid() {
        ArrayList<ArrayList<Character>> characterGrid = new ArrayList<>();
        for (ArrayList<JTextField> inputGridRow : inputGrid) {
            ArrayList<Character> characterGridRow = new ArrayList<>();
            for (JTextField input : inputGridRow) {
                if (input.getText().isEmpty()) {
                    characterGridRow.add(' ');
                } else {
                    characterGridRow.add(input.getText().charAt(0));
                }
            }
            characterGrid.add(characterGridRow);
        }
        return characterGrid;
    }

    // Method to write a character grid to the input grid
    public void writeCharacterGrid(ArrayList<ArrayList<Character>> characterGrid) {
        // Resize the input grid to the size of the character grid
        setRowCount(characterGrid.size());
        setColumnCount(characterGrid.get(0).size());
        // Actually write the characters
        for (int i = 0; i < rowCount; i++) {
            ArrayList<JTextField> inputGridRow = inputGrid.get(i);
            ArrayList<Character> characterGridRow = characterGrid.get(i);
            for (int j = 0; j < columnCount; j++) {
                Character character = characterGridRow.get(j);
                JTextField input = inputGridRow.get(j);
                if (character == ' ') {
                    input.setText("");
                } else {
                    input.setText(character.toString());
                }
            }
        }
    }
}
