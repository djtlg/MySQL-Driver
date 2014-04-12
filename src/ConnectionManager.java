import java.sql.*;

/**
 * 
 * @author Tolga ILDIZ, Volkan ALCIN
 * 
 *         This class handles the connection with the SQL database.
 * 
 */
public class ConnectionManager {

	/**
	 * 
	 * @param host
	 *            the IP or the address of the host that is hosting the SQL
	 *            database.
	 * @param port
	 *            The port number that the SQL database is using to comunicate.
	 * @throws ClassNotFoundException
	 */
	public ConnectionManager(String host, String port)
			throws ClassNotFoundException {
		driverName = "com.mysql.jdbc.Driver";
		ConnectionManager.host = "jdbc:mysql://" + host + ":" + port;
		Class.forName(driverName);
	}

	/**
	 * Connects to the database and returns a connection to the SQL database.
	 * 
	 * @param username
	 * @param password
	 * @return A connection that can be used to for SQL operations.
	 * @throws SQLException
	 */
	public Connection getConnection(String username, String password)
			throws SQLException {

		conn = DriverManager.getConnection(host, username, password);
		return conn;
	}

	/**
	 * Closes the connection to the SQL database.
	 * 
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException {
		conn.close();
	}

	private static String driverName;
	private static String host;
	private static Connection conn;
}