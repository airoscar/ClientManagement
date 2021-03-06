package Shared;// ENSF 519-2 Java Project I
// Server.Model.Person Management System
// Oscar Chen & Savith Jayasekera
// November 5 2018

public class InputNameAddressException extends Exception {

    public InputNameAddressException() {
        super("Invalid client data entered: first name and last name should be no more than 20 characters long, " +
                "address should be not more than 50 characters long.");
    }

}
