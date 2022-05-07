package com.github.locxter.scrtcrtr.lib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;

// StorageController class
public class StorageController {
    // Attribute
    private File file;

    // Constructor
    public StorageController(File file) {
        this.file = file;
    }

    // Getter and setter
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    // Function to read a character grid from file
    public ArrayList<ArrayList<Character>> readCharacterGrid() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            ArrayList<ArrayList<Character>> characterGrid = new ArrayList<>();
            int characterGridColumns = 0;
            String line = "";
            // Read the file character by character for each line
            while ((line = bufferedReader.readLine()) != null) {
                ArrayList<Character> characterGridRow = new ArrayList<>();
                for (Character character : line.toCharArray()) {
                    characterGridRow.add(character);
                }
                if (line.length() > characterGridColumns) {
                    characterGridColumns = line.length();
                }
                characterGrid.add(characterGridRow);
            }
            bufferedReader.close();
            // Fix the formatting
            for (int i = 0; i < characterGrid.size(); i++) {
                ArrayList<Character> characterGridRow = characterGrid.get(i);
                if (characterGridRow.size() < characterGridColumns) {
                    for (int j = characterGridRow.size() - 1; j < characterGridColumns - 1; j++) {
                        characterGridRow.add(' ');
                    }
                }
            }
            return characterGrid;
        } catch (Exception exception) {
            // Display an error if something does not work as expected
            JOptionPane.showMessageDialog(null, "Reading file failed", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // Function to write a character grid to file
    public void writeCharacterGrid(ArrayList<ArrayList<Character>> characterGrid) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            // Write the file character by character for each line
            for (int i = 0; i < characterGrid.size(); i++) {
                ArrayList<Character> characterGridRow = characterGrid.get(i);
                for (Character character : characterGridRow) {
                    bufferedWriter.write(character.toString());
                }
                if (i < characterGrid.size() - 1) {
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.close();
        } catch (Exception exception) {
            // Display an error if something does not work as expected
            JOptionPane.showMessageDialog(null, "Writing file failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}