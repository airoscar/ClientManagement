import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * This class handles view of log in screen.
 */
public class ClientManagementSystemDBLogin extends JFrame {

    private JTextField usernameField = new JTextField("root",15);
    private JPasswordField passwordField = new JPasswordField(15);
    private JTextField dbNameField = new JTextField("clientsDB",15);
    private JButton okButton = new JButton("OK");
    private JButton cancelButton = new JButton("Cancel");

    public ClientManagementSystemDBLogin(){
        setTitle("Login to Database");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridLayout(4,1,0,0));

        add(usernamePanel());
        add(passwordPanel());
        add(dbNamePanel());
        add(addButtons());

        //opens the window centered at the middle of the screen
        setLocationRelativeTo(null);

        setResizable(false);

    }

    /**
     * Sets up username panel.
     * @return
     */
    private JPanel usernamePanel () {
        JPanel username = new JPanel();
        username.setLayout(new GridLayout(1,2,-100,0));

        JPanel usernameLabelPanel = new JPanel();
        usernameLabelPanel.setBorder(new EmptyBorder(30,0,0,10));
        usernameLabelPanel.add(new JLabel("Enter your Username:"));

        JPanel usernamePanel = new JPanel();
        usernamePanel.setBorder(new EmptyBorder(30,0,0,10));
        usernamePanel.add(usernameField);

        username.add(usernameLabelPanel);
        username.add(usernamePanel);

        return username;
    }

    /**
     * Sets up password panel.
     * @return
     */
    private JPanel passwordPanel () {
        JPanel password = new JPanel();
        password.setLayout(new GridLayout(1,2,-100,0));


        JPanel passwordLabelPanel = new JPanel();
        passwordLabelPanel.setBorder(new EmptyBorder(30,0,0,10));
        passwordLabelPanel.add(new JLabel("Enter your Password:"));

        JPanel passwordPanel = new JPanel();
        passwordPanel.setBorder(new EmptyBorder(30,0,0,10));
        passwordPanel.add(passwordField);

        password.add(passwordLabelPanel);
        password.add(passwordPanel);

        return password;
    }

    /**
     * Sets up databse name panel.
     * @return
     */
    private JPanel dbNamePanel(){
        JPanel dbName = new JPanel();
        dbName.setLayout(new GridLayout(1,2, -100,0));

        JPanel dbNameLabelPanel = new JPanel();
        dbNameLabelPanel.setBorder(new EmptyBorder(30,0,0,10));
        dbNameLabelPanel.add(new JLabel("Enter the Database name:"));

        JPanel dbPanel = new JPanel();
        dbPanel.setBorder(new EmptyBorder(30,0,0,10));
        dbPanel.add(dbNameField);

        dbName.add(dbNameLabelPanel);
        dbName.add(dbPanel);

        return dbName;
    }

    /**
     * Add button to view.
     * @return
     */
    private JPanel addButtons () {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new EmptyBorder(20,100,20,100));
        buttonPanel.setLayout(new GridLayout(1,2, 40,0));

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    /**
     * Set up action listener.
     * @param okButtonListener
     * @param cancelButtonListener
     */
    public void setActionListeners (ActionListener okButtonListener, ActionListener cancelButtonListener){
        okButton.addActionListener(okButtonListener);
        cancelButton.addActionListener(cancelButtonListener);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String (passwordField.getPassword());
    }

    public String getDBName (){
        return dbNameField.getText();
    }

}
