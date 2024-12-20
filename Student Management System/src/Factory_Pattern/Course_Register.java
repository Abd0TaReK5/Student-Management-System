package Factory_Pattern;
import Singelton_Pattern.DBconnection;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;


/**
 *
 * @author TaReK
 */
public class Course_Register implements courses{
    @Override
    public void Register(String course, String courseCode, String courseType) {
    String query = "INSERT INTO courses (`course-name`, `course-code`, `type`) VALUES (?, ?, ?)";

    // Get the singleton instance of DBconnection
    DBconnection dbConnection = DBconnection.getInstance();

    try (Connection connection = dbConnection.getConnection(); 
         PreparedStatement statement = connection.prepareStatement(query)) {

        // Set parameters for the query
        statement.setString(1, course);
        statement.setString(2, courseCode);
        statement.setString(3, courseType);

        // Execute the query and check if the insertion is successful
        int rowsAffected = statement.executeUpdate();

        // Provide feedback based on the success of the query
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Course Registered Successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Error: Failed to Register Course.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
}
}
