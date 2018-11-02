// ENSF 519-2 Project 1 Exercise 1
// Oscar Chen & Savith Jayasekera

//Note: Added private method to check for database existence before creating a new one.
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.Scanner;

// Lab 10 - In Lab Exercise 1

// This program allows you to create and manage a store inventory database.
// It creates a database and datatable, then populates that table with tools from
// items.txt.
public class InventoryManager {

    public Connection jdbc_connection;
    public PreparedStatement statement;
    public String databaseName = "InventoryDB", tableName = "ToolTable", dataFile = "ItemsNew.txt";

    // Students should configure these variables for their own MySQL environment
    // If you have not created your first database in mySQL yet, you can leave the
    // "[DATABASE NAME]" blank to get a connection and create one with the createDB() method.
    public String connectionInfo = "jdbc:mysql://localhost:3306/",
            login = "root",
            password = "greencreatures";

    public InventoryManager() {
        try {
            // If this throws an error, make sure you have added the mySQL connector JAR to the project
            Class.forName("com.mysql.jdbc.Driver");

            // If this fails make sure your connectionInfo and login/password are correct
            jdbc_connection = DriverManager.getConnection(connectionInfo, login, password);
            System.out.println("Connected to: " + connectionInfo + "\n");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Use the jdbc connection to create a new database in MySQL.
    // (e.g. if you are connected to "jdbc:mysql://localhost:3306", the database will be created at "jdbc:mysql://localhost:3306/InventoryDB")
    public void createDB() {

        if (checkDBExistence()) {   //if database of same name already exist
            System.out.println("Database already exist. Use database.");

        } else {

            try {
                String query = "CREATE DATABASE " + databaseName;
                statement = jdbc_connection.prepareStatement(query);
                statement.execute();
                System.out.println("Created Database " + databaseName);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //select the database for use
        try {
            jdbc_connection.createStatement().execute("USE " + databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //check if database already exist
    private boolean checkDBExistence() {

        boolean dbExist = false;
        ResultSet resultSet = null;
        try {
            resultSet = jdbc_connection.getMetaData().getCatalogs();

            while (resultSet.next()) {
                // Get the database name, which is at position 1
                String dbName = resultSet.getString(1);
                System.out.println(dbName);
                if (dbName.equalsIgnoreCase(databaseName)){
                    dbExist = true;
                }
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbExist;
    }


    // Create a data table inside of the database to hold tools
    public void createTable() {
        String sql = "CREATE TABLE " + tableName + "(" +
                "ID INT(4) NOT NULL, " +
                "TOOLNAME VARCHAR(20) NOT NULL, " +
                "QUANTITY INT(4) NOT NULL, " +
                "PRICE DOUBLE(5,2) NOT NULL, " +
                "SUPPLIERID INT(4) NOT NULL, " +
                "PRIMARY KEY ( id ))";
        try {
            statement = jdbc_connection.prepareStatement(sql);
            statement.executeUpdate(sql);
            System.out.println("Created Table " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Removes the data table from the database (and all the data held within it!)
    public void removeTable() {
        String sql = "DROP TABLE " + tableName;
        try {
            statement = jdbc_connection.prepareStatement(sql);
            statement.executeUpdate(sql);
            System.out.println("Removed Table " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fills the data table with all the tools from the text file 'items.txt' if found
    public void fillTable() {
        try {
            Scanner sc = new Scanner(new FileReader(dataFile));
            while (sc.hasNext()) {
                String toolInfo[] = sc.nextLine().split(";");
                addItem(new Tool(Integer.parseInt(toolInfo[0]),
                        toolInfo[1],
                        Integer.parseInt(toolInfo[2]),
                        Double.parseDouble(toolInfo[3]),
                        Integer.parseInt(toolInfo[4])));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.err.println("File " + dataFile + " Not Found! Place it in project root folder.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add a tool to the database table
    public void addItem(Tool tool) {

        try {
            String sql = "INSERT INTO " + tableName + " VALUES (?, ?, ?, ?, ?)";
            statement = jdbc_connection.prepareStatement(sql);
            statement.setInt(1, tool.getID());
            statement.setString(2, tool.getName());
            statement.setInt(3,tool.getQuantity());
            statement.setDouble(4, tool.getPrice());
            statement.setInt(5, tool.getSupplierID());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // This method should search the database table for a tool matching the toolID parameter and return that tool.
    // It should return null if no tools matching that ID are found.
    public Tool searchTool(int toolID) {
        String sql = "SELECT * FROM " + tableName + " WHERE ID = ? ";
        ResultSet tool;
        try {
            statement = jdbc_connection.prepareStatement(sql);
            statement.setInt(1, toolID);
            tool = statement.executeQuery();
            if (tool.next()) {
                return new Tool(tool.getInt("ID"),
                        tool.getString("TOOLNAME"),
                        tool.getInt("QUANTITY"),
                        tool.getDouble("PRICE"),
                        tool.getInt("SUPPLIERID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Prints all the items in the database to console
    public void printTable() {
        try {
            String sql = "SELECT * FROM " + tableName;
            statement = jdbc_connection.prepareStatement(sql);
            ResultSet tools = statement.executeQuery();
            System.out.println("Tools:");
            while (tools.next()) {
                System.out.println(tools.getInt("ID") + " " +
                        tools.getString("TOOLNAME") + " " +
                        tools.getInt("QUANTITY") + " " +
                        tools.getDouble("PRICE") + " " +
                        tools.getInt("SUPPLIERID"));
            }
            tools.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        InventoryManager inventory = new InventoryManager();

        inventory.createDB();

        inventory.createTable();

        System.out.println("\nFilling the table with tools");
        inventory.fillTable();

        System.out.println("Reading all tools from the table:");
        inventory.printTable();

        System.out.println("\nSearching table for tool 1002: should return 'Grommets'");
        int toolID = 1002;
        Tool searchResult = inventory.searchTool(toolID);
        if (searchResult == null)
            System.out.println("Search Failed to find a tool matching ID: " + toolID);
        else
            System.out.println("Search Result: " + searchResult.toString());

        System.out.println("\nSearching table for tool 9000: should fail to find a tool");
        toolID = 9000;
        searchResult = inventory.searchTool(toolID);
        if (searchResult == null)
            System.out.println("Search Failed to find a tool matching ID: " + toolID);
        else
            System.out.println("Search Result: " + searchResult.toString());

        System.out.println("\nTrying to remove the table");
        inventory.removeTable();

        try {
            inventory.statement.close();
            inventory.jdbc_connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("\nThe program is finished running");
        }
    }
}