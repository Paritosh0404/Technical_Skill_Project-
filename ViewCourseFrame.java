import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewCourseFrame extends JFrame {
    private JTable coursesTable;
    private JScrollPane scrollPane;

    public ViewCourseFrame() {
        setTitle("View All Courses");
        setSize(900, 300);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Column names for the table
        String[] columnNames = {"Course ID", "Course Name", "Instructor", "Location", "Max Students", "Section"};

        // Initialize table with no data initially
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        coursesTable = new JTable(tableModel);
        coursesTable.setFillsViewportHeight(true);

        // Add table to a scroll pane
        scrollPane = new JScrollPane(coursesTable);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch and populate data from database
        fetchCourseData(tableModel);

        // Add a back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose()); // Close the current frame and return to the previous screen

        // Add the back button at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void fetchCourseData(DefaultTableModel tableModel) {
        String query = "SELECT course_id, course_name, instructor, location, max_students, section FROM courses";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Loop through the result set and add rows to the table model
            while (resultSet.next()) {
                String courseId = resultSet.getString("course_id");
                String courseName = resultSet.getString("course_name");
                String instructor = resultSet.getString("instructor");
                String location = resultSet.getString("location");
                int maxStudents = resultSet.getInt("max_students");
                int section = resultSet.getInt("section");

                // Add row to the table model
                tableModel.addRow(new Object[]{courseId, courseName, instructor, location, maxStudents, section});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching course data from the database.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewCourseFrame::new);
    }
}
