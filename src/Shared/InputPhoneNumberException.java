package Shared;// ENSF 519-2 Java Project I
// Server.Model.Person Management System
// Oscar Chen & Savith Jayasekera
// November 5 2018

public class InputPhoneNumberException extends Exception {

    public InputPhoneNumberException() {
        super("Invalid phone number: phone number must be in the format of '123-456-7890'.");
    }
}
