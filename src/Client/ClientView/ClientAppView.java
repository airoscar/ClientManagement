package Client.ClientView;

// ENSF 519-2 Java Project I
// Server.Model.Person Management System
// Oscar Chen & Savith Jayasekera
// November 5 2018

import Shared.Person;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Creates the main window of the Server.Model.Person Management System user interface
 */
public class ClientAppView extends JFrame {

    //the search results should be saved in this DefaultListModel
    private DefaultListModel<String> searchResults = new DefaultListModel<>();
    private JList<String> searchResultList = new JList<>(searchResults);

    private JRadioButton clientIDSearchButton = new JRadioButton("Client ID");
    private JRadioButton lastNameSearchButton = new JRadioButton("Last Name");
    private JRadioButton clientTypeSearchButton = new JRadioButton("Client Type");

    private JTextField searchBoxTextField = new JTextField(24);
    private JButton searchButton = new JButton("Search");
    private JButton clearSearchButton = new JButton("Clear Search");

    private JTextField clientIDTextField = new JTextField("");
    private JTextField firstNameTextField = new JTextField("");
    private JTextField lastNameTextField = new JTextField("");
    private JTextField addressTextField = new JTextField("");
    private JTextField postalCodeTextField = new JTextField("");
    private JTextField phoneNumberTextField = new JTextField("");
    private JSpinner clientTypeSpinner = new JSpinner(new SpinnerListModel(new String[]{"R", "C"}));

    private JButton saveButton = new JButton("Save");
    private JButton deleteButton = new JButton("Delete");
    private JButton clearButton = new JButton("Clear");


    /**
     * Creates the main window of the user interface
     */
    public ClientAppView() {
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //opens the window centered at the middle of the screen
        setLocationRelativeTo(null);

        addPanelsToView();
        setResizable(false);
    }

    /**
     * Add panels to view.
     */
    private void addPanelsToView() {
        JPanel northPanel = northPanel();
        JPanel centerPanel = centerPanel();
        add(BorderLayout.NORTH, northPanel);
        add(BorderLayout.CENTER, centerPanel);
    }

    /**
     * Set up north panel.
     *
     * @return
     */
    private JPanel northPanel() {
        JPanel northPanel = new JPanel();
        northPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        JLabel screenTitle = new JLabel("Client Management Screen", SwingConstants.CENTER);
        screenTitle.setFont(new Font("SANS_SERIF", Font.PLAIN, 30));
        northPanel.add(screenTitle);
        northPanel.setBackground(new Color(148, 148, 184));
        return northPanel;
    }

