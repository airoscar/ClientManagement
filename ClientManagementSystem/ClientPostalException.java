// ENSF 519-2 Java Project I
// Client Management System
// Oscar Chen & Savith Jayasekera
// November 5 2018

public class ClientPostalException extends Exception {

    public ClientPostalException () {
        super ("Invalid postal code: postal code must be in the format of 'A1A 1A1'.");
    }
}
