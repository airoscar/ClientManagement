public class ClientPostalException extends Exception {

    public ClientPostalException () {
        super ("Invalid postal code: postal code must be in the format of 'A1A 1A1'.");
    }
}
