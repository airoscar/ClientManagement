package Server.Model;

// ENSF 519-2 Java Project I
// Server.Model.Person Management System
// Oscar Chen & Savith Jayasekera
// November 5 2018

import Shared.*;

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
public class DatabaseController {
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
     * Called upon to add a person entry into database.
     *
     * @param person
     * @throws SQLException
     */
    public void addClient(Person person) throws Exception {

        String query = " insert into " + dataTableName +
                " (firstname, lastname, address, postalCod, phoneNumber, clientType)" + " values (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
        preparedStmt.setString(1, person.getFirstName());
        preparedStmt.setString(2, person.getLastName());
        preparedStmt.setString(3, person.getAddress());
        preparedStmt.setString(4, person.getPostalCode());
        preparedStmt.setString(5, person.getPhoneNumber());
        preparedStmt.setString(6, person.getClientType());

        preparedStmt.execute();
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
    public ArrayList<Person> searchColumn(String phrase, String column) throws SQLException {
        String query = "SELECT * FROM " + dataTableName + " WHERE " + column + " LIKE '%" + phrase + "%'";

        ResultSet result = dbConnection.createStatement().executeQuery(query);

        return parseResultSetToList(result);
    }

    /**
     * Private utility methods used by search methods. </br>
     * Accepts an objection of type ResultSet as parameter, puts content of set into an ArrayList of Server.Model.Person objects.
     *
     * @param result
     * @return
     * @throws SQLException
     */
    private ArrayList<Person> parseResultSetToList(ResultSet result) throws SQLException {
        ArrayList<Person> resultList = new ArrayList<>();
        while (result.next()) {
            Person resultPerson = new Person();
            resultPerson.setDataID(result.getString("id"));
            resultPerson.setFirstName(result.getString("firstname"));
            resultPerson.setLastName(result.getString("lastname"));
            resultPerson.setAddress(result.getString("address"));
            resultPerson.setPostalCode(result.getString("postalCod"));
            resultPerson.setPhoneNumber(result.getString("phoneNumber"));
            resultPerson.setClientType(result.getString("clientType"));
            resultList.add(resultPerson);
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
     * Remove a person by providing the person as an object of type Server.Model.Person
     *
     * @param person
     * @throws SQLException
     */
    public void deleteClient(Person person) throws SQLException {
        deleteClient(person.getDataID());
    }

    /**
     * Updates a person's info by providing a new person object to overwrite one in the database which shares the same id.
     * @param person
     * @throws SQLException
     */
    public void updateClient(Person person) throws Exception {

        String id = person.getDataID();
        String firstname = person.getFirstName();
        String lastname = person.getLastName();
        String address = person.getAddress();
        String postalCod = person.getPostalCode();
        String phoneNumber = person.getPhoneNumber();
        String clientType = person.getClientType();

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
            Person person = new Person();
            person.setFirstName(details[0]);
            person.setLastName(details[1]);
            person.setAddress(details[2]);
            person.setPostalCode(details[3]);
            person.setPhoneNumber(details[4]);
            person.setClientType(details[5]);
            addClient(person);
        }
    }
}
