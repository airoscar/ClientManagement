package Server.Controller;

import Server.Model.DatabaseController;
import Server.ServerView;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.net.InetAddress;

public class ServerApplication {

    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private int port;
    private DatabaseController databaseController;

    public ServerApplication(int port) {
        this.port = port;
    }

    public void start() {

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

    public static void main(String[] args) {

        ServerApplication a = new ServerApplication(8989);
        a.start();


    }


}
