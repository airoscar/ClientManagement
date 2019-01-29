// ENSF 519-2 Java Project I
// Person Management System
// Oscar Chen & Savith Jayasekera
// November 5 2018

public class InputClientTypeException extends Exception {
    public InputClientTypeException() {
        super("Invalid client type: client type must be either 'C' or 'R'.");
    }
}
