package Server.Controller;

import Server.SMS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientHandler(Socket socket){
        this.socket = socket;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e){
            SMS.print("ClientHandler failed to set up IO streams: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        //in.readObject();
    }
}
