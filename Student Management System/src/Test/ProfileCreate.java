package Test;
import Singelton_Pattern.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author TaReK
 */
public class ProfileCreate implements Profile {
    @Override
    public void create(String name,String code,String Case){
        String query = "INSERT INTO students (`name`, `code`, `graduation`) VALUES (?, ?, ?)";
        
        DBconnection dbConnection = DBconnection.getInstance(); //the single instance that will access the DBconnection 
        try (Connection connection = dbConnection.getConnection(); 
         PreparedStatement statement = connection.prepareStatement(query)) {

        // Set parameters for the query
        statement.setString(1, name);
        statement.setString(2, code);
        statement.setString(3, Case);

        // Execute the query and check if the insertion is successful
        int rowsAffected = statement.executeUpdate();

        // Provide feedback based on the success of the query
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Student Registered Successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Error: Failed to Register Student.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
    }
}
