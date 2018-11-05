// ENSF 519-2 Java Project I
// Client Management System
// Oscar Chen & Savith Jayasekera
// November 5 2018

public class ClientTypeException extends Exception {
    public ClientTypeException() {
        super("Invalid client type: client type must be either 'C' or 'R'.");
    }
}
