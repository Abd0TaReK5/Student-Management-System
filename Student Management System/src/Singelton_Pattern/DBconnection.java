package Singelton_Pattern;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    //create local instance for the class DBconnection
    private static DBconnection instance;
    //creaete object from the sql method to be used for establishing the connection with the DB
    private Connection connection;
    // this is prive constructor to prevent instantiation because this is sengelton pattern!!
    private DBconnection() {
        initializeConnection();
    }
    // the only direct accessble method in the whole class to get the only instance to use the class's private methods!!
    public static DBconnection getInstance() {
        if (instance == null) {
            instance = new DBconnection();
        }
        return instance;
    }

    private void initializeConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/student_management"; // Replace with your DB info!!
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                initializeConnection(); // Re-establish the connection if it was closed
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
