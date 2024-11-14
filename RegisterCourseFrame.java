import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterCourseFrame extends JFrame {
    private String username;
    private JTextField courseIdField;

    public RegisterCourseFrame(String username) {
        this.username = username;
        setTitle("Register for Course");
        setSize(400, 150); // Increased size for better arrangement
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Padding around components
        Font labelFont = new Font("Arial", Font.PLAIN, 25);
        Font fieldFont = new Font("Arial", Font.PLAIN, 25);
        Color buttonColor = new Color(0, 204, 255);

        // Course ID Label and Field
        JLabel courseIdLabel = new JLabel("Course ID:");
        courseIdLabel.setFont(labelFont);
        courseIdField = new JTextField();
        courseIdField.setFont(fieldFont);

        // Set GridBagLayout for the Course ID Label and Field
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        add(courseIdLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(courseIdField, gbc);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 25));
        registerButton.setBackground(buttonColor);
        registerButton.addActionListener(e -> registerCourse());

        // Set GridBagLayout for Register Button
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        add(registerButton, gbc);

        setVisible(true);
    }

    private void registerCourse() {
        String courseId = courseIdField.getText();
        if (courseId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a course ID.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO registrations (username, course_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, courseId);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Successfully registered for course " + courseId);
                dispose();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: Could not register for course.");
        }
    }
}
