package Singelton_Pattern;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class GradeProcessor implements GradeProcess {
    private static GradeProcessor instance; // Singleton instance
    
    private GradeProcessor() {
        
    }

    public static GradeProcessor getInstance() {
        if (instance == null) {
            instance = new GradeProcessor();
        }
        return instance;
    }
    
    private String determineGrade(double grade) {
    String[] grades = { "A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "F" };
    int index;

    if (grade >= 97) {
        index = 0;
    } else if (grade >= 60) {
        index = (int) Math.floor((97 - grade) / 3) + 1;
    } else {
        index = grades.length - 1; // grade is F
    }

    return grades[Math.min(index, grades.length - 1)];
}
    
    @Override
    public void update(String studentCode,String course, double numericGrade) {
    // Convert numeric grade to letter grade
    String letterGrade = determineGrade(numericGrade);

    // The instance we get from the DBConnection file to access the DB
    String updateQuery = "UPDATE students SET grade = ?, course = ? WHERE code = ?";

    DBconnection dbConnection = DBconnection.getInstance();
    try (Connection connection = dbConnection.getConnection();
         PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

        // Set parameters for the update query
        updateStmt.setString(1, letterGrade); // Letter grade to update
        updateStmt.setString(2, course); // Letter grade to update
        updateStmt.setString(3, studentCode); // Student code to identify the record

        // Execute update
        int rowsUpdated = updateStmt.executeUpdate();

        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(null, "Data updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Student Code not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
@Override
public void delete(String studentCode, String course) {
    // The instance we get from the DBConnection file to access the DB
    DBconnection dbConnection = DBconnection.getInstance();
    
    // SQL query to delete grade based on studentCode and course
    String deleteQuery = "DELETE FROM students WHERE code = ? AND course = ?";

    try (Connection connection = dbConnection.getConnection();
         PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {

        // Set parameters for the delete query
        deleteStmt.setString(1, studentCode); // Student code to identify the record
        deleteStmt.setString(2, course); // Course name to identify the record

        // Execute delete
        int rowsDeleted = deleteStmt.executeUpdate();

        if (rowsDeleted > 0) {
            JOptionPane.showMessageDialog(null, "successfully deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No matching student found for the given Data.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

}
