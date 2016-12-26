package mongoConnection;

public class ConnectionHandler {
    public static Connection connection;

    public static void generateConnection()
    {
        connection =  new Connection();
    }
}
