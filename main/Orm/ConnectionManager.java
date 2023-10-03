package main.Orm;
import java.sql.*;

public class ConnectionManager {

    private static final String url = "jdbc:postgresql://dpg-ckdssj4iibqc73b7v230-a.frankfurt-postgres.render.com/ursulottidb";
    private static final String username = "ursulottidb_user";
    private static final String password = "UYhNPsC173ftFSelsjjSLQq7kVs6z2uD";
    private static Connection con = null;

    private ConnectionManager(){}

    static public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        if (con == null)
            con = DriverManager.getConnection(url, username, password);

        return con;
    }
}

