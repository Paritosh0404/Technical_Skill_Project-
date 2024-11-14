import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditCourseFrame extends JFrame {
    private JTextField courseIdField, courseNameField, instructorField, locationField;
    private JSpinner maxStudentsSpinner, sectionSpinner;
    private JButton fetchButton, updateButton;

    public EditCourseFrame() {
        setTitle("Edit Course");
        setSize(500, 500);  // Increased size for better arrangement
        setLayout(new GridBagLayout());  // Use GridBagLayout for more control
        setLocationRelativeTo(null);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Padding around components

        // Font and color settings for text fields and labels
        Font labelFont = new Font("Arial", Font.PLAIN, 20);
        Font fieldFont = new Font("Arial", Font.PLAIN, 20);
        Color buttonColor = new Color(0, 204, 255);

        // Course ID Label and Field
        JLabel courseIdLabel = new JLabel("Course ID:");
        courseIdLabel.setFont(labelFont);
        courseIdField = new JTextField();
        courseIdField.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        add(courseIdLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(courseIdField, gbc);

        // Fetch Button
        fetchButton = new JButton("Fetch Course");
        fetchButton.setFont(new Font("Arial", Font.PLAIN, 25));
        fetchButton.setBackground(buttonColor);
        fetchButton.addActionListener(e -> fetchCourseDetails());

        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        add(fetchButton, gbc);

        // Course Name Label and Field
        JLabel courseNameLabel = new JLabel("Course Name:");
        courseNameLabel.setFont(labelFont);
        courseNameField = new JTextField();
        courseNameField.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        add(courseNameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(courseNameField, gbc);

        // Max Students Label and Spinner
        JLabel maxStudentsLabel = new JLabel("Max Students:");
        maxStudentsLabel.setFont(labelFont);
        maxStudentsSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        maxStudentsSpinner.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 3;
        add(maxStudentsLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        add(maxStudentsSpinner, gbc);

        // Instructor Label and Field
        JLabel instructorLabel = new JLabel("Instructor:");
        instructorLabel.setFont(labelFont);
        instructorField = new JTextField();
        instructorField.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 4;
        add(instructorLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        add(instructorField, gbc);

        // Section Label and Spinner
        JLabel sectionLabel = new JLabel("Section:");
        sectionLabel.setFont(labelFont);
        sectionSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        sectionSpinner.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 5;
        add(sectionLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 5;
        add(sectionSpinner, gbc);

        // Location Label and Field
        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(labelFont);
        locationField = new JTextField();
        locationField.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 6;
        add(locationLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 6;
        add(locationField, gbc);

        // Update Button
        updateButton = new JButton("Update Course");
        updateButton.setFont(new Font("Arial", Font.PLAIN, 25));
        updateButton.setBackground(buttonColor);
        updateButton.addActionListener(e -> updateCourseDetails());

        gbc.gridx = 1; gbc.gridy = 7;
        add(updateButton, gbc);

        setVisible(true);
    }

    private void fetchCourseDetails() {
        String courseId = courseIdField.getText();
        if (courseId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a course ID.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM courses WHERE course_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, courseId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                courseNameField.setText(resultSet.getString("course_name"));
                maxStudentsSpinner.setValue(resultSet.getInt("max_students"));
                instructorField.setText(resultSet.getString("instructor"));
                sectionSpinner.setValue(resultSet.getInt("section"));
                locationField.setText(resultSet.getString("location"));
            } else {
                JOptionPane.showMessageDialog(this, "Course with ID " + courseId + " not found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching course details from the database.");
        }
    }

    private void updateCourseDetails() {
        String courseId = courseIdField.getText();
        String courseName = courseNameField.getText();
        int maxStudents = (int) maxStudentsSpinner.getValue();
        String instructor = instructorField.getText();
        int section = (int) sectionSpinner.getValue();
        String location = locationField.getText();

        if (courseId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a course ID.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE courses SET course_name = ?, max_students = ?, instructor = ?, section = ?, location = ? WHERE course_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, courseName);
            statement.setInt(2, maxStudents);
            statement.setString(3, instructor);
            statement.setInt(4, section);
            statement.setString(5, location);
            statement.setString(6, courseId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Course with ID " + courseId + " updated successfully!");
                dispose(); // Close the frame after update
            } else {
                JOptionPane.showMessageDialog(this, "Course with ID " + courseId + " not found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating course details in the database.");
        }
    }
}
