import javax.swing.*;
import java.awt.*;

class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("Admin Dashboard - Elective Course Registration System");
        setSize(600, 400); // Smaller initial size
        setLocationRelativeTo(null); // Center the frame on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10)); // Padding between buttons

        // Font and color settings for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 18); // Reduced font size for compact look
        Color buttonColor = new Color(106, 226, 222); // Light blue background
        Color textColor = Color.BLACK;

        // Create buttons with consistent styling
        JButton createCourseButton = createStyledButton("Create Course", buttonFont, buttonColor, textColor);
        JButton deleteCourseButton = createStyledButton("Delete Course", buttonFont, buttonColor, textColor);
        JButton editCourseButton = createStyledButton("Edit Course", buttonFont, buttonColor, textColor);
        JButton viewCoursesButton = createStyledButton("View All Courses", buttonFont, buttonColor, textColor);
        JButton logoutButton = createStyledButton("Logout", buttonFont, new Color(255, 102, 102), Color.WHITE); // Light red for logout

        // Action listeners for buttons
        createCourseButton.addActionListener(e -> new CreateCourseFrame());
        deleteCourseButton.addActionListener(e -> new DeleteCourseFrame());
        editCourseButton.addActionListener(e -> new EditCourseFrame());
        viewCoursesButton.addActionListener(e -> new ViewCourseFrame());
        logoutButton.addActionListener(e -> {
            new LoginScreen();
            dispose(); // Close admin dashboard
        });

        // Add buttons to the frame
        add(createCourseButton);
        add(deleteCourseButton);
        add(editCourseButton);
        add(viewCoursesButton);
        add(logoutButton);

        setVisible(true);
    }

    // Helper method to create styled buttons
    private JButton createStyledButton(String text, Font font, Color bgColor, Color textColor) {
        JButton button = new JButton(text);
        button.setFont(font); // Apply compact font size
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFocusPainted(false); // Removes focus border on click
        button.setOpaque(true); // Ensures color is applied on all platforms
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Padding inside button
        button.setPreferredSize(new Dimension(200, 40)); // Adjusted button size
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminDashboard::new);
    }
}
