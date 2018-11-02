import java.sql.*;

public class SQLtest {

    public static void main(String[] args) {

        try {

            Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "greencreatures");
            Statement myStmt = myConnection.createStatement();

            ///////Create a new database
//            myConnection.createStatement().execute("CREATE DATABASE demo");


            ///////Delete a database
//            myConnection.createStatement().execute("DROP DATABASE demo");


            ///////Select a specific database by name
            myConnection.prepareStatement("USE demo").execute();


            //////read from data table
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM employees");
            while (myRs.next()) {
                System.out.println(myRs.getString("id") + ","
                        + myRs.getString("last_name") + ", "
                        + myRs.getNString("first_name"));
            }
            /////End of read

//            //////Create table named employees
//            String newTable = "CREATE TABLE employees ("
//                    + "id INTEGER NOT NULL AUTO_INCREMENT,"
//                    + "last_name VARCHAR(20),"
//                    + "first_name VARCHAR(20)," +
//                    "primary key (id))";
//            myStmt.executeUpdate(newTable);
//            //////End of table creation


//            //////insert into data table
//            String query = " insert into employees (last_name, first_name, middle_name) values (?, ?, ?)";
//            PreparedStatement preparedStmt = myConnection.prepareStatement(query);
//            preparedStmt.setString (1, "Jay");
//            preparedStmt.setString (2, "Savi");
//            preparedStmt.setString(3,"Chand");
//            preparedStmt.execute();
//            //////End of insert


//            //////Add column to data table
//            int addSuccess = myStmt.executeUpdate("ALTER TABLE employees ADD middle_name VARCHAR(20)");
//            System.out.println("Add column operation: " + addSuccess);
//            //////End of add column


//            /////Remove a row from data table
//            String query = " DELETE from employees WHERE id = ? ";
//            PreparedStatement preparedStatement = myConnection.prepareStatement(query);
//            preparedStmt.setInt(1, 5);
//            preparedStatement.execute();
//            /////End of remove row


            myConnection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
