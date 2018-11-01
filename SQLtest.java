import java.sql.*;

public class SQLtest {

    public static void main(String[] args) {

        try {

            Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?autoReconnect=true&useSSL=false", "root", "greencreatures");
            Statement myStmt = myConnection.createStatement();

            //////read from data table
            ResultSet myRs = myStmt.executeQuery("select * from employees");
            while (myRs.next()) {
                System.out.println(myRs.getString("last_name") + ", " + myRs.getNString("first_name"));
            }
            /////End of read

            //////Create table named employees
            String newTable = "CREATE TABLE employees ("
                    + "id INTEGER NOT NULL AUTO_INCREMENT,"
                    + "last_name VARCHAR(20),"
                    + "first_name VARCHAR(20)," +
                    "primary key (id))";
            myStmt.executeUpdate(newTable);
            //////End of table creation


            //////insert into data table
            String query = " insert into employees (first_name, last_name) values (?, ?)";
            PreparedStatement preparedStmt = myConnection.prepareStatement(query);
            preparedStmt.setString (1, "Savith");
            preparedStmt.setString (2, "Sucker");
            preparedStmt.execute();
            //////End of insert


            myConnection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
