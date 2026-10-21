import java.sql.*;
public class DBConnection {
    static String password = "Pavani@999";
    static String url = "jdbc:mysql://localhost:3306/hrms_db";
    static String user = "root";
    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }
}