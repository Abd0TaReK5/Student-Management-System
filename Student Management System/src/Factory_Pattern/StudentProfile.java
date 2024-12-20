package Factory_Pattern;

import Singelton_Pattern.DBconnection;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class StudentProfile  {
    private final String name;
    private final String code;
    private final String address;
    private final String phone;
    private final String email;
    private final String studentCase;

    // Constructor
    public StudentProfile(String name, String code, String address, String phone, String email, String studentCase) {
        this.name = name;
        this.code = code;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.studentCase = studentCase;
    }

    // Implementing the cloneProfile method for Prototype Pattern
    
    public StudentProfile cloneProfile() {
        return new StudentProfile(this.name, this.code, this.address, this.phone, this.email, this.studentCase);
    }

    // Static Builder Class
    public static class StudentBuilder {
        private String name;
        private String code;
        private String address;
        private String phone;
        private String email;
        private String studentCase;

        public StudentBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public StudentBuilder setCode(String code) {
            this.code = code;
            return this;
        }

        public StudentBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public StudentBuilder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public StudentBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public StudentBuilder setStudentCase(String studentCase) {
            this.studentCase = studentCase;
            return this;
        }

        public StudentProfile build() {
            return new StudentProfile(name, code, address, phone, email, studentCase);
        }
    }

    // Getters
    
    public String getName() { return name; }

    
    public String getCode() { return code; }

    
    public String getAddress() { return address; }

    
    public String getPhone() { return phone; }

   
    public String getEmail() { return email; }

    
    public String getStudentCase() { return studentCase; }

    // Create method with database logic
  
    public void create(StudentProfile student) {
        DBconnection dbConnection = DBconnection.getInstance();
        String query = "INSERT INTO students (name, code, graduation, address, phone, email) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getCode());
            stmt.setString(3, student.getStudentCase());
            stmt.setString(4, student.getAddress());
            stmt.setString(5, student.getPhone());
            stmt.setString(6, student.getEmail());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Student Profile Created Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
