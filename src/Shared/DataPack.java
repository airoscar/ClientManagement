package Shared;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Data Model used to communicate between client and server via socket
 */
public class DataPack implements Serializable {
    private static final long serialversionUID = 666L;
    private int actionId;   // 1 - add; 2 - update/edit; 3 - delete; 4 - search
    private String msg;     //messages to be passed between client and server.
    private ArrayList<Person> data = new ArrayList<>();

    public DataPack() {
    }

    public DataPack(String msg) {   //construct with only a message
        this.msg = msg;
    }

    public DataPack(int actionId) { //construct with only an actionId
        this.actionId = actionId;
    }

    public DataPack(int actionId, String msg) { //construct with an actionId, and a message
        this.actionId = actionId;
        this.msg = msg;
    }

    public DataPack(int actionId, Person p){  //construct with an actionId, and a single person object
        data.add(p);
        this.actionId = actionId;
    }

    public DataPack(ArrayList<Person> data) { //construct with an ArrayList of persons
        this.data = data;
    }

    public DataPack(int actionId, ArrayList<Person> listOfPersons){
        this.actionId = actionId;
        data = listOfPersons;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(ArrayList<Person> data) {
        this.data = data;
    }

    public int getActionId() {
        return actionId;
    }

    public String getMsg() {
        return msg;
    }

    public ArrayList<Person> getData() {
        return data;
    }

    public int getNumerOfPersons(){
        if (data ==null){
            return 0;
        } else {
            return data.size();
        }
    }

    @Override
    public String toString() {
        return "DataPack: actionId=" + this.actionId + " msg=" + this.msg + " array size=" + data.size();
    }
}
