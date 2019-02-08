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
     * @param data
     * @return
     */
    public DataPack sendToServer(DataPack data){
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

    public ArrayList<Person> sendSearchResultDataPack(String phrase, String column){
        DataPack serverResponse =  sendToServer(new DataPack(4, phrase+","+column));
        if (serverResponse != null){
            return serverResponse.getData();
        }
        return null;
    }



}