    /**
     * Set up center panel.
     *
     * @return
     */
    private JPanel centerPanel() {

        //This panel is the main panel with two sections split vertically: Search Server.Model.Person section on the left
        //and the Server.Model.Person Information section on the right
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2));

        //This panel is the Search Server.Model.Person section. It is split horizontally into: Search Selection on top and
        //Search Results on the bottom
        JPanel searchSection = new JPanel();
        searchSection.setBorder(BorderFactory.createLineBorder(Color.black));
        searchSection.setLayout(new GridLayout(2, 1));

        //Creates the Search Selection Panel
        JPanel searchSelection = new JPanel();

        searchSelection.setBorder(BorderFactory.createLineBorder(Color.black));
        searchSelection.setLayout(new BorderLayout(0, 50));

        searchSelection.add(new JLabel("Search Clients", SwingConstants.CENTER), BorderLayout.NORTH);

        JPanel searchSelectionCenterPanel = new JPanel();
        searchSelectionCenterPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        searchSelectionCenterPanel.setLayout(new BorderLayout(0, 10));

        JLabel searchSelectionExplanation = new JLabel("Select type of search to be performed:");
        searchSelectionExplanation.setFont(new Font("SANS_SERIF", Font.PLAIN, 12));
        searchSelectionCenterPanel.add(searchSelectionExplanation, BorderLayout.NORTH);

        ButtonGroup searchSelectionButtons = new ButtonGroup();
        searchSelectionButtons.add(clientIDSearchButton);
        searchSelectionButtons.add(lastNameSearchButton);
        searchSelectionButtons.add(clientTypeSearchButton);

        JPanel searchSelectionCenterPanelButtons = new JPanel();
        searchSelectionCenterPanelButtons.setLayout(new BoxLayout(searchSelectionCenterPanelButtons, BoxLayout.Y_AXIS));
        searchSelectionCenterPanelButtons.add(clientIDSearchButton);
        searchSelectionCenterPanelButtons.add(lastNameSearchButton);
        searchSelectionCenterPanelButtons.add(clientTypeSearchButton);

        clientIDSearchButton.setAlignmentX(LEFT_ALIGNMENT);
        lastNameSearchButton.setAlignmentX(LEFT_ALIGNMENT);
        clientTypeSearchButton.setAlignmentX(LEFT_ALIGNMENT);

        searchSelectionCenterPanel.add(searchSelectionCenterPanelButtons, BorderLayout.CENTER);

        JPanel searchSelectionSouthPanel = new JPanel();
        searchSelectionSouthPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 40, 10));
        searchSelectionSouthPanel.setLayout(new BorderLayout(10, 30));

        JLabel searchParameterExplanation = new JLabel("Enter the search parameter below:");
        searchParameterExplanation.setFont(new Font("SANS_SERIF", Font.PLAIN, 12));
        searchSelectionSouthPanel.add(searchParameterExplanation, BorderLayout.NORTH);

        searchSelectionSouthPanel.add(searchBoxTextField, BorderLayout.WEST);
        searchSelectionSouthPanel.add(searchButton, BorderLayout.CENTER);
        searchSelectionSouthPanel.add(clearSearchButton, BorderLayout.EAST);

        //completing the search selection section
        searchSelection.add(searchSelectionCenterPanel, BorderLayout.CENTER);
        searchSelection.add(searchSelectionSouthPanel, BorderLayout.SOUTH);

        //Creates the Search Results Panel
        JPanel searchResultsPanel = new JPanel();
        searchResultsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        searchResultsPanel.setLayout(new BorderLayout(0, 10));
        JLabel searchResultsPanelLabel = new JLabel("Search Results", SwingConstants.CENTER);
        searchResultsPanel.add(searchResultsPanelLabel, BorderLayout.NORTH);
        searchResultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchResultsPanel.add(searchResultList, BorderLayout.CENTER);
        searchResultsPanel.add(new JScrollPane(searchResultList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));

        searchSection.add(searchSelection);
        searchSection.add(searchResultsPanel);

        //Creates the Server.Model.Person Information section
        JPanel clientInformationSection = new JPanel();
        clientInformationSection.setBorder(BorderFactory.createLineBorder(Color.black));
        clientInformationSection.setLayout(new BorderLayout(0, 30));
        JLabel clientInformationSectionLabel = new JLabel("Client Information", SwingConstants.CENTER);
        clientInformationSection.add(clientInformationSectionLabel, BorderLayout.NORTH);

        JPanel clientInformationSectionCenterPanel = new JPanel();
        clientInformationSectionCenterPanel.setBorder(new EmptyBorder(40, 90, 40, 90));
        clientInformationSectionCenterPanel.setLayout(new GridLayout(7, 2, -10, 45));

        clientInformationSectionCenterPanel.add(new JLabel("Client ID:"));
        clientIDTextField.setEditable(false);
        clientInformationSectionCenterPanel.add(clientIDTextField);
        clientInformationSectionCenterPanel.add(new JLabel("First Name:"));
        clientInformationSectionCenterPanel.add(firstNameTextField);
        clientInformationSectionCenterPanel.add(new JLabel("Last Name:"));
        clientInformationSectionCenterPanel.add(lastNameTextField);
        clientInformationSectionCenterPanel.add(new JLabel("Address:"));
        clientInformationSectionCenterPanel.add(addressTextField);
        clientInformationSectionCenterPanel.add(new JLabel("Postal Code:"));
        clientInformationSectionCenterPanel.add(postalCodeTextField);
        clientInformationSectionCenterPanel.add(new JLabel("Phone Number:"));
        clientInformationSectionCenterPanel.add(phoneNumberTextField);
        clientInformationSectionCenterPanel.add(new JLabel("Client Type:"));
        clientInformationSectionCenterPanel.add(clientTypeSpinner);

        clientInformationSection.add(clientInformationSectionCenterPanel, BorderLayout.CENTER);

        JPanel clientInformationSectionSouthPanel = new JPanel();
        clientInformationSectionSouthPanel.setLayout(new GridLayout(1, 3, 30, 0));
        clientInformationSectionSouthPanel.setBorder(new EmptyBorder(0, 50, 40, 50));
        clientInformationSectionSouthPanel.add(saveButton);
        clientInformationSectionSouthPanel.add(deleteButton);
        clientInformationSectionSouthPanel.add(clearButton);

        clientInformationSection.add(clientInformationSectionSouthPanel, BorderLayout.SOUTH);

        centerPanel.add(searchSection);
        centerPanel.add(clientInformationSection);

        return centerPanel;
    }

    /**
     * Set up listeners for buttons and list.
     *
     * @param searchResultsListListener
     * @param searchButtonListener
     * @param clearSearchButtonListener
     * @param saveButtonListener
     * @param deleteButtonListener
     * @param clearButtonListener
     */
    public void setUpActionListeners(ListSelectionListener searchResultsListListener, ActionListener searchButtonListener,
                                     ActionListener clearSearchButtonListener, ActionListener saveButtonListener,
                                     ActionListener deleteButtonListener, ActionListener clearButtonListener) {

        searchResultList.addListSelectionListener(searchResultsListListener);
        searchButton.addActionListener(searchButtonListener);
        clearSearchButton.addActionListener(clearSearchButtonListener);
        saveButton.addActionListener(saveButtonListener);
        deleteButton.addActionListener(deleteButtonListener);
        clearButton.addActionListener(clearButtonListener);
    }

    /**
     * Clear the search parameter box
     */
    public void clearSearchBox (){
        searchBoxTextField.setText("");
    }

    /**
     * Clear search result.
     */
    public void clearSearchResults() {
        searchResults.clear();
    }

    /**
     * Clear input text fields.
     */
    public void clearClientInformation() {
        clientIDTextField.setText("");
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        addressTextField.setText("");
        postalCodeTextField.setText("");
        phoneNumberTextField.setText("");
    }

    /**
     * Get value of selected list item.
     *
     * @return
     */
    public String getSelectedSearchResult() {
        return searchResultList.getSelectedValue();
    }

    public String getSearchBoxTextFieldText() {
        return searchBoxTextField.getText();
    }

    public DefaultListModel<String> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(ArrayList<Person> searchResults) {
        for (Person result : searchResults) {
            this.searchResults.addElement(result.toString());
        }
    }

    /**
     * This method returns an integer representation of the radio button that is currently selected.
     *
     * @return
     */
    public int getSelectedSearchButton() {
        if (clientIDSearchButton.isSelected()) {
            return 1;
        } else if (lastNameSearchButton.isSelected()) {
            return 2;
        } else if (clientTypeSearchButton.isSelected()) {
            return 3;
        }
        return 0;
    }

    public String getClientID() {
        return clientIDTextField.getText();
    }

    public String getFirstName() {
        return firstNameTextField.getText();
    }

    public String getLastName() {
        return lastNameTextField.getText();
    }

    public String getAddress() {
        return addressTextField.getText();
    }

    public String getPostalCode() {
        return postalCodeTextField.getText();
    }

    public String getPhoneNumber() {
        return phoneNumberTextField.getText();
    }

    public String getClientType() {
        return (String) clientTypeSpinner.getValue();
    }

    public void setClientIDText(String text) {
        this.clientIDTextField.setText(text);
    }

    public void setFirstNameTextField(String text) {
        this.firstNameTextField.setText(text);
    }

    public void setLastNameTextField(String text) {
        this.lastNameTextField.setText(text);
    }

    public void setAddressTextField(String text) {
        this.addressTextField.setText(text);
    }

    public void setPostalCodeTextField(String text) {
        this.postalCodeTextField.setText(text);
    }

    public void setPhoneNumberTextField(String text) {
        this.phoneNumberTextField.setText(text);
    }

    public void setClientTypeSpinner(String text) {
        this.clientTypeSpinner.setValue(text);
    }

    /**
     * Update value of text fields from a array of String types.
     *
     * @param texts
     */
    public void updateTextFields(String[] texts) {
        clientIDTextField.setText(texts[0].trim());
        firstNameTextField.setText(texts[1].trim());
        lastNameTextField.setText(texts[2].trim());
        addressTextField.setText(texts[3].trim());
        postalCodeTextField.setText(texts[4].trim());
        phoneNumberTextField.setText(texts[5].trim());
        clientTypeSpinner.setValue(texts[6].trim());
    }
}

