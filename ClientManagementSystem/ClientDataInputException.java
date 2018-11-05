public class ClientDataInputException extends Exception {

    public ClientDataInputException () {
        super("Invalid client data entered: first name and last name should be no more than 20 characters long, " +
                "address should be not more than 50 characters long.");
    }

}
