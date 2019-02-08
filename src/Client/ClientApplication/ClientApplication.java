package Client.ClientApplication;

import javax.swing.*;

public class ClientApplication {
    public static void main(String[] args) {
        try {
            String serverAddress = JOptionPane.showInputDialog(null,
                    "Enter the server address: ", "localhost");
            int port = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Enter the port number: ", "8989"));
            new ClientController(serverAddress, port);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Connecting to " +
                    "specified server. Game will quit");
        }
    }
}
