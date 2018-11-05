// ENSF 519-2 Java Project I
// Client Management System
// Oscar Chen & Savith Jayasekera
// November 5 2018

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * Controller that delegates between View and Data. </br>
 * Sets up View and Data.
 */
public class ClientManagementSystemController {

    private ClientManagementSystemData dataModel;
    private ClientManagementSystemView view;

    ClientManagementSystemController() throws ClientDataBaseStartUpException {
        dataModel = new ClientManagementSystemData();
        view = new ClientManagementSystemView();
        if (setUpData() == 0) { //failed to set up database
            throw new ClientDataBaseStartUpException();
        } else {
            setUpView();
            readDataFromFile();
        }
    }

    /**
     * Called upon when the search button is pressed.
     *
     * @throws Exception
     */
    private void searchButtonPressed() throws Exception {
        view.clearSearchResults();
        String phrase = view.getSearchBoxTextFieldText();   //phrase to search for
        String column = "";     //the column in which to search for the phrase
        ArrayList<Client> searchResults;

        int selectedButton = view.getSelectedSearchButton();//Read radio button selection

        if (selectedButton == 0) {  //generate 'column' attribute based on radio button selection
            JOptionPane.showMessageDialog(null, "Please make a selection before clicking the search button");
            return;
        } else if (selectedButton == 1) {
            column = "id";
        } else if (selectedButton == 2) {
            column = "lastname";
        } else if (selectedButton == 3) {
            column = "clientType";
        }
        searchResults = dataModel.searchColumn(phrase, column);
        view.setSearchResults(searchResults);
    }

    /**
     * Gets called when the save button is pressed.
     *
     * @throws Exception
     */
    private void saveButtonPressed() throws Exception {
        Client client = new Client();
        client.setDataID(view.getClientID());
        client.setFirstName(view.getFirstName());
        client.setLastName(view.getLastName());
        client.setAddress(view.getAddress());
        client.setPostalCode(view.getPostalCode());
        client.setPhoneNumber(view.getPhoneNumber());
        client.setClientType(view.getClientType());

        if (view.getClientID().equalsIgnoreCase("")) {
            dataModel.addClient(client);
        } else {
            dataModel.updateClient(client);
        }
        searchButtonPressed();
    }

    /**
     * Gets called when the delete button is pressed.
     *
     * @throws Exception
     */
    private void deleteButtonPressed() throws Exception {
        String clientID = view.getClientID();
        if (clientID.equalsIgnoreCase("")) {
            return;
        } else {
            dataModel.deleteClient(clientID);
        }
        view.clearClientInformation();
        searchButtonPressed();
    }

    private void listClicked() {
        String selection = view.getSelectedSearchResult();
        if (selection == null) {
            view.clearClientInformation();
        } else {
            String[] details = selection.split(",");
            view.updateTextFields(details);
        }
    }

    /**
     * Sets up the UI View.</br>
     * Creates various listeners for the View.
     */
    private void setUpView() {

        ActionListener searchButtonListener = new ActionListener() {    //set up listener for Search button
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    searchButtonPressed();
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null, error.getMessage());
                }
            }
        };

        ActionListener clearSearchButtonListener = new ActionListener() {   //set up listener for Search Clear button
            @Override
            public void actionPerformed(ActionEvent e) {
                view.clearSearchResults();
            }
        };

        ActionListener saveButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveButtonPressed();
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null, error.getMessage());
                }
            }
        };

        ActionListener clearButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.clearClientInformation();
            }
        };

        ActionListener deleteButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteButtonPressed();

                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null, error.getMessage());
                }
            }
        };

        ListSelectionListener listListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    listClicked();
                }
            }
        };

        view.setUpActionListeners(listListener, searchButtonListener, clearSearchButtonListener, saveButtonListener,
                deleteButtonListener, clearButtonListener);

        view.setVisible(true);
    }

    /**
     * Set up data model
     */
    private int setUpData() {
        try {
            String username = JOptionPane.showInputDialog("Please enter username: ");
            String password = JOptionPane.showInputDialog("Please enter password: ");
            String database = JOptionPane.showInputDialog("Please enter name of the database: ", "clientsDB");
            dataModel.setUpDatabase(username, password, database);
            dataModel.initializeDatabase();
            return 1;   //return 1 if database is set up
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return 0;   //return 0 if exception encountered during data set up.
        }
    }

    /**
     * Prompt user to upload data from text file to database.
     */
    private void readDataFromFile() {
        BufferedReader reader = new LoadDataFromFile().getBufferedReader();
        if (reader == null) {
            return;
        }
        try {
            dataModel.uploadFileToDatabase(reader);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
