package Server.Controller;

import Server.SMS;
import Shared.DataPack;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private SystemController sysController;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientHandler(Socket socket, SystemController sysController){
        this.sysController = sysController;
        this.socket = socket;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            SMS.print("ClientHandler started..");
        } catch (IOException e){
            SMS.print("ClientHandler failed to set up IO streams: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            SMS.print("ClientHandler listening..");
            DataPack dataFromClient = (DataPack)in.readObject();
        } catch (IOException e) {
            SMS.print("ClientHandler encountered an error receiving data from client: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            SMS.print("ClientHandler encountered an error reading data received from client: " + e.getMessage());
        }

        DataPack dataToClient = sysController.processDataFromClient();

    }
}
