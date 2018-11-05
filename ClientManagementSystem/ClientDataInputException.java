// ENSF 519-2 Java Project I
// Client Management System
// Oscar Chen & Savith Jayasekera
// November 5 2018

public class ClientDataInputException extends Exception {

    public ClientDataInputException () {
        super("Invalid client data entered: first name and last name should be no more than 20 characters long, " +
                "address should be not more than 50 characters long.");
    }

}
