import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteCourseFrame extends JFrame {
    private JTextField courseIdField;
    private JButton deleteButton;

    public DeleteCourseFrame() {
        setTitle("Delete Course");
        setSize(500, 150);
        setLayout(new GridLayout(2, 2, 10, 10)); // Compact layout with padding
        setLocationRelativeTo(null); // Center the frame on screen

        // Font and color settings for label and button
        Font labelFont = new Font("Arial", Font.BOLD, 20);
        Font buttonFont = new Font("Arial", Font.BOLD, 20);
        Color buttonColor = new Color(0, 204, 255); // Consistent button color

        // Course ID label and text field
        JLabel courseIdLabel = new JLabel("Course ID:");
        courseIdLabel.setFont(labelFont);
        courseIdField = new JTextField();
        courseIdField.setFont(labelFont);

        // Delete button with consistent styling
        deleteButton = new JButton("Delete Course");
        deleteButton.setFont(buttonFont);
        deleteButton.setBackground(buttonColor);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setOpaque(true);
        deleteButton.setBorderPainted(false);
        deleteButton.addActionListener(e -> {
            String courseId = courseIdField.getText();

            if (courseId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a course ID.");
                return;
            }

            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "DELETE FROM courses WHERE course_id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, courseId);

                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Course with ID " + courseId + " deleted successfully!");
                    dispose(); // Close the frame after deletion
                } else {
                    JOptionPane.showMessageDialog(this, "Course with ID " + courseId + " not found.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting course from the database.");
            }
        });

        // Adding components to the frame
        add(courseIdLabel);
        add(courseIdField);
        add(new JLabel()); // Empty label for spacing
        add(deleteButton);

        setVisible(true);
    }
}
