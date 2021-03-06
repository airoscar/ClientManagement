package Client.ClientView;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * This class is used to display a series of prompts for users to input a text file, </br>
 * which contains clients info to be uploaded to the database.
 */
public class LoadDataFromFileView {

    public LoadDataFromFileView() {

    }

    /**
     * Prompts user if they would like to upload a text file.</br>
     * If yes, ask for text file.
     * @return
     */
    public BufferedReader getBufferedReader() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null,
                "Would you like to upload from a text file?", "Warning", dialogButton);

        if (dialogResult == JOptionPane.NO_OPTION || dialogResult == JOptionPane.CLOSED_OPTION) {
            return null;
        }

        try {
            File file = new File(JOptionPane.showInputDialog(null, "Enter the file name:",
                    "clients.txt"));
            BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));
            return reader;
        } catch (FileNotFoundException error) {
            JOptionPane.showMessageDialog(null, "File not found.");
        } catch (Exception error) {
            //JOptionPane.showMessageDialog(null, "Failed to load data from file.");
        }
        return null;
    }
}
