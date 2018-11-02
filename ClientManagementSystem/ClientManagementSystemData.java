import java.sql.*;

public class ClientManagementSystemData {
    Connection dbConnection;
    String dbName;
    String username;
    String password;

    /**
     * Set usernmae, password, and database name.
     * @param username
     * @param password
     * @param dbName
     */
    public void setUpDatabase(String username, String password, String dbName) {
        this.username = username;
        this.password = password;
        this.dbName = dbName;
    }

    /**
     * Initializes the database. Checks for database existence before creating a new one.
     * @throws SQLException
     */
    public void initializeDatabase() throws SQLException {

        dbConnection = DriverManager.getConnection("jdbc:mysql://localhost/", username, password);

        if (!checkDBExistence()) {
            dbConnection.createStatement().execute("CREATE DATABASE " + dbName);
        }

        dbConnection.createStatement().execute("USE " + dbName);
    }

    /**
     * Private utility method used for checking database existence.
     * @return True if database exist.
     * @throws SQLException
     */
    private boolean checkDBExistence() throws SQLException {

        boolean dbExist = false;
        ResultSet resultSet = null;

        resultSet = dbConnection.getMetaData().getCatalogs();

        while (resultSet.next()) {
            // Get the database name, which is at position 1
            String dbName = resultSet.getString(1);
            if (dbName.equalsIgnoreCase(dbName)) {
                dbExist = true;
            }
        }

        resultSet.close();

        return dbExist;
    }

}
