import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Creates the main window of the Client Management System user interface
 */
public class ClientManagementSystemView extends JFrame {

    //the search results should be saved in this DefaultListModel
    DefaultListModel searchResults = new DefaultListModel();

    JRadioButton clientIDSearchButton = new JRadioButton("Client ID");
    JRadioButton lastNameSearchButton = new JRadioButton("Last Name");
    JRadioButton clientTypeSearchButton = new JRadioButton("Client Type");

    JTextField searchBoxTextField = new JTextField(15);
    JButton searchButton = new JButton("Search");
    JButton clearSearchButton = new JButton("Clear Search");

    JTextField clientIDTextField = new JTextField();
    JTextField firstNameTextField = new JTextField();
    JTextField lastNameTextField = new JTextField();
    JTextField addressTextField = new JTextField();
    JTextField postalCodeTextField = new JTextField();
    JTextField phoneNumberTextField = new JTextField();
    JSpinner clientTypeSpinner = new JSpinner();

    JButton saveButton = new JButton("Save");
    JButton deleteButton = new JButton("Delete");
    JButton clearButton = new JButton("Clear");


    /**
     * Creates the main window of the user interface
     */
    public ClientManagementSystemView(){
        setSize(900,750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //opens the window centered at the middle of the screen
        setLocationRelativeTo(null);

        addPanelsToView();
        setResizable(false);
    }

    private void addPanelsToView(){

        JPanel northPanel = northPanel();
        JPanel centerPanel = centerPanel();


        add(BorderLayout.NORTH, northPanel);
        add(BorderLayout.CENTER, centerPanel);












    }

    private JPanel northPanel(){
        JPanel northPanel = new JPanel();
        northPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        JLabel screenTitle = new JLabel("Client Management Screen", SwingConstants.CENTER);
        screenTitle.setFont(new Font("SANS_SERIF", Font.PLAIN, 30));
        northPanel.add(screenTitle);
        northPanel.setBackground(new Color(148, 148, 184));

        return northPanel;
    }


    private JPanel centerPanel(){

        //This panel is the main panel with two sections split vertically: Search Client section on the left
        //and the Client Information section on the right
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1,2));

        //This panel is the Search Client section. It is split horizontally into: Search Selection on top and
        //Search Results on the bottom
        JPanel searchSection = new JPanel();
        searchSection.setBorder(BorderFactory.createLineBorder(Color.black));
        searchSection.setLayout(new GridLayout(2,1));

        //Creates the Search Selection Panel
        JPanel searchSelection = new JPanel();

        searchSelection.setBorder(BorderFactory.createLineBorder(Color.black));
        searchSelection.setLayout(new BorderLayout(0,50));

        searchSelection.add(new JLabel("Search Clients",SwingConstants.CENTER), BorderLayout.NORTH);

        JPanel searchSelectionCenterPanel = new JPanel();
        searchSelectionCenterPanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        searchSelectionCenterPanel.setLayout(new BorderLayout(0,10));

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
        searchSelectionSouthPanel.setBorder(BorderFactory.createEmptyBorder(0,10,40,10));
        searchSelectionSouthPanel.setLayout(new BorderLayout(20,30));

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
        searchResultsPanel.setLayout(new BorderLayout(0,10));
        JList searchResultList = new JList(searchResults);
        JLabel searchResultsPanelLabel = new JLabel("Search Results", SwingConstants.CENTER);
        searchResultsPanel.add(searchResultsPanelLabel,BorderLayout.NORTH);
        searchResultsPanel.add(searchResultList, BorderLayout.CENTER);
        searchResultsPanel.add(new JScrollPane(searchResultList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));

        searchSection.add(searchSelection);
        searchSection.add(searchResultsPanel);

        //Creates the Client Information section
        JPanel clientInformationSection = new JPanel();
        clientInformationSection.setBorder(BorderFactory.createLineBorder(Color.black));
        clientInformationSection.setLayout(new BorderLayout(0,30));
        JLabel clientInformationSectionLabel = new JLabel("Client Information", SwingConstants.CENTER);
        clientInformationSection.add(clientInformationSectionLabel, BorderLayout.NORTH);


        JPanel clientInformationSectionCenterPanel = new JPanel();
        clientInformationSectionCenterPanel.setBorder(new EmptyBorder(40,110,40,110));
        clientInformationSectionCenterPanel.setLayout(new GridLayout(7,2,-10,50));

        clientInformationSectionCenterPanel.add(new JLabel("Client ID:"));
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
        clientInformationSectionSouthPanel.setLayout(new GridLayout(1,3,30,0));
        clientInformationSectionSouthPanel.setBorder(new EmptyBorder(0,50,40,50));
        clientInformationSectionSouthPanel.add(saveButton);
        clientInformationSectionSouthPanel.add(deleteButton);
        clientInformationSectionSouthPanel.add(clearButton);

        clientInformationSection.add(clientInformationSectionSouthPanel, BorderLayout.SOUTH);


        centerPanel.add(searchSection);
        centerPanel.add(clientInformationSection);

        return centerPanel;


    }



    public static void main(String[] args) {
        new ClientManagementSystemView().setVisible(true);
    }
	
	
}

