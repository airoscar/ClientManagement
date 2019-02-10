package Client.ClientApplication;

import Shared.DataPack;
import Shared.Person;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConnector {

    private ObjectOutputStream out;
    private ObjectInputStream in;


    public ServerConnector(String server, int port) {

        try {
            Socket socket = new Socket(server, port);

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Called upon with a DataPack, sends it to server, listens for a DataPack reply from server, return DataPack.
     */
    private DataPack sendToServer(DataPack data) {
        DataPack responseFromServer = null;
        try {
            out.writeObject(data);
            System.out.println("Sent to server: " + data);

            responseFromServer = (DataPack) in.readObject();
            System.out.println("Received from server: " + responseFromServer);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return responseFromServer;
    }

    /**
     * Sends the appropriate datapack to the server when the client presses the search button. Returns the
     * arraylist of person objects returned from the server. If an error occurred, returns null
     */
    public ArrayList<Person> sendSearchDataPack(String phrase, String column) {
        DataPack data = new DataPack(4, phrase + "," + column);
        DataPack serverResponse = sendToServer(data);    //format of the message
        System.out.println(data);

        if (serverResponse != null) {
            return serverResponse.getData();
        }
        return null;
    }

    /**
     * Called upon to send a list of clients to the server to be added to the database.
     * @param listOfPeople
     * @return
     */
    public String sendMultipleClientsToAddToDatabase(ArrayList<Person> listOfPeople) {
        DataPack serverResponse = sendToServer(new DataPack(1, listOfPeople));
        return serverResponse.getMsg();
    }

    /**
     * Called upon to send a single client to the server to be added to the database.
     * @param personToAdd
     * @return
     */
    public boolean wasClientAdditionSuccessful(Person personToAdd) {
        DataPack serverResponse = sendToServer(new DataPack(1, personToAdd));
        return processServerResponse(serverResponse);
    }

    /**
     * Called upon to send a single client to the server to be edited in the database.
     * @param personToModify
     * @return
     */
    public boolean wasClientModificationSuccessful(Person personToModify) {
        DataPack serverResponse = sendToServer(new DataPack(2, personToModify));
        return processServerResponse(serverResponse);
    }

    /**
     * Called upon to send a single client to the server to be deleted from the database.
     * @param personToDelete
     * @return
     */
    public boolean wasClientDeletionSuccessful(Person personToDelete) {
        DataPack serverResponse = sendToServer(new DataPack(3, personToDelete));
        return processServerResponse(serverResponse);
    }

    /**
     * Called upon to process response data from server.
     * @param serverResponse
     * @return
     */
    private boolean processServerResponse(DataPack serverResponse) {
        if (serverResponse != null) {
            return serverResponse.getMsg().equalsIgnoreCase("success");     //if server message is "success", return true
        }
        return false;
    }
}
