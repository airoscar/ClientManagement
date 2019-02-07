package Client.ClientApplication;



public class ClientApplication {
    public static void main(String[] args) {
            new ClientViewController();
            new ServerConnector("localhost", 8989);
    }
}
