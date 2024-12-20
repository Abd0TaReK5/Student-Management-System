package Singelton_Pattern;
import Factory_Pattern.courses;
import Factory_Pattern.Prototype;
import Factory_Pattern.Course_Register;
import Factory_Pattern.StudentProfile;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;


public class MainGUI extends JFrame implements GUI {
    String currentUserRole = "admin";
    @Override
    public void MainGUI() {
        // Set up the GUI frame
        // You can change this to "admin" for testing
        // Initialize the Proxy with the current user role
        DBconnectionProxy dbProxy = new Proxy(currentUserRole);
        setTitle("Student Management");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a JTabbedPane to hold multiple tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        // Create the "Student Management" tab
        JPanel studentPanel = createStudentPanel();
        tabbedPane.addTab("Student Management", studentPanel);
        // Create the "Student Management" tab
        JPanel coursePanel = createCoursePanel();
        tabbedPane.addTab("Course Management", coursePanel);
        // Create the "System" tab (initially empty)
        JPanel systemPanel = createSystemPanel();
        tabbedPane.addTab("The System", systemPanel);
        
        JPanel GradePanel = createGradeProcessingPanel();
        tabbedPane.addTab("Data Management", GradePanel);

        // Add a ChangeListener to intercept tab selection
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 1) { // "course" tab is at index 1
                dbProxy.getConnection();
                JPasswordField passwordField = new JPasswordField();
                Object[] message = {"Enter administrative password:", passwordField};
                int option = JOptionPane.showConfirmDialog(
                        this, message, "Authentication", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    String enteredPassword = new String(passwordField.getPassword());
                    if (!validatePassword(enteredPassword)) {
                        JOptionPane.showMessageDialog(this, "Incorrect password! Access denied.");
                        tabbedPane.setSelectedIndex(0); // Switch back to "Student Management" tab
                    }
                } else {
                    tabbedPane.setSelectedIndex(0); // open the tab!!
                }
            }
        });

        // Add the tabbed pane to the frame
        add(tabbedPane);

        // Display the GUI
        setVisible(true);
    }
     //-----------------------------student tab----------------------------//
    private JPanel createStudentPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); 
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Existing fields
    gbc.gridx = 0; gbc.gridy = 0;
    panel.add(new JLabel("Student Name:"), gbc);
    gbc.gridx = 1;
    JTextField nameField = new JTextField(20);
    panel.add(nameField, gbc);

    gbc.gridx = 0; gbc.gridy = 1;
    panel.add(new JLabel("Student Code:"), gbc);
    gbc.gridx = 1;
    JTextField codeField = new JTextField(20);
    panel.add(codeField, gbc);

    // New fields for Address, Phone, and Email
    gbc.gridx = 0; gbc.gridy = 2;
    panel.add(new JLabel("Address:"), gbc);
    gbc.gridx = 1;
    JTextField addressField = new JTextField(20);
    panel.add(addressField, gbc);

    gbc.gridx = 0; gbc.gridy = 3;
    panel.add(new JLabel("Phone:"), gbc);
    gbc.gridx = 1;
    JTextField phoneField = new JTextField(20);
    panel.add(phoneField, gbc);

    gbc.gridx = 0; gbc.gridy = 4;
    panel.add(new JLabel("Email:"), gbc);
    gbc.gridx = 1;
    JTextField emailField = new JTextField(20);
    panel.add(emailField, gbc);

    // Radio Buttons for Student Case
    gbc.gridx = 0; gbc.gridy = 5;
    panel.add(new JLabel("Case:"), gbc);

    gbc.gridx = 1;
    JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    ButtonGroup typeGroup = new ButtonGroup();
    JRadioButton undergradButton = new JRadioButton("Undergraduation");
    JRadioButton partTimeButton = new JRadioButton("Part-Time");
    JRadioButton graduatedButton = new JRadioButton("Graduated");
    // make all the options in the same group to select only one of them!
    typeGroup.add(undergradButton);
    typeGroup.add(partTimeButton);
    typeGroup.add(graduatedButton);
    
    radioPanel.add(undergradButton);
    radioPanel.add(partTimeButton);
    radioPanel.add(graduatedButton);
    
    panel.add(radioPanel, gbc);

    // Capture selected radio button
    String[] selectedCase = {""};
    ActionListener radioListener = e -> selectedCase[0] = e.getActionCommand();
    undergradButton.setActionCommand("Undergraduation");
    partTimeButton.setActionCommand("Part-Time");
    graduatedButton.setActionCommand("Graduated");
    
    undergradButton.addActionListener(radioListener);
    partTimeButton.addActionListener(radioListener);
    graduatedButton.addActionListener(radioListener);

    // Register Button
    gbc.gridx = 0; gbc.gridy = 6;
    gbc.gridwidth = 2;
    JButton registerButton = new JButton("Register Student");
