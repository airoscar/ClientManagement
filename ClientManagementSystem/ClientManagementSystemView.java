import javax.swing.*;
import java.awt.*;

/**
 * Creates the main window of the Client Management System user interface
 */
public class ClientManagementSystemView extends JFrame {

    //the search results should be saved in this DefaultListModel
    DefaultListModel searchResutls;

    JRadioButton clientIDSearchButton = new JRadioButton("Client ID");
    JRadioButton lastNameSearchButton = new JRadioButton("Last Name");
    JRadioButton clientTypeSearchButton = new JRadioButton("Client Type");

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
//        add(BorderLayout.LINE_END, eastPanel);
//        add(BorderLayout.CENTER, new JPanel());











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

    /*private JSplitPane westPanel(){
        JPanel searchSection = new JPanel();
        searchSection.add(new JLabel("Search Clients", SwingConstants.CENTER));


        JPanel searchResultsSection = new JPanel();
        //JList searchResults = new JList(searchResutls);
        searchResultsSection.add(new JLabel("Search Results:"));
        //searchResultsSection.add(searchResults);

        JSplitPane westPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, searchSection, searchResultsSection);
        westPanel.setResizeWeight(.5);
        westPanel.setEnabled(false);

        return westPanel;
    }*/

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



        searchSelection.add(searchSelectionCenterPanel, BorderLayout.CENTER);





        /*JLabel searchSelectionLabel = new JLabel("Search Clients");
        searchSelectionLabel.setAlignmentX(CENTER_ALIGNMENT);

        JLabel searchSelectionExplanation = new JLabel("Select type of search to be performed:");
        searchSelectionExplanation.setFont(new Font("SANS_SERIF", Font.PLAIN, 12));
        searchSelectionExplanation.setAlignmentX(CENTER_ALIGNMENT);*/








        /*searchSelection.add(searchSelectionLabel);
        searchSelection.add(Box.createRigidArea(new Dimension(0,20)));
        searchSelection.add(searchSelectionExplanation);
        searchSelection.add(Box.createRigidArea(new Dimension(0,20)));*/

        /*searchSelection.add(clientIDSearchButton);
        searchSelection.add(lastNameSearchButton);
        searchSelection.add(clientTypeSearchButton);
*/



        //Creates the Search Results Panel
        JPanel searchResultsPanel = new JPanel();
        searchResultsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        searchResultsPanel.setLayout(new BoxLayout(searchResultsPanel, BoxLayout.Y_AXIS));
        //JList searchResults = new JList(searchResutls);
        JLabel searchResultsPanelLabel = new JLabel("Search Results");
        searchResultsPanelLabel.setAlignmentX(CENTER_ALIGNMENT);
        searchResultsPanel.add(searchResultsPanelLabel);
        searchResultsPanel.add(Box.createRigidArea(new Dimension(0,10)));
        //searchResultsSection.add(searchResults);

        searchSection.add(searchSelection);
        searchSection.add(searchResultsPanel);

        //Creates the Client Information section
        JPanel clientInformationSection = new JPanel();
        clientInformationSection.setBorder(BorderFactory.createLineBorder(Color.black));
        clientInformationSection.setLayout(new BoxLayout(clientInformationSection, BoxLayout.Y_AXIS));
        JLabel clientInformationSectionLabel = new JLabel("Client Information");
        clientInformationSectionLabel.setAlignmentX(CENTER_ALIGNMENT);
        clientInformationSection.add(clientInformationSectionLabel);

        centerPanel.add(searchSection);
        centerPanel.add(clientInformationSection);

        return centerPanel;


    }



    public static void main(String[] args) {
        new ClientManagementSystemView().setVisible(true);
    }
	
	
}

