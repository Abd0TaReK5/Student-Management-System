package Factory_Pattern;
import Singelton_Pattern.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Prototype {

    public static boolean isStudentCodeExist(String code) {
        DBconnection dbConnection = DBconnection.getInstance();
        String query = "SELECT COUNT(*) FROM students WHERE code = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            
            stmt.setString(1, code);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //here we give him the parameter of the StudentProfile class and the StudentBuilder nested class inside it 
    public static void cloneAndValidate(StudentProfile.StudentBuilder builder, String code) {
        if (isStudentCodeExist(code)) {
            JOptionPane.showMessageDialog(null, "Duplicated student code, please try again.");
        } else {
            // Clone the prototype to preserve builder integrity
            StudentProfile student = builder.build(); // Build the initial object
            student.cloneProfile().create(student);   // Clone and create in a single line
        }
    }
}