registerButton.addActionListener(e -> {
    String name = nameField.getText();
    String code = codeField.getText();
    String address = addressField.getText();
    String phone = phoneField.getText();
    String email = emailField.getText();
    String studentCase = selectedCase[0];

    // Validation
    if (name.trim().isEmpty() || code.trim().isEmpty() || studentCase.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please fill in all required fields!");
    } else {
        // Create StudentProfile instance using Builder
        StudentProfile.StudentBuilder builder = new StudentProfile.StudentBuilder()
                .setName(name)
                .setCode(code)
                .setAddress(address)
                .setPhone(phone)
                .setEmail(email)
                .setStudentCase(studentCase);

        // Call the cloneAndValidate method to check if the student code already exists before creation
        //from the cloneAndValidate method if the student didnt exist,it will redirect the data to the builder method!!
        Prototype.cloneAndValidate(builder, code);

        // Success message will be handled inside cloneAndValidate if validation passes
    }
});
panel.add(registerButton, gbc);

return panel;

}

//-----------------------------course tab----------------------------//
private JPanel createCoursePanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between components
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Course Name label and text field
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(new JLabel("Course Name:"), gbc);

    gbc.gridx = 1;
    JTextField coursenameField = new JTextField(20);
    panel.add(coursenameField, gbc);

    // Course Code label and text field
    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(new JLabel("Course Code:"), gbc);

    gbc.gridx = 1;
    JTextField coursecodeField = new JTextField(20);
    panel.add(coursecodeField, gbc);

    // Course Type label and radio buttons
    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(new JLabel("Course Type:"), gbc);

    gbc.gridx = 1;
    JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Ensures radio buttons are displayed correctly
    ButtonGroup typeGroup = new ButtonGroup();

    JRadioButton coreButton = new JRadioButton("Core");
    JRadioButton electiveButton = new JRadioButton("Elective");
    JRadioButton labButton = new JRadioButton("Lab");

    // Add radio buttons to the group and panel
    typeGroup.add(coreButton);
    typeGroup.add(electiveButton);
    typeGroup.add(labButton);
    radioPanel.add(coreButton);
    radioPanel.add(electiveButton);
    radioPanel.add(labButton);

    panel.add(radioPanel, gbc);

    // ActionListener to capture the selected course type
    String[] selectedType = {""}; // To hold the selected type
    ActionListener radioListener = e -> selectedType[0] = e.getActionCommand();

    coreButton.setActionCommand("Core");
    electiveButton.setActionCommand("Elective");
    labButton.setActionCommand("Lab");

    coreButton.addActionListener(radioListener);
    electiveButton.addActionListener(radioListener);
    labButton.addActionListener(radioListener);

    // Course registration button
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    JButton registerButton = new JButton("Register Course");
    registerButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String course = coursenameField.getText();
            String courseCode = coursecodeField.getText();
            String courseType = selectedType[0];

            if (course.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter Course Name!");
            } else if (courseCode.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter Course Code!");
            } else if (courseType.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select the Course Type!");
            } else {
                // Do something with the course data (save to DB or other logic)
                courses c1=new Course_Register();
                c1.Register(course, courseCode, courseType);
            }
        }
    });

    panel.add(registerButton, gbc);

    // Ensure layout is refreshed
    panel.revalidate();
    panel.repaint();

    return panel;
}
     //-----------------------------system tab----------------------------//

    private JPanel createSystemPanel() {
    JPanel panel = new JPanel(new BorderLayout());

    // Filter Panel
    JPanel filterPanel = new JPanel();
    JLabel filterLabel = new JLabel("Filter by:");
    JComboBox<String> filterComboBox = new JComboBox<>();
    filterComboBox.addItem("All");
    filterComboBox.addItem("name");
    filterComboBox.addItem("level");
    filterComboBox.addItem("course");
    filterComboBox.addItem("graduation");
    filterPanel.add(filterLabel);
    filterPanel.add(filterComboBox);

    JButton refreshButton = new JButton("Refresh");
    filterPanel.add(refreshButton);

    panel.add(filterPanel, BorderLayout.NORTH);

    // Table to display data
    JTable table = new JTable();
    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane, BorderLayout.CENTER);

    // Initialize Proxy with current user role (this should be dynamically passed)
