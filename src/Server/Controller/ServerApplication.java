package Server.Controller;

import Server.SMS;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.net.InetAddress;

public class ServerApplication {

    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private int port;
    private SystemController sysController = new SystemController();

    public ServerApplication(int port) {
        this.port = port;
    }

    public void start() {

        try {
            serverSocket = new ServerSocket(port);
            SMS.print("Server started..");

            InetAddress inetAddress = InetAddress.getLocalHost();
            SMS.print("Host Name: " + inetAddress.getHostName());
            SMS.print("Server IP Address on local network:" + inetAddress.getHostAddress());

            while (true) {
                executorService.execute(new ClientHandler(serverSocket.accept(), sysController));

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
