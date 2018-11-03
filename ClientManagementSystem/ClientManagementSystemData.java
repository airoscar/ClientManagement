import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class ClientManagementSystemData {
    private Connection dbConnection;
    private String dbName;
    private String username;
    private String password;
    private String dataTableName = "client_table";
    Map <String,String> dataDictionary = new HashMap<String,String>();

    /**
     * Setter method for username, password, and database name.
     *
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
     * Initializes the database.
     *
     * @throws SQLException
     */
    public void initializeDatabase() throws SQLException {

        dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", username, password);

        if (!checkDBExistence()) {  //create new database if it doesn't already exist

            dbConnection.createStatement().execute("CREATE DATABASE " + dbName);

        }

        //select database for use
        dbConnection.createStatement().execute("USE " + dbName);


        if (!checkDataTable(dbName)) { //create new table in database if existing one not found
            createNewTable();
        }

        addMissingColumns();    //checks the columns in the table, add any missing columns;


    }

    private void createNewTable() throws SQLException {

        String newTable = "CREATE TABLE " + dataTableName
                + " (id INTEGER NOT NULL AUTO_INCREMENT,"
                //+ "firstname VARCHAR(20),"
                //+ "lastname VARCHAR(20),"
                //+ "address VARCHAR(50),"
                //+ "postalCod CHAR(7),"
                //+ "phoneNumber CHAR(12),"
                //+ "clientType CHAR(1),"
                + "primary key (id))";
        dbConnection.createStatement().execute(newTable);
    }

    /**
     * Check to see if any of the data tables in the database match the name of our table
     *
     * @return
     * @throws SQLException
     */
    private boolean checkDataTable(String databaseChecked) throws SQLException {

        String query = "SELECT TABLE_NAME FROM information_schema.tables WHERE table_schema = '" + databaseChecked + "'";

        ResultSet resultSet = dbConnection.createStatement().executeQuery(query);

        int count = resultSet.getMetaData().getColumnCount();

        //loop through rows in the set
        while (resultSet.next()) {
            for (int i = 1; i <= count; i++) {  //loop through column (for data table names)

                if (resultSet.getString(i).equalsIgnoreCase(dataTableName)) {//table found
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Add missing columns to a data table.
     * @throws SQLException
     */
    private void addMissingColumns () throws SQLException {

        ArrayList<String> missingColumns = missingColumnsInTable(dataTableName);

        if (missingColumns == null) {   //if no missing columns found
            return;
        }

        Map <String,String> dataDictionary = new HashMap<String,String>();
        dataDictionary.put("firstname", "VARCHAR(20)");
        dataDictionary.put("lastname", "VARCHAR(20)");
        dataDictionary.put("address", "VARCHAR(50)");
        dataDictionary.put("postalCod", "CHAR(7)");
        dataDictionary.put("phoneNumber", "CHAR(12)");
        dataDictionary.put("clientType", "CHAR(1)");

        for (String name: missingColumns) { //add missing columns
            dbConnection.createStatement().execute("ALTER TABLE " + dataTableName
                    + " ADD " + name + " " + dataDictionary.get(name));
        }
    }

    /**
     * This method looks at a given data table, and look for column names that match our required names set. </br>
     * Returns a list of missing column names. </br>
     * Returns null if every columns has been found.
     * @param tableName
     * @return
     * @throws SQLException
     */
    private ArrayList<String> missingColumnsInTable(String tableName) throws SQLException {

        String[] columnNames = {"id", "firstname", "lastname", "address", "postalCod", "phoneNumber", "clientType"};
        boolean[] columnMatch = {false, false, false, false, false, false, false};
        ArrayList<String> missingColumns = new ArrayList();

        String query = "SELECT * FROM " + tableName;
        ResultSet resultSet = dbConnection.createStatement().executeQuery(query);

        int count = resultSet.getMetaData().getColumnCount();

        for (int i = 1; i <= count; i ++){  //loop through the column names found in the table

            String foundColumn = resultSet.getMetaData().getColumnName(i);  //column name found at index i

            for (int j = 0; j < columnNames.length; j++) {  //step through the columnNames[] and look for match

                if (foundColumn.equalsIgnoreCase(columnNames[j])) { //match found, set corresponding flag to true
                    columnMatch[j] = true;
                    break;
                }
            }
        }

        //loop through the columnMatch[], where there is a 'false', the corresponding item from columnNames[] is added.
        for (int i = 0; i < columnMatch.length; i++){
            if (columnMatch[i] == false){
                missingColumns.add(columnNames[i]);
            }
        }

        return missingColumns;
    }

    /**
     * Private utility method used for checking database existence.
     *
     * @return True if database exist.
     * @throws SQLException
     */
    private boolean checkDBExistence() throws SQLException {

        boolean dbExist = false;
        ResultSet resultSet = null;

        resultSet = dbConnection.getMetaData().getCatalogs();

        while (resultSet.next()) {
            // Get the database name, which is at position 1
            String result1 = resultSet.getString(1);
            if (result1.equalsIgnoreCase(dbName)) {
                dbExist = true;
            }
        }

        resultSet.close();

        return dbExist;
    }

    public static void main(String[] args) {
        ClientManagementSystemData myDB = new ClientManagementSystemData();
        myDB.setUpDatabase("root", "greencreatures", "clientsDB");
        try {
            myDB.initializeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
