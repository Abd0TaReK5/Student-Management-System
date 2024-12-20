package Test;

import Factory_Pattern.StudentProfile;

/**
 *
 * @author TaReK
 */
public interface Student_Profile {
    void create(StudentProfile student);
    String getName();
    String getCode();
    String getAddress();
    String getPhone();
    String getEmail();
    String getStudentCase();
    StudentProfile cloneProfile(); // Method to clone the StudentProfile object
}