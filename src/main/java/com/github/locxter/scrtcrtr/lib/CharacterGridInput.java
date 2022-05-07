package com.github.locxter.scrtcrtr.lib;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

// CharacterGridInput class
public class CharacterGridInput extends JScrollPane {
    // Attributes
    private int rowCount;
    private int columnCount;
    private ArrayList<ArrayList<JTextField>> inputGrid = new ArrayList<>();
    private JPanel panel = new JPanel();
    private GridBagConstraints constraints = new GridBagConstraints();

    // Constructor
    public CharacterGridInput(int rowCount, int columnCount) {
        super();
        // Create the input grid and assemble the panel
        for (int i = 0; i < rowCount; i++) {
            ArrayList<JTextField> inputGridRow = new ArrayList<>();
            for (int j = 0; j < columnCount; j++) {
                JTextField input = createInput();
                inputGridRow.add(input);
            }
            inputGrid.add(inputGridRow);
        }
        panel.setLayout(new GridBagLayout());
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                constraints.gridx = j;
                constraints.gridy = i;
                panel.add(inputGrid.get(i).get(j), constraints);
            }
        }
        // Configure the scroll pane
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.setViewportView(panel);
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    // Getter and setter
    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        // Resize if needed
        if (this.rowCount != rowCount) {
            if (this.rowCount < rowCount) {
                // Delete rows when currently to many exist
                for (int i = this.rowCount - 1; i < rowCount - 1; i++) {
                    ArrayList<JTextField> inputGridRow = new ArrayList<>();
                    for (int j = 0; j < columnCount; j++) {
                        JTextField input = createInput();
                        inputGridRow.add(input);
                    }
                    inputGrid.add(inputGridRow);
                }
            } else {
                // Add rows when currently to few exist
                for (int i = this.rowCount - 1; i > rowCount - 1; i--) {
                    inputGrid.remove(i);
                }
            }
            // Redraw the panel
            panel.removeAll();
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    constraints.gridx = j;
                    constraints.gridy = i;
                    panel.add(inputGrid.get(i).get(j), constraints);
                }
            }
            this.setViewportView(panel);
            this.rowCount = rowCount;
        }
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        // Resize if needed
        if (this.columnCount != columnCount) {
            if (this.columnCount < columnCount) {
                // Delete columns when currently to many exist
                for (int i = 0; i < rowCount; i++) {
                    ArrayList<JTextField> inputGridRow = inputGrid.get(i);
                    for (int j = this.columnCount - 1; j < columnCount - 1; j++) {
                        JTextField input = createInput();
                        inputGridRow.add(input);
                    }
                }
            } else {
                // Add rows when currently to few exist
                for (int i = 0; i < rowCount; i++) {
                    ArrayList<JTextField> inputGridRow = inputGrid.get(i);
                    for (int j = this.columnCount - 1; j > columnCount - 1; j--) {
                        inputGridRow.remove(j);
                    }
                }
            }
            // Redraw the panel
            panel.removeAll();
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    constraints.gridx = j;
                    constraints.gridy = i;
                    panel.add(inputGrid.get(i).get(j), constraints);
                }
            }
            this.setViewportView(panel);
            this.columnCount = columnCount;
        }
    }

    // Function to read the input grid
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

    // Function to write a character grid to the input grid
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

    // Helper function for creating an input
    private JTextField createInput() {
        JTextField input = new JTextField();
        Dimension dimension = new Dimension(24, 24);
        input.setMaximumSize(dimension);
        input.setMinimumSize(dimension);
        input.setPreferredSize(dimension);
        input.setHorizontalAlignment(JTextField.CENTER);
        input.setDocument(new LengthLimitedDocument(1));
        input.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        return input;
    }

}