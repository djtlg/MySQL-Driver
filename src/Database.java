import java.sql.*;
import java.util.ArrayList;

/**
 * 
 * @author Tolga ILDIZ, Volkan Alcin
 * 
 *         Database class includes the necessary operations for database
 *         management on the database level. It can be used to add, remove, and
 *         display the databases in the SQL system.
 * 
 */
public class Database {

	public Database(Connection con) {
		stmt = null;
		this.con = con;
	}

	/**
	 * Creates an new and empty database in the host.
	 * 
	 * @param databaseName
	 *            The name for the new database that will be created.
	 * @throws SQLException
	 */
	void addDB(String databaseName) throws SQLException {
		stmt = con.createStatement();
		String sql = (new StringBuilder("create database ")).append(
				databaseName).toString();
		stmt.executeUpdate(sql);
		stmt.close();
	}

	/**
	 * Removes an existing database in the host with all of it's data.
	 * 
	 * @param databaseName
	 *            The name of the database that is being removed.
	 * @throws SQLException
	 */
	void removeDB(String databaseName) throws SQLException {
		stmt = con.createStatement();
		String sql = (new StringBuilder("drop database ")).append(databaseName)
				.toString();
		stmt.executeUpdate(sql);
		stmt.close();
	}

	/**
	 * Returns the names of the databases in the host.
	 * 
	 * @return ArrrayList that has the names of the databases in the host.
	 * @throws SQLException
	 */
	ArrayList<String> getDBs() throws SQLException {
		ArrayList<String> dbs = new ArrayList<String>();
		stmt = con.createStatement();
		String sql = "show databases";
		ResultSet rs;
		for (rs = stmt.executeQuery(sql); rs.next(); dbs.add(rs
				.getString("database")))
			;
		rs.close();
		stmt.close();
		return dbs;
	}

	/**
	 * Chooses the database that is going to be worked on. This function makes
	 * the same operation as "SELECT DATABASE ..."
	 * 
	 * @param databaseName
	 *            The name of the database that is going to be worked on.
	 * @throws SQLException
	 */
	void selectDB(String databaseName) throws SQLException {
		con.setCatalog(databaseName);
	}

	private Statement stmt;
	private Connection con;
}