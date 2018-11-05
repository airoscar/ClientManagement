
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
import java.sql.SQLException;
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


        } catch (Exception e) {
            e.printStackTrace();
        }
        setUpView();
        view.setVisible(true);

    }

    /**
     * Sets up the UI View.</br>
     * Creates various listeners for the View.
     */
    private void setUpView() {

        ActionListener searchButtonListener = new ActionListener() {    //set up listener for Search button
            @Override
            public void actionPerformed(ActionEvent e) {
                view.clearSearchResults();
                String phrase = view.getSearchBoxTextFieldText();   //phrase to search for
                String column = "";     //the column in which to search for the phrase
                ArrayList<Client> searchResults;

                int selectedButton = view.getSelectedSearchButton();

                if (selectedButton == 0) {
                    JOptionPane.showMessageDialog(null, "Please make a selection before clicking the search button");
                    return;
                } else if (selectedButton == 1){
                    column = "id";
                } else if (selectedButton == 2){
                    column = "lastname";
                } else if (selectedButton == 3){
                    column = "clientType";
                }

                try {
                    searchResults = dataModel.searchColumn(phrase, column);

                } catch (SQLException exp) {
                    JOptionPane.showMessageDialog(null, "Search Failed");
                    return;
                }

                view.setSearchResults(searchResults);
            }
        };

        ActionListener clearSearchButtonListener = e -> view.clearSearchResults();  //set up listener for Search Clear button



        view.setUpActionListeners(searchButtonListener, clearSearchButtonListener);

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
    public static void main (String [] args) {

        new ClientManagementSystemController();


    }


}
