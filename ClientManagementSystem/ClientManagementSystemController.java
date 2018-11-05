// ENSF 519-2 Java Project I
// Client Management System
// Oscar Chen & Savith Jayasekera
// November 5 2018

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClientManagementSystemController {

    private ClientManagementSystemData dataModel;
    private ClientManagementSystemView view;

    ClientManagementSystemController() {
        dataModel = new ClientManagementSystemData();
        view = new ClientManagementSystemView();
        setUpData();
        setUpView();
    }

    private void searchButtonPressed() throws Exception {
        view.clearSearchResults();
        String phrase = view.getSearchBoxTextFieldText();   //phrase to search for
        String column = "";     //the column in which to search for the phrase
        ArrayList<Client> searchResults;

        int selectedButton = view.getSelectedSearchButton();

        if (selectedButton == 0) {
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
        if (view.getSelectedSearchResult() == null) {
            view.clearClientInformation();
        } else {
            String selectedItem = view.getSelectedSearchResult();
            String[] details = selectedItem.split(",");
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
                listClicked();
            }
        };

        view.setUpActionListeners(listListener, searchButtonListener, clearSearchButtonListener, saveButtonListener,
                deleteButtonListener, clearButtonListener);

        view.setVisible(true);
    }

    /**
     * Set up data model
     */
    private void setUpData() {
        try {
            String username = JOptionPane.showInputDialog("Please enter username: ");
            String password = JOptionPane.showInputDialog("Please enter password: ");
            String database = JOptionPane.showInputDialog("Please enter name of the database: ", "clientsDB");
            dataModel.setUpDatabase(username, password, database);
            dataModel.initializeDatabase();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
