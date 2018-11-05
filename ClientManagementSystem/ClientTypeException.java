public class ClientTypeException extends Exception {
    public ClientTypeException() {
        super("Invalid client type: client type must be either 'C' or 'R'.");
    }
}
