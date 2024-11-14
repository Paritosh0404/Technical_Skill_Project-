import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class CreateCourseFrame extends JFrame {
    private JTextField courseIdField, courseNameField, instructorField, locationField;
    private JSpinner maxStudentsSpinner, sectionSpinner;
    private JButton createButton;

    public CreateCourseFrame() {
        setTitle("Create Course");
        setSize(600, 400); // Compact frame size
        setLocationRelativeTo(null); // Center on screen
        setLayout(new GridLayout(7, 2, 10, 10)); // Add padding between components

        // Font and color settings
        Font labelFont = new Font("Arial", Font.BOLD, 20); // Consistent font size for labels
        Font fieldFont = new Font("Arial", Font.PLAIN, 18); // Slightly smaller font for input fields

        // Course ID
        JLabel courseIdLabel = new JLabel("Course ID:");
        courseIdLabel.setFont(labelFont);
        courseIdField = new JTextField();
        courseIdField.setFont(fieldFont);

        // Course Name
        JLabel courseNameLabel = new JLabel("Course Name:");
        courseNameLabel.setFont(labelFont);
        courseNameField = new JTextField();
        courseNameField.setFont(fieldFont);

        // Max Students
        JLabel maxStudentsLabel = new JLabel("Max Students:");
        maxStudentsLabel.setFont(labelFont);
        maxStudentsSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        maxStudentsSpinner.setFont(fieldFont);

        // Instructor
        JLabel instructorLabel = new JLabel("Instructor:");
        instructorLabel.setFont(labelFont);
        instructorField = new JTextField();
        instructorField.setFont(fieldFont);

        // Section
        JLabel sectionLabel = new JLabel("Section:");
        sectionLabel.setFont(labelFont);
        sectionSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        sectionSpinner.setFont(fieldFont);

        // Location
        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(labelFont);
        locationField = new JTextField();
        locationField.setFont(fieldFont);

        // Create button
        createButton = new JButton("Create Course");
        createButton.setFont(new Font("Arial", Font.BOLD, 22));
        createButton.setBackground(new Color(0, 153, 204)); // Consistent color with dashboard button
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        createButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Padding inside button

        // Action listener for button
        createButton.addActionListener(e -> {
            String courseId = courseIdField.getText();
            String courseName = courseNameField.getText();
            int maxStudents = (int) maxStudentsSpinner.getValue();
            String instructor = instructorField.getText();
            int section = (int) sectionSpinner.getValue();
            String location = locationField.getText();

            if (courseId.isEmpty() || courseName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Course ID and Course Name cannot be empty.");
                return;
            }

            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO courses (course_id, course_name, max_students, instructor, section, location) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, courseId);
                statement.setString(2, courseName);
                statement.setInt(3, maxStudents);
                statement.setString(4, instructor);
                statement.setInt(5, section);
                statement.setString(6, location);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Course created successfully!");
                    dispose(); // Close the frame after successful creation
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving course to the database.");
            }
        });

        // Adding components to the frame
        add(courseIdLabel); add(courseIdField);
        add(courseNameLabel); add(courseNameField);
        add(maxStudentsLabel); add(maxStudentsSpinner);
        add(instructorLabel); add(instructorField);
        add(sectionLabel); add(sectionSpinner);
        add(locationLabel); add(locationField);
        add(new JLabel()); add(createButton); // Empty label for alignment

        setVisible(true);
    }
}
