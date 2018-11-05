// ENSF 519-2 Java Project I
// Client Management System
// Oscar Chen & Savith Jayasekera
// November 5 2018

public class ClientPhoneNumberException extends Exception {

    public ClientPhoneNumberException () {
        super("Invalid phone number: phone number must be in the format of '123-456-7890'.");
    }
}
