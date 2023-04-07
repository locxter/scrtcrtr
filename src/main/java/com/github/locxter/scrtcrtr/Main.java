package com.github.locxter.scrtcrtr;

import com.formdev.flatlaf.FlatDarkLaf;
import com.github.locxter.scrtcrtr.lib.CharacterInputGrid;
import com.github.locxter.scrtcrtr.lib.StorageController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

// Main class
public class Main {
    // Attributes
    static private File file;
    static private int rowCount = 16;
    static private int columnCount = 32;

    // Main method
    public static void main(String[] args) {
        StorageController storageController = new StorageController(file);
        // Set a pleasing LaF
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception exception) {
            System.out.println("Failed to initialize LaF.");
        }
        // UI components
        JFrame frame = new JFrame("scrtcrtr");
        JPanel panel = new JPanel();
        GridBagConstraints constraints = new GridBagConstraints();
        JButton openButton = new JButton("Open");
        JButton saveButton = new JButton("Save");
        JLabel rowCountLabel = new JLabel("Rows:");
        JSpinner rowCountInput = new JSpinner(new SpinnerNumberModel(rowCount, 1, 128, 1));
        JLabel columnCountLabel = new JLabel("Columns:");
        JSpinner columnCountInput = new JSpinner(new SpinnerNumberModel(columnCount, 1, 128, 1));
        CharacterInputGrid characterInputGrid = new CharacterInputGrid(rowCount, columnCount);
        JLabel aboutLabel = new JLabel("2022 locxter");
        // Add functions to the buttons and inputs
        openButton.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                storageController.setFile(file);
                ArrayList<ArrayList<Character>> characterGrid = storageController.readCharacterGrid();
                if (characterGrid != null) {
                    characterInputGrid.writeCharacterGrid(characterGrid);
                    rowCount = characterInputGrid.getRowCount();
                    columnCount = characterInputGrid.getColumnCount();
                    rowCountInput.setValue(rowCount);
                    columnCountInput.setValue(columnCount);
                }
            }
        });
        saveButton.addActionListener(event -> {
            if (file == null) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showSaveDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
                    storageController.setFile(file);
                }
            }
            if (file != null) {
                storageController.writeCharacterGrid(characterInputGrid.readCharacterGrid());
            }
        });
        rowCountInput.addChangeListener(e -> {
            rowCount = (int) rowCountInput.getValue();
            characterInputGrid.setRowCount(rowCount);
        });
        columnCountInput.addChangeListener(e -> {
            columnCount = (int) columnCountInput.getValue();
            characterInputGrid.setColumnCount(columnCount);
        });
        // Create the main panel
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GridBagLayout());
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(openButton, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(saveButton, constraints);
        constraints.fill = GridBagConstraints.RELATIVE;
        constraints.gridx = 2;
        constraints.gridy = 0;
        panel.add(rowCountLabel, constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 3;
        constraints.gridy = 0;
        panel.add(rowCountInput, constraints);
        constraints.fill = GridBagConstraints.RELATIVE;
        constraints.gridx = 4;
        constraints.gridy = 0;
        panel.add(columnCountLabel, constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 5;
        constraints.gridy = 0;
        panel.add(columnCountInput, constraints);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 6;
        panel.add(characterInputGrid, constraints);
        constraints.fill = GridBagConstraints.RELATIVE;
        constraints.weighty = 0;
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(aboutLabel, constraints);
        // Create the main window
        frame.setSize(new Dimension(640, 640));
        frame.setMinimumSize(new Dimension(480, 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);
    }
}