//  //access proxy class through the interface DBconnectionProxy
    DBconnectionProxy dbProxy = new Proxy(currentUserRole); // Pass the current user role to Proxy

    // Check if user is admin before calling populateTable
    if (dbProxy.hasPermission()) {
        // If permission is granted (user is admin), populate the table
        table registerInstance = TheSystem.getInstance(); // Get Singleton instance of System
        registerInstance.populateTable(table, filterComboBox.getSelectedItem().toString());
    } else {
        // If not admin, show an empty table
        DefaultTableModel emptyModel = new DefaultTableModel(new Object[][]{}, new String[]{"name", "code", "course","grade","level","graduation","address","phone","email"});
        table.setModel(emptyModel);
        System.out.println("Access denied. Displaying empty table.");
    }

    // Add filter action
    filterComboBox.addActionListener(e -> {
        if (dbProxy.hasPermission()) {
            table registerInstance = TheSystem.getInstance();
            registerInstance.populateTable(table, filterComboBox.getSelectedItem().toString());
        } else {
            DefaultTableModel emptyModel = new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Name", "Course"});
            table.setModel(emptyModel);
            System.out.println("Access denied. Displaying empty table.");
        }
    });

    // Add refresh action
    refreshButton.addActionListener(e -> {
        if (dbProxy.hasPermission()) {
            table registerInstance = TheSystem.getInstance();
            registerInstance.populateTable(table, filterComboBox.getSelectedItem().toString());
        } else {
            DefaultTableModel emptyModel = new DefaultTableModel(new Object[][]{}, new String[]{"name", "code", "course","grade","level","graduation","address","phone","email"});
            table.setModel(emptyModel);
            System.out.println("Access denied. Displaying empty table.");
        }
    });

    return panel;
}
    //------------------------------Update tab------------------------------------//
    private JPanel createGradeProcessingPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Student Code label and text field
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(new JLabel("Student Code:"), gbc);

    gbc.gridx = 1;
    JTextField studentCodeField = new JTextField(20);
    panel.add(studentCodeField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(new JLabel("Course Name:"), gbc);

    gbc.gridx = 1;
    JTextField courseField = new JTextField(20);
    panel.add(courseField, gbc);

    // Grade label and text field
    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(new JLabel("Student Grade:"), gbc);

    gbc.gridx = 1;
    JTextField gradeField = new JTextField(20);
    panel.add(gradeField, gbc);

    // Function to validate student code and course fields
    Runnable validateFields = () -> {
        String studentCode = studentCodeField.getText();
        String course = courseField.getText();

        if (studentCode.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter Student Code!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (course.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter Course Name!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    };

    // Update Button
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    JButton addGradeButton = new JButton("Update");
    GradeProcess gradeProcessor = GradeProcessor.getInstance();
    addGradeButton.addActionListener(e -> {
        validateFields.run(); // Call validation logic
        
        String studentCode = studentCodeField.getText();
        String course = courseField.getText();
        double numericGrade;
        
        // Validation for grade field
        try {
            numericGrade = Double.parseDouble(gradeField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid grade!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // If validation passes, update the grade
        gradeProcessor.update(studentCode, course, numericGrade);
    });
    panel.add(addGradeButton, gbc);

    // Delete Button
    gbc.gridy = 4;
    JButton deleteButton = new JButton("Delete");
    deleteButton.addActionListener(e -> {
        validateFields.run(); // Call validation logic
        
        String studentCode = studentCodeField.getText();
        String course = courseField.getText();
        
        // If validation passes, delete the grade
        GradeProcess G1 = GradeProcessor.getInstance();
        G1.delete(studentCode, course);
    });
    panel.add(deleteButton, gbc);

    return panel;
}

    private boolean validatePassword(String password) {
        return "123".equals(password); // Replace "123" with your password
    }
}