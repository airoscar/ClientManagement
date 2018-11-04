import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ClientManagementSystemData {
    private Connection dbConnection;
    private String dbName;
    private String username;
    private String password;
    private String dataTableName = "client_table";
    Map<String, String> dataDictionary = new HashMap<String, String>();

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

    /**
     * Called upon to add a client entry into database.
     *
     * @param clientData
     * @throws SQLException
     */
    public void addClient(String[] clientData) throws SQLException {

        String[] verifiedData = verifyInput(clientData);
        if (verifiedData == null) { //invalid input
            return;
        }

        String query = " insert into " + dataTableName +
                " (firstname, lastname, address, postalCod, phoneNumber, clientType)" + " values (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
        preparedStmt.setString(1, verifiedData[0]);
        preparedStmt.setString(2, verifiedData[1]);
        preparedStmt.setString(3, verifiedData[2]);
        preparedStmt.setString(4, verifiedData[3]);
        preparedStmt.setString(5, verifiedData[4]);
        preparedStmt.setString(6, verifiedData[5]);

        preparedStmt.execute();
    }

    /**
     * Verify the input data, make neccessary changes to format if needed to maintain format consistency. </br>
     * Returns null if the input data does not meet requirement. </br>
     * Returns a re-formatted array of String type ready that meet format requirements.
     *
     * @param clientData
     * @return
     */
    private String[] verifyInput(String[] clientData) {
        String firstName = clientData[0];
        String lastName = clientData[1];
        String address = clientData[2];
        String postalCode = clientData[3];
        String phoneNumber = clientData[4];
        String clientType = clientData[5];

        if (firstName.length() > 20) {
            return null;
        }
        if (lastName.length() > 20) {
            return null;
        }
        if (address.length() > 50) {
            return null;
        }
        if (fixPostalFormat(postalCode) == null) {
            return null;
        } else {
            postalCode = fixPostalFormat(postalCode);
        }
        if (fixPhoneNumber(phoneNumber) == null) {
            return null;
        } else {
            phoneNumber = fixPhoneNumber(phoneNumber);
        }
        if (clientType.equalsIgnoreCase("C") || clientType.equalsIgnoreCase("R")) {
            clientType = clientType.toUpperCase();
        } else {
            return null;
        }

        return new String[]{firstName, lastName, address, postalCode, phoneNumber, clientType};
    }

    /**
     * Checks if the postal code is in proper format. </br>
     * Returns null if the input is not recognized as a postal format. </br>
     * If the input is postal format, but does not have a space in the middle, insert a space in the middle. </br>
     * Return the properly formatted postal code.
     *
     * @param postal
     * @return
     */
    private String fixPostalFormat(String postal) {
        String regex = "^[A-Z][0-9][A-Z]\\s?[0-9][A-Z][0-9]$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(postal);

        if (!matcher.matches()) {   //not a postal format, return null
            return null;
        }

        if (postal.length() == 6) {  //if no space in the middle, add space
            postal = postal.substring(0, 3) + " " + postal.substring(3, 6);
        }

        return postal;  //return verified postal code
    }

    /**
     * Checks if the phone number is in proper format. </br>
     * Returns null if the number is not formatted correctly. </br>
     * If dashes are missing, add dashes in appropriated locations.
     *
     * @param number
     * @return
     */
    private String fixPhoneNumber(String number) {
        String regex = "^[0-9][0-9][0-9]-?[0-9][0-9][0-9]-?[0-9][0-9][0-9][0-9]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);

        if (!matcher.matches()) {   //not a phone number format, return null
            return null;
        }

        if (number.length() == 10) {  //if number is missing two dashes
            number = number.substring(0, 3) + "-" + number.substring(3, 6) + "-" + number.substring(6, 10);
        } else if (number.length() == 11 && number.charAt(3) != '-') { //number missing one dash at index 3
            number = number.substring(0, 3) + "-" + number.substring(3, 11);
        } else if (number.length() == 11 && number.charAt(7) != '-') { //number missing one dash at index 7
            number = number.substring(0, 7) + "-" + number.substring(7, 11);
        }

        return number;
    }

    /**
     * Utility method used to create a new data table.
     *
     * @throws SQLException
     */
    private void createNewTable() throws SQLException {

        String newTable = "CREATE TABLE " + dataTableName
                + " (id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,"
                + "firstname VARCHAR(20),"
                + "lastname VARCHAR(20),"
                + "address VARCHAR(50),"
                + "postalCod CHAR(7),"
                + "phoneNumber CHAR(12),"
                + "clientType CHAR(1),"
                + "FULLTEXT(firstname, lastname, address, postalCod, phoneNumber)) ENGINE = InnoDB";
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
     *
     * @throws SQLException
     */
    private void addMissingColumns() throws SQLException {

        ArrayList<String> missingColumns = missingColumnsInTable(dataTableName);

        if (missingColumns == null) {   //if no missing columns found
            return;
        }

        Map<String, String> dataDictionary = new HashMap<String, String>();
        dataDictionary.put("firstname", "VARCHAR(20)");
        dataDictionary.put("lastname", "VARCHAR(20)");
        dataDictionary.put("address", "VARCHAR(50)");
        dataDictionary.put("postalCod", "CHAR(7)");
        dataDictionary.put("phoneNumber", "CHAR(12)");
        dataDictionary.put("clientType", "CHAR(1)");

        for (String name : missingColumns) { //add missing columns
            dbConnection.createStatement().execute("ALTER TABLE " + dataTableName
                    + " ADD " + name + " " + dataDictionary.get(name));
        }
    }

    /**
     * This method looks at a given data table, and look for column names that match our required names set. </br>
     * Returns a list of missing column names. </br>
     * Returns null if every columns has been found.
     *
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

        for (int i = 1; i <= count; i++) {  //loop through the column names found in the table

            String foundColumn = resultSet.getMetaData().getColumnName(i);  //column name found at index i

            for (int j = 0; j < columnNames.length; j++) {  //step through the columnNames[] and look for match

                if (foundColumn.equalsIgnoreCase(columnNames[j])) { //match found, set corresponding flag to true
                    columnMatch[j] = true;
                    break;
                }
            }
        }

        //loop through the columnMatch[], where there is a 'false', the corresponding item from columnNames[] is added.
        for (int i = 0; i < columnMatch.length; i++) {
            if (columnMatch[i] == false) {
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

    //code testing
    public static void main(String[] args) {
        ClientManagementSystemData myDB = new ClientManagementSystemData();
        myDB.setUpDatabase("root", "greencreatures", "clientsDB");

        try {
            myDB.initializeDatabase();
            myDB.addClient(new String[]{"Xool", "Guy", "123LeValley", "S7K3J5", "306373-1234", "c"});
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
