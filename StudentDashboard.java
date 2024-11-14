import javax.swing.*;
import java.awt.*;

class StudentDashboard extends JFrame {
    private String username;

    public StudentDashboard(String username) {
        this.username = username;
        setTitle("Student Dashboard - Elective Course Registration System");
        setSize(600, 400); // Smaller initial size
        setLocationRelativeTo(null); // Center the frame on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10)); // Padding between buttons

        // Font and color settings for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 18); // Reduced font size for compact look
        Color buttonColor = new Color(106, 226, 222); // Light blue background
        Color textColor = Color.BLACK;

        // Create buttons with consistent styling
        JButton viewCoursesButton = createStyledButton("View Available Courses", buttonFont, buttonColor, textColor);
        JButton registerCourseButton = createStyledButton("Register for Course", buttonFont, buttonColor, textColor);
        JButton withdrawCourseButton = createStyledButton("Withdraw from Course", buttonFont, buttonColor, textColor);
        JButton viewRegisteredCoursesButton = createStyledButton("View Registered Courses", buttonFont, buttonColor, textColor);
        JButton logoutButton = createStyledButton("Logout", buttonFont, new Color(255, 102, 102), Color.WHITE); // Light red for logout

        // Action listeners for buttons
        viewCoursesButton.addActionListener(e -> new ViewCourseFrame());
        registerCourseButton.addActionListener(e -> new RegisterCourseFrame(username));
        withdrawCourseButton.addActionListener(e -> new WithdrawCourseFrame(username));
        viewRegisteredCoursesButton.addActionListener(e -> new ViewRegisteredCoursesFrame(username));
        logoutButton.addActionListener(e -> {
            new LoginScreen();
            dispose(); // Close student dashboard
        });

        // Add buttons to the frame
        add(viewCoursesButton);
        add(registerCourseButton);
        add(withdrawCourseButton);
        add(viewRegisteredCoursesButton);
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
        SwingUtilities.invokeLater(() -> new StudentDashboard("student1"));
    }
}
