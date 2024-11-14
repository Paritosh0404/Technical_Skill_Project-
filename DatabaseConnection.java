import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3307/course_registration_system";
        String user = "root"; // replace with your MySQL username
        String password = "root"; // replace with your MySQL password

        return DriverManager.getConnection(url, user, password);
    }
}
