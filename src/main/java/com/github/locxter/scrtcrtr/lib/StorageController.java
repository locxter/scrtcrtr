package com.github.locxter.scrtcrtr.lib;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

// Storage controller class
public class StorageController {
    // Attribute
    private File file;

    // Constructors
    public StorageController() {
    }

    public StorageController(File file) {
        this();
        this.file = file;
    }

    // Getter and setter
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    // Method to read a character grid from file
    public ArrayList<ArrayList<Character>> readCharacterGrid() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            ArrayList<ArrayList<Character>> characterGrid = new ArrayList<>();
            int characterGridColumns = 0;
            String line;
            // Read the file character by character for each line
            while ((line = bufferedReader.readLine()) != null) {
                ArrayList<Character> characterGridRow = new ArrayList<>();
                for (char character : line.toCharArray()) {
                    characterGridRow.add(character);
                }
                if (line.length() > characterGridColumns) {
                    characterGridColumns = line.length();
                }
                characterGrid.add(characterGridRow);
            }
            bufferedReader.close();
            // Fix the formatting
            for (ArrayList<Character> characterGridRow : characterGrid) {
                if (characterGridRow.size() < characterGridColumns) {
                    for (int j = characterGridRow.size() - 1; j < characterGridColumns - 1; j++) {
                        characterGridRow.add(' ');
                    }
                }
            }
            return characterGrid;
        } catch (Exception exception) {
            // Display an error if something does not work as expected
            JOptionPane.showMessageDialog(null, "Reading file failed", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // Method to write a character grid to file
    public void writeCharacterGrid(ArrayList<ArrayList<Character>> characterGrid) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            // Write the file character by character for each line
            for (ArrayList<Character> characterGridRow : characterGrid) {
                for (Character character : characterGridRow) {
                    bufferedWriter.write(character.toString());
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception exception) {
            // Display an error if something does not work as expected
            JOptionPane.showMessageDialog(null, "Writing file failed", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
