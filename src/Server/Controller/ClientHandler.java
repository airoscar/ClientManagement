package Server.Controller;

import Server.Model.DatabaseController;
import Server.ServerView;
import Shared.DataPack;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private DatabaseController databaseController;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientHandler(Socket socket, DatabaseController databaseController){
        this.databaseController = databaseController;
        this.socket = socket;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            ServerView.print("ClientHandler started..");
        } catch (IOException e){
            ServerView.print("ClientHandler failed to set up IO streams: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            ServerView.print("ClientHandler listening..");
            DataPack dataFromClient = (DataPack)in.readObject();
        } catch (IOException e) {
            ServerView.print("ClientHandler encountered an error receiving data from client: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            ServerView.print("ClientHandler encountered an error reading data received from client: " + e.getMessage());
        }

        DataPack dataToClient = processDataFromClient();



    }

    private DataPack processDataFromClient() {




        return null;
    }
}
