package Server.Controller;

import Server.Model.DataVerifier;
import Server.Model.DatabaseController;
import Server.ServerView;
import Shared.DataPack;
import Shared.Person;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;

public class ClientHandler implements Runnable {
    private DatabaseController databaseController;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientHandler(Socket socket, DatabaseController databaseController) {
        this.databaseController = databaseController;
        this.socket = socket;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            ServerView.print("ClientHandler started..");
        } catch (IOException e) {
            ServerView.print("ClientHandler failed to set up IO streams: " + e.getMessage());
        }
    }

    @Override
    public void run() {

        try {
            ServerView.print("ClientHandler listening..");
            DataPack dataFromClient = (DataPack) in.readObject();
            DataPack dataToClient = processDataFromClient(dataFromClient);
        } catch (IOException e) {
            ServerView.print("ClientHandler encountered an error receiving data from client: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            ServerView.print("ClientHandler encountered an error reading data received from client: " + e.getMessage());
        }
    }

    private DataPack processDataFromClient(DataPack data) {

        switch (data.getActionId()) {
            case 1: //add client
                String msg = "";
                if (data.getNumerOfPersons() > 0) {

                    Iterator<Person> it = data.getData().iterator();

                    while (it.hasNext()) {
                        Person person;
                        try {
                            person = new DataVerifier().verifyInput(it.next());
                            databaseController.addClient(person);

                        } catch (Exception e) {
                            msg = msg + "/n" + e.getMessage();
                        }
                    }


                }


                break;

            case 2: //edit client


                break;


            case 3: // delete client


                break;

            case 4: //search for client


                break;


            default:
                break;
        }
        if (data.getActionId() == 1) {   //add client

        }

//        DataVerifier verify = new DataVerifier();


        return null;
    }
}
