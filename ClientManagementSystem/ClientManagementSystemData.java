// ENSF 519-2 Java Project I
// Client Management System
// Oscar Chen & Savith Jayasekera
// November 5 2018

import java.io.BufferedReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Data model
 */
public class ClientManagementSystemData {
    private Connection dbConnection;
    private String dbName;
    private String username;
    private String password;
    private String dataTableName = "client_table";
    private Map<String, String> dataDictionary = new HashMap<String, String>();

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

        dbConnection.createStatement().execute("USE " + dbName);

        if (!checkDataTable(dbName)) { //create new table in database if existing one not found
            createNewTable();
        }

        addMissingColumns();    //checks the columns in the table, add any missing columns;
    }

    /**
     * Called upon to add a client entry into database.
     *
     * @param client
     * @throws SQLException
     */
    public void addClient(Client client) throws Exception {

        client = verifyInput(client);   //check input for data integrity
        if (client == null) { //invalid input
            return;
        }

        String query = " insert into " + dataTableName +
                " (firstname, lastname, address, postalCod, phoneNumber, clientType)" + " values (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
        preparedStmt.setString(1, client.getFirstName());
        preparedStmt.setString(2, client.getLastName());
        preparedStmt.setString(3, client.getAddress());
        preparedStmt.setString(4, client.getPostalCode());
        preparedStmt.setString(5, client.getPhoneNumber());
        preparedStmt.setString(6, client.getClientType());

        preparedStmt.execute();
    }

    /**
     * Verify format of input data, make necessary changes to format if needed to maintain format consistency. </br>
     * Returns null if the input data does not meet requirement. </br>
     * Returns a re-formatted array of String type ready that meet format requirements.
     *
     * @param client
     * @return
     */
    private Client verifyInput(Client client) throws Exception {

        if (client.getFirstName().length() > 20) {
            throw new ClientDataInputException();
        }
        if (client.getLastName().length() > 20) {
            throw new ClientDataInputException();
        }
        if (client.getAddress().length() > 50) {
            throw new ClientDataInputException();
        }
        if (fixPostalFormat(client.getPostalCode()) == null) {
            throw new ClientPostalException();
        } else {
            client.setPostalCode(fixPostalFormat(client.getPostalCode()));
        }
        if (fixPhoneNumber(client.getPhoneNumber()) == null) {
            throw new ClientPhoneNumberException();
        } else {
            client.setPhoneNumber(fixPhoneNumber(client.getPhoneNumber()));
        }
        if (client.getClientType().equalsIgnoreCase("C") || client.getClientType().equalsIgnoreCase("R")) {
            client.setClientType(client.getClientType().toUpperCase());
        } else {
            throw new ClientTypeException();
        }

        return client;
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
        String regex = "^[A-Z,a-z][0-9][A-Z,a-z]\\s?[0-9][A-Z,a-z][0-9]$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(postal);

        if (!matcher.matches()) {   //not a postal format, return null
            return null;
        }

        if (postal.length() == 6) {  //if no space in the middle, add space
            postal = postal.substring(0, 3) + " " + postal.substring(3, 6);
        }

        postal = postal.toUpperCase();

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
     * Search for a client by looking for a phrase in a specific column.
     *
     * @param phrase
     * @param column
     * @return
     * @throws SQLException
     */
    public ArrayList<Client> searchColumn(String phrase, String column) throws SQLException {
        String query = "SELECT * FROM " + dataTableName + " WHERE " + column + " LIKE '%" + phrase + "%'";

        ResultSet result = dbConnection.createStatement().executeQuery(query);

        return parseResultSetToList(result);
    }

    /**
     * Private utility methods used by search methods. </br>
     * Accepts an objection of type ResultSet as parameter, puts content of set into an ArrayList of Client objects.
     *
     * @param result
     * @return
     * @throws SQLException
     */
    private ArrayList<Client> parseResultSetToList(ResultSet result) throws SQLException {
        ArrayList<Client> resultList = new ArrayList<>();
        while (result.next()) {
            Client resultClient = new Client();
            resultClient.setDataID(result.getString("id"));
            resultClient.setFirstName(result.getString("firstname"));
            resultClient.setLastName(result.getString("lastname"));
            resultClient.setAddress(result.getString("address"));
            resultClient.setPostalCode(result.getString("postalCod"));
            resultClient.setPhoneNumber(result.getString("phoneNumber"));
            resultClient.setClientType(result.getString("clientType"));
            resultList.add(resultClient);
        }

        return resultList;
    }

    /**
     * Remove a client by providing the client's id from the database.
     *
     * @param id
     * @throws SQLException
     */
    public void deleteClient(String id) throws SQLException {
        dbConnection.createStatement().execute(" DELETE from " + dataTableName + " WHERE id = " + id);
    }

    /**
     * Remove a client by providing the client as an object of type Client
     *
     * @param client
     * @throws SQLException
     */
    public void deleteClient(Client client) throws SQLException {
        deleteClient(client.getDataID());
    }

    /**
     * Updates a client's info by providing a new client object to overwrite one in the database which shares the same id.
     * @param client
     * @throws SQLException
     */
    public void updateClient(Client client) throws Exception {
        client = verifyInput(client);

        if (client == null){ //if input is invalid
            return;
        }

        String id = client.getDataID();
        String firstname = client.getFirstName();
        String lastname = client.getLastName();
        String address = client.getAddress();
        String postalCod = client.getPostalCode();
        String phoneNumber = client.getPhoneNumber();
        String clientType = client.getClientType();

        dbConnection.createStatement().execute(" UPDATE " + dataTableName +
                " SET firstname = '" + firstname + "', lastname = '" + lastname + "', address = '" + address + "'," +
                "postalCod = '" + postalCod + "', phoneNumber = '" + phoneNumber + "', clientType = '" + clientType +"' " +
                "WHERE id = " + id);
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

    /**
     * Receives an input of type BufferedReader. </br>
     * Upload a txt file of client data to database. </br>
     * The file must be structured such that each line represent a client. </br>
     * The client's information must be separated by a semi-colon as such: </br>
     * first name; last name; address; postal code; phone number; client type </br>
     * See example txt file.
     */
    public void uploadFileToDatabase(BufferedReader reader) throws Exception {

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            String [] details = line.split(";");
            Client client = new Client();
            client.setFirstName(details[0]);
            client.setLastName(details[1]);
            client.setAddress(details[2]);
            client.setPostalCode(details[3]);
            client.setPhoneNumber(details[4]);
            client.setClientType(details[5]);
            addClient(client);
        }
    }
}
