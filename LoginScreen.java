import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginScreen extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JComboBox<String> roleComboBox;

    public LoginScreen() {
        setTitle("Elective Course Registration System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the frame

        // Background Image
        JLabel backgroundLabel = new JLabel(new ImageIcon("img2.jpg"));
        backgroundLabel.setLayout(new BorderLayout()); // Use BorderLayout for main panel positioning

        // Transparent Main Panel
        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        mainPanel.setOpaque(false); // Make the panel transparent
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30)); // Padding around the panel

        // Title JLabel
        JLabel titleLabel = new JLabel("Elective Course Registration System", JLabel.CENTER);
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // UI Components
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setForeground(Color.BLACK);
        roleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        roleComboBox = new JComboBox<>(new String[]{"Admin", "Student"});
        roleComboBox.setBackground(new Color(230, 230, 230)); // Light background
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        roleComboBox.setPreferredSize(new Dimension(150, 30));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        usernameField = new JTextField();
        usernameField.setBackground(new Color(230, 230, 230));
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(0, 153, 204), 1));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));

        passwordField = new JPasswordField();
        passwordField.setBackground(new Color(230, 230, 230));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(0, 153, 204), 1));

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 153, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.addActionListener(new LoginAction());

        // Add components to main panel
        mainPanel.add(titleLabel);
        mainPanel.add(new JLabel()); // Placeholder for layout alignment
        mainPanel.add(roleLabel);
        mainPanel.add(roleComboBox);
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(new JLabel()); // Placeholder for layout alignment
        mainPanel.add(loginButton);

        // Add main panel to the background label
        backgroundLabel.add(mainPanel, BorderLayout.CENTER);

        // Add the background label to the frame
        setContentPane(backgroundLabel);
        setVisible(true);
    }

    private class LoginAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String role = (String) roleComboBox.getSelectedItem();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setString(3, role);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    if (role.equals("Admin")) {
                        new AdminDashboard(); // Assuming AdminDashboard exists
                    } else if (role.equals("Student")) {
                        new StudentDashboard(username); // Pass username to StudentDashboard
                    }
                    dispose(); // Close login screen
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login credentials.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error connecting to the database.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginScreen::new);
    }
}
