package Server.Controller;

import Server.SMS;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;

public class ServerApplication {

    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private int port;
    private SystemController sysController = new SystemController();

    public ServerApplication (int port) {
        this.port = port;
    }

    public void start(){

        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                executorService.execute(new ClientHandler(serverSocket.accept(), sysController));
                SMS.print("Client connected..");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {


    }


}
