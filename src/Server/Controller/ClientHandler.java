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
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is created for each client socket that connects to the server threadpool. <br>
 * Handles input and output operations and communicate with the client.
 */
public class ClientHandler implements Runnable {
    private DatabaseController databaseController;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientHandler(Socket socket, DatabaseController databaseController) {
        this.databaseController = databaseController;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            ServerView.print("ClientHandler started..");
        } catch (IOException e) {
            ServerView.print("ClientHandler failed to set up IO streams: " + e.getMessage());
        }
    }

    /**
     * Listens for client, forward data to other methods to process, return reply data to client.
     */
    @Override
    public void run() {

        try {
            while (true) {
                ServerView.print("ClientHandler listening for client..");
                DataPack dataFromClient = (DataPack) in.readObject();
                ServerView.print("Received from client: " + dataFromClient);

                DataPack dataToClient = processDataFromClient(dataFromClient);
                out.writeObject(dataToClient);
                ServerView.print("Sent to client: " + dataToClient);
            }

        } catch (IOException e) {
            ServerView.print("ClientHandler encountered an error receiving data from client: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            ServerView.print("ClientHandler encountered an error reading data received from client: " + e.getMessage());
        }
    }

    /**
     * Process a DataPack from client, reads the actionId, and return a DataPack.
     *
     * @param data
     * @return
     */
    private DataPack processDataFromClient(DataPack data) {
        DataPack response = new DataPack();
        String msg = "";

        switch (data.getActionId()) {

            case 1: //add client

                if (data.getNumerOfPersons() > 0) {

                    Iterator<Person> it = data.getData().iterator();

                    while (it.hasNext()) {
                        Person person;
                        try {
                            person = new DataVerifier().verifyInput(it.next());
                            databaseController.addClient(person);

                        } catch (Exception e) {
                            msg = msg + "\n" + e.getMessage();
                        }
                    }
                }

                break;

            case 2: //edit client

                if (data.getNumerOfPersons() > 0) {
                    Iterator<Person> it = data.getData().iterator();

                    while (it.hasNext()) {
                        Person person;
                        try {
                            person = new DataVerifier().verifyInput(it.next());
                            databaseController.updateClient(person);
                        } catch (Exception e) {
                            msg = msg + "\n" + e.getMessage();
                        }
                    }
                }

                break;


            case 3: // delete client

                if (data.getNumerOfPersons() > 0) {
                    Iterator<Person> it = data.getData().iterator();

                    while (it.hasNext()) {
                        Person person = it.next();
                        try {
                            databaseController.deleteClient(person.getDataID());
                        } catch (Exception e) {
                            msg = msg + "\n" + e.getMessage();
                        }
                    }
                }

                break;

            case 4: //search for client

                String search = data.getMsg(); //the search msg should be in the format of phrase,column
                String[] searchCriteria = search.split(",");
                String searchPhrase = searchCriteria[0].trim();
                String searchCol = searchCriteria[1].trim();

                ServerView.print("searchPhrase=" + searchPhrase + " searchCol=" + searchCol);

                try {

                    ArrayList<Person> results = databaseController.searchColumn(searchPhrase, searchCol);
                    response.setData(results);
                } catch (Exception e) {
                    msg = e.getMessage();
                }

                break;

            default:
                msg = "Unknown command.";
                break;
        }

        if (msg.equals("")) {    //if no error messages appended to msg
            msg = "success";
        }

        response.setMsg(msg);

        return response;
    }
}
