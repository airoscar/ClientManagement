package Server.Controller;

import Server.Model.DatabaseController;
import Server.ServerLoginView;
import Server.ServerView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.net.InetAddress;

public class ServerApplication {

    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private int port;
    private DatabaseController databaseController;
    private ServerLoginView loginWindow;

    public ServerApplication(int port) {
        databaseController = new DatabaseController();
        this.port = port;
        loginWindow = new ServerLoginView();
        setUp();
        start();

    }

    private void start() {

        try {
            serverSocket = new ServerSocket(port);
            ServerView serverView = new ServerView();
            ServerView.print("Server started..");

            InetAddress inetAddress = InetAddress.getLocalHost();
            ServerView.print("Host Name: " + inetAddress.getHostName());
            ServerView.print("Server IP Address on local network:" + inetAddress.getHostAddress());

            while (true) {
                executorService.execute(new ClientHandler(serverSocket.accept(), databaseController));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set up database log in. Initialize database. start View.
     */
    private void setUp() {

        ActionListener okButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                databaseController.setUpDatabase(loginWindow.getUsername(), loginWindow.getPassword(), loginWindow.getDBName());
                loginWindow.setVisible(false);
                loginWindow.dispose();

            }
        };

        ActionListener canButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginWindow.setVisible(false);
                loginWindow.dispose();
            }
        };

        loginWindow.setActionListeners(okButtonListener, canButtonListener);
        loginWindow.setVisible(true);
    }

    public static void main(String[] args) {

        ServerApplication a = new ServerApplication(8989);


    }


}
