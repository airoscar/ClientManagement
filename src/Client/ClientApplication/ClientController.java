package Client.ClientApplication;

import Client.ClientView.ClientAppView;
import Client.ClientView.LoadDataFromFile;
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
public class ClientController {

    private ClientAppView view;
    private ServerConnector serverConnector;

    public ClientController(String serverAddress, int portNumber) {
        view = new ClientAppView();
        serverConnector = new ServerConnector(serverAddress, portNumber);
    }

    /**
     * Called upon when the search button is pressed.
     */
    private void searchButtonPressed() throws Exception {
        view.clearSearchResults();
        String phrase = view.getSearchBoxTextFieldText();   //phrase to search for
        String column = "";     //the column in which to search for the phrase
        ArrayList<Person> searchResults;

        int selectedButton = view.getSelectedSearchButton();//Read radio button selection

        if (selectedButton == 0) {  //generate 'column' attribute based on radio button selection
            JOptionPane.showMessageDialog(null, "Please make a selection " +
                    "before clicking the search button");
            return;
        } else if (selectedButton == 1) {
            column = "id";
        } else if (selectedButton == 2) {
            column = "lastname";
        } else if (selectedButton == 3) {
            column = "clientType";
        }

        searchResults = serverConnector.sendSearchResultDataPack(phrase, column);

        if (searchResults != null) {
            view.setSearchResults(searchResults);
        } else {
            JOptionPane.showMessageDialog(null, "Search failed.");
        }
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
            if (serverConnector.wasClientAdditionSuccessful(person)){
                JOptionPane.showMessageDialog(null, "Client added successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Client addition failed.");
            }

        } else {
            if (serverConnector.wasClientModificationSuccessful(person)){
                JOptionPane.showMessageDialog(null, "Client modified successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Client modification failed.");
            }
        }
        view.clearClientInformation();
        view.clearSearchResults();
    }

    /**
     * Gets called when the delete button is pressed.
     *
     */
    private void deleteButtonPressed() throws Exception {
        String clientID = view.getClientID();
        if (clientID.equalsIgnoreCase("")) {
            return;
        } else {
            if (serverConnector.wasClientDeletionSuccessful(new Person(clientID))){
                JOptionPane.showMessageDialog(null, "Client deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Client deletion failed.");
            }
        }
        view.clearClientInformation();
        view.clearSearchResults();
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
            JOptionPane.showMessageDialog(null, populatePersonsFromFile(reader));
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
                //TODO: Can create login window and create it here
                try {
                    setUpView();
                    readDataFromFile();
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null, error.getMessage());
                }

            }
        };

//        ActionListener canButtonListener = new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                loginWindow.setVisible(false);
//                loginWindow.dispose();
//            }
//        };
//
//        loginWindow.setActionListeners(okButtonListener, canButtonListener);
//        loginWindow.setVisible(true);
    }

    /**
     * Receives an input of type BufferedReader. </br>
     * Upload a txt file of client data to database. </br>
     * The file must be structured such that each line represent a client. </br>
     * The client's information must be separated by a semi-colon as such: </br>
     * first name; last name; address; postal code; phone number; client type </br>
     * See example txt file.
     */
    public String populatePersonsFromFile(BufferedReader reader) throws Exception {

        ArrayList<Person> clientsReadFromFile = new ArrayList<>();

        String line;

        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            String[] details = line.split(";");
            Person person = new Person();
            person.setFirstName(details[0]);
            person.setLastName(details[1]);
            person.setAddress(details[2]);
            person.setPostalCode(details[3]);
            person.setPhoneNumber(details[4]);
            person.setClientType(details[5]);

            clientsReadFromFile.add(person);
        }
        return serverConnector.sendMultipleClientsToAddToDatabase(clientsReadFromFile);
    }
}
