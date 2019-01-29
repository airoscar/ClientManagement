// ENSF 519-2 Java Project I
// Person Management System
// Oscar Chen & Savith Jayasekera
// November 5 2018

public class InputPostalException extends Exception {

    public InputPostalException() {
        super ("Invalid postal code: postal code must be in the format of 'A1A 1A1'.");
    }
}
