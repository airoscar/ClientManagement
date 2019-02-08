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
            System.out.println("Sent to server: " + data);
            out.writeObject(data);
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
    public ArrayList<Person> sendSearchResultDataPack(String phrase, String column) {
        DataPack data = new DataPack(4, phrase + "," + column);
        DataPack serverResponse = sendToServer(data);    //format of the message
        System.out.println(data);

        if (serverResponse != null) {
            return serverResponse.getData();
        }
        return null;
    }

    public String sendMultipleClientsToAddToDatabase(ArrayList<Person> listOfPeople) {
        DataPack serverResponse = sendToServer(new DataPack(1, listOfPeople));
        return serverResponse.getMsg();
    }

    public boolean wasClientAdditionSuccessful(Person personToAdd) {
        DataPack serverResponse = sendToServer(new DataPack(1, personToAdd));
        return processServerResponse(serverResponse);
    }

    public boolean wasClientModificationSuccessful(Person personToModify) {
        DataPack serverResponse = sendToServer(new DataPack(2, personToModify));
        return processServerResponse(serverResponse);
    }

    public boolean wasClientDeletionSuccessful(Person personToDelete) {
        DataPack serverResponse = sendToServer(new DataPack(3, personToDelete));
        return processServerResponse(serverResponse);
    }

    private boolean processServerResponse(DataPack serverResponse) {
        if (serverResponse != null) {
            return serverResponse.getMsg().equalsIgnoreCase("success");     //if server message is "success", return true
        }
        return false;
    }
}
