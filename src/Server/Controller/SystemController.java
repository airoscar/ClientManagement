package Server.Controller;

// ENSF 519-2 Java Project I
// Server.Model.Person Management System
// Oscar Chen & Savith Jayasekera
// November 5 2018

import Client.ClientView.ClientAppView;
import Client.ClientView.ClientLoginView;
import Client.ClientView.LoadDataFromFile;
import Server.Model.DataModel;
import Shared.Person;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * Server.Controller that delegates between View and Data. <br>
 * Sets up View and Data.
 */
public class SystemController {

    private DataModel dataModel;
    private ClientAppView view;
    private ClientLoginView loginWindow;

    public SystemController() {
        dataModel = new DataModel();
        view = new ClientAppView();
        loginWindow = new ClientLoginView();
        setUp();
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
        ArrayList<Person> searchResults;

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
        Person person = new Person();
        person.setDataID(view.getClientID());
        person.setFirstName(view.getFirstName());
        person.setLastName(view.getLastName());
        person.setAddress(view.getAddress());
        person.setPostalCode(view.getPostalCode());
        person.setPhoneNumber(view.getPhoneNumber());
        person.setClientType(view.getClientType());

        if (view.getClientID().equalsIgnoreCase("")) {
            dataModel.addClient(person);
        } else {
            dataModel.updateClient(person);
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
                view.clearSearchBox();
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

    /**
     * Set up database log in. Initialize database. start View.
     */
    private void setUp() {

        ActionListener okButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataModel.setUpDatabase(loginWindow.getUsername(), loginWindow.getPassword(), loginWindow.getDBName());
                try {
                    dataModel.initializeDatabase();
                    setUpView();
                    readDataFromFile();
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null, error.getMessage());
                }
                loginWindow.setVisible(false);
                loginWindow.dispose();
            }
        };

        ActionListener canButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginWindow.setVisible(false);
                loginWindow.dispose();
            }
        };

        loginWindow.setActionListeners(okButtonListener, canButtonListener);
        loginWindow.setVisible(true);
    }
}