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

        try{
            Socket socket = new Socket(server, port);

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Called upon with a DataPack, sends it to server, listens for a DataPack reply from server, return DataPack.
     */
    private DataPack sendToServer(DataPack data){
        DataPack responseFromServer = null;
        try {
            out.writeObject(data);
            responseFromServer = (DataPack) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return responseFromServer;
    }


    public boolean wasClientAdditionSuccessful(Person personToAdd){
        DataPack serverResponse = sendToServer(new DataPack(1,personToAdd));
        return processServerResponse(serverResponse);
    }

    public boolean wasClientModificationSuccessful(Person personToModify) {
        DataPack serverResponse = sendToServer(new DataPack(2, personToModify));
        return processServerResponse(serverResponse);
    }

    public boolean wasClientDeletionSuccessful(String clientID){
        DataPack serverResponse = sendToServer(new DataPack(2,clientID));
        return processServerResponse(serverResponse);
    }

    private boolean processServerResponse(DataPack serverResponse){
        if (serverResponse != null){    //if server message is "success", return true
            return serverResponse.getMsg().equalsIgnoreCase("success");
        }
        return false;
    }

    /**
     * Sends the appropriate datapack to the server when the client presses the search button. Returns the
     * arraylist of person objects returned from the server. If an error occurred, returns null
     */
    public ArrayList<Person> sendSearchResultDataPack(String phrase, String column){
        DataPack serverResponse =  sendToServer(new DataPack(4, phrase+","+column));    //format of the message
        if (serverResponse != null){
            return serverResponse.getData();
        }
        return null;
    }

}
