import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewRegisteredCoursesFrame extends JFrame {
    private String username;
    private JTable coursesTable;
    private JScrollPane scrollPane;

    public ViewRegisteredCoursesFrame(String username) {
        this.username = username;
        setTitle("View Registered Courses");
        setSize(800, 400); // Increased size for better arrangement
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Column names for the table
        String[] columnNames = {"Course ID", "Course Name", "Instructor", "Location"};

        // Initialize the table model
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        coursesTable = new JTable(tableModel);
        coursesTable.setFont(new Font("Arial", Font.PLAIN, 16)); // Font for table text
        coursesTable.setFillsViewportHeight(true); // Ensures the table fills the viewport

        // Fetch and display the registered courses for the user
        fetchRegisteredCourses(tableModel);

        // Add table to a scroll pane
        scrollPane = new JScrollPane(coursesTable);
        add(scrollPane, BorderLayout.CENTER);

        // Add a back button with improved styling
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 25)); // Consistent font for button
        backButton.setBackground(new Color(0, 204, 255)); // Button color
        backButton.setPreferredSize(new Dimension(200, 40)); // Preferred size for the button
        backButton.addActionListener(e -> dispose()); // Close the current frame

        // Panel for the back button to place it at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center align the button
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void fetchRegisteredCourses(DefaultTableModel tableModel) {
        String query = "SELECT c.course_id, c.course_name, c.instructor, c.location " +
                "FROM courses c JOIN registrations r ON c.course_id = r.course_id " +
                "WHERE r.username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            // Loop through the result set and add rows to the table model
            while (resultSet.next()) {
                String courseId = resultSet.getString("course_id");
                String courseName = resultSet.getString("course_name");
                String instructor = resultSet.getString("instructor");
                String location = resultSet.getString("location");

                tableModel.addRow(new Object[]{courseId, courseName, instructor, location});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving registered courses from the database.");
        }
    }

    public static void main(String[] args) {
        // Replace with the actual username or pass it dynamically when creating the frame
        SwingUtilities.invokeLater(() -> new ViewRegisteredCoursesFrame("student01"));
    }
}
