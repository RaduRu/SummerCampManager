package Orm;
import java.sql.*;

public class ConnectionManager {
    private static final String url = "jdbc:postgresql://localhost:5432/UrsulottiDB";
    private static final String username = "Radu";
    private static final String password = "Moldovean2001";
    private static Connection con = null;

    private ConnectionManager(){}

    static public Connection getConnection() throws SQLException {
        if (con == null)
            con = DriverManager.getConnection(url, username, password);

        return con;
    }
}

