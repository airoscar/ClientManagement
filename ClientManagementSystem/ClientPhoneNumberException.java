public class ClientPhoneNumberException extends Exception {

    public ClientPhoneNumberException () {
        super("Invalid phone number: phone number must be in the format of '123-456-7890'.");
    }
}
