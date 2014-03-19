import java.sql.*;

public class ConnectionManager
{

    public ConnectionManager(String host, String port)
        throws ClassNotFoundException
    {
        driverName = "com.mysql.jdbc.Driver";
        ConnectionManager.host = "jdbc:mysql://" + host + ":" + port;
        Class.forName(driverName);
    }

    public Connection getConnection(String username, String password)
        throws SQLException
    {

        conn = DriverManager.getConnection(host, username, password);
        return conn;
    }

    public void closeConnection()
        throws SQLException
    {
        conn.close();
    }

    private static String driverName;
    private static String host;
    private static Connection conn;
}