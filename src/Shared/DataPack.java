package Shared;

import java.util.ArrayList;

/**
 * Data Model used to communicate between client and server via socket
 */
public class DataPack {
    private int actionId;   // 1 - add; 2 - update/edit; 3 - delete; 4 - search
    private String msg;
    private ArrayList<Person> data;

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
}
