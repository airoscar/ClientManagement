// ENSF 519-2 Java Project I
// Client Management System
// Oscar Chen & Savith Jayasekera
// November 5 2018

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Creates the main window of the Client Management System user interface
 */
public class ClientManagementSystemView extends JFrame {

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
    public ClientManagementSystemView() {
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //opens the window centered at the middle of the screen
        setLocationRelativeTo(null);

        addPanelsToView();
        setResizable(false);
    }

    private void addPanelsToView() {

        JPanel northPanel = northPanel();
        JPanel centerPanel = centerPanel();


        add(BorderLayout.NORTH, northPanel);
        add(BorderLayout.CENTER, centerPanel);
    }

    private JPanel northPanel() {
        JPanel northPanel = new JPanel();
        northPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        JLabel screenTitle = new JLabel("Client Management Screen", SwingConstants.CENTER);
        screenTitle.setFont(new Font("SANS_SERIF", Font.PLAIN, 30));
        northPanel.add(screenTitle);
        northPanel.setBackground(new Color(148, 148, 184));

        return northPanel;
    }


    private JPanel centerPanel() {

        //This panel is the main panel with two sections split vertically: Search Client section on the left
        //and the Client Information section on the right
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2));

        //This panel is the Search Client section. It is split horizontally into: Search Selection on top and
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
//        JList searchResultList = new JList(searchResults);
        JLabel searchResultsPanelLabel = new JLabel("Search Results", SwingConstants.CENTER);
        searchResultsPanel.add(searchResultsPanelLabel, BorderLayout.NORTH);
        searchResultsPanel.add(searchResultList, BorderLayout.CENTER);
        searchResultsPanel.add(new JScrollPane(searchResultList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));

        searchSection.add(searchSelection);
        searchSection.add(searchResultsPanel);

        //Creates the Client Information section
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

    public void setUpActionListeners(MouseListener searchResultsListListener, ActionListener searchButtonListener,
                                     ActionListener clearSearchButtonListener, ActionListener saveButtonListener,
                                     ActionListener deleteButtonListener, ActionListener clearButtonListener) {

        searchResultList.addMouseListener(searchResultsListListener);
        searchButton.addActionListener(searchButtonListener);
        clearSearchButton.addActionListener(clearSearchButtonListener);
        saveButton.addActionListener(saveButtonListener);
        deleteButton.addActionListener(deleteButtonListener);
        clearButton.addActionListener(clearButtonListener);
    }


    public void clearSearchResults() {
        searchResults.removeAllElements();
    }

    public void clearClientInformation() {
        clientIDTextField.setText("");
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        addressTextField.setText("");
        postalCodeTextField.setText("");
        phoneNumberTextField.setText("");

    }

    public String getSelectedSearchResult() {
        return searchResultList.getSelectedValue();
    }

    public String getSearchBoxTextFieldText() {
        return searchBoxTextField.getText();
    }

    public DefaultListModel<String> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(ArrayList<Client> searchResults) {
        for (Client result : searchResults) {
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

    public static void main(String[] args) {
        new ClientManagementSystemView().setVisible(true);
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

    public void updateTextFields (String[] texts){
        clientIDTextField.setText(texts[0].trim());
        firstNameTextField.setText(texts[1].trim());
        lastNameTextField.setText(texts[2].trim());
        addressTextField.setText(texts[3].trim());
        postalCodeTextField.setText(texts[4].trim());
        phoneNumberTextField.setText(texts[5].trim());
        clientTypeSpinner.setValue(texts[6].trim());
    }
}

