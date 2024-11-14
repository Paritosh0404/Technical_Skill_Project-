import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class WithdrawCourseFrame extends JFrame {
    private String username;
    private JTextField courseIdField;

    public WithdrawCourseFrame(String username) {
        this.username = username;
        setTitle("Withdraw from Course");
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

        // Withdraw Button
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setFont(new Font("Arial", Font.PLAIN, 25));
        withdrawButton.setBackground(buttonColor);
        withdrawButton.addActionListener(e -> withdrawCourse());

        // Set GridBagLayout for Withdraw Button
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        add(withdrawButton, gbc);

        setVisible(true);
    }

    private void withdrawCourse() {
        String courseId = courseIdField.getText();
        if (courseId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a course ID:");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM registrations WHERE username = ? AND course_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, courseId);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Successfully withdrawn from course " + courseId);
                dispose();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: Could not withdraw from course.");
        }
    }
}
