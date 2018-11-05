
// ENSF 519-2 Java Project I
// Client Management System
// Oscar Chen & Savith Jayasekera
// November 5 2018

import java.sql.SQLException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class ClientManagementSystemController {

    private ClientManagementSystemData dataModel;
    private ClientManagementSystemView view;


    ClientManagementSystemController() {
        dataModel = new ClientManagementSystemData();
        view = new ClientManagementSystemView();


        try {
            dataModel.setUpDatabase("root", "greencreatures", "clientsDB");
            dataModel.initializeDatabase();


            //TODO: TEST CODE: DELETE AFTER TESTING
            dataModel.addClient(new Client("Co", "Guy", "123 Sri lankadabada Drive", "A7K5J5", "3063731234", "C"));
            dataModel.addClient(new Client("Mot", "Teres", "34 Sri Drive", "A7K 5J5", "306-373-3065", "c"));

            setUpView();
        } catch (Exception e) {
            e.printStackTrace();
        }

        view.setVisible(true);

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

    private void deleteButtonPressed() throws Exception {
        String clientID = view.getClientID();
        if (clientID.equalsIgnoreCase("")) {
            return;
        } else {
            dataModel.deleteClient(Integer.parseInt(clientID));
        }
        view.clearClientInformation();
        searchButtonPressed();
    }

    private void listClicked() throws Exception {
        String selectedItem = view.getSelectedSearchResult();
        String[] details = selectedItem.split(",");
        view.updateTextFields(details);
    }

    /**
     * Sets up the UI View.</br>
     * Creates various listeners for the View.
     */
    private void setUpView() throws Exception {

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

        ActionListener clearSearchButtonListener = e -> view.clearSearchResults();  //set up listener for Search Clear button


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

        MouseListener listListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    listClicked();
                }catch (Exception error) {
                    JOptionPane.showMessageDialog(null, error.getMessage());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                return;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                return;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                return;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                return;
            }
        };

        view.setUpActionListeners(listListener, searchButtonListener, clearSearchButtonListener, saveButtonListener,
                deleteButtonListener, clearButtonListener);

        //TODO: Complete after JList display is configured
        /*MouseListener searchResultsListener = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                String selectedItem = view.getSelectedSearchResult();

            }
        };*/
    }


    private void setUpData() {
        try {
            dataModel.initializeDatabase();
        } catch (SQLException e) {

        }
    }

    //TODO: TESTING METHOD: DELETE AFTER TESTING
    public static void main(String[] args) {

        new ClientManagementSystemController();


    }


}
