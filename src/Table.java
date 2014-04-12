import java.sql.*;
import java.util.*;

/**
 * 
 * @author Tolga ILDIZ, Volkan Alcin
 * 
 *         Table class includes the necessary operations for table management on
 *         the table level. It can be used to add, remove, and display the
 *         tables in the database. Also it can add new columns, remove existing
 *         columns modify to and from existing tables.
 * 
 */
public class Table {

	public Table(Connection con) {
		stmt = null;
		this.con = con;
	}

	/**
	 * Adds a new empty table to an existing table.
	 * 
	 * @param tableName
	 *            A string that contains the name of the new table
	 * @param columns
	 *            A LinkedHashMap that contains the column names and types. <K,
	 *            V> K - The name of the column. V - The type of the column.
	 * @throws SQLException
	 */
	void addTable(String tableName, LinkedHashMap<String, String> columns)
			throws SQLException {
		if (!columns.isEmpty() && !tableName.isEmpty()) {
			Iterator<String> it = columns.keySet().iterator();
			stmt = con.createStatement();
			String sql = (new StringBuilder("create table ")).append(tableName)
					.append(" (").toString();
			while (it.hasNext()) {
				String columnName = it.next();
				if (it.hasNext())
					sql = (new StringBuilder(String.valueOf(sql)))
							.append(columnName).append(" ")
							.append(columns.get(columnName)).append(", ")
							.toString();
				else
					sql = (new StringBuilder(String.valueOf(sql)))
							.append(columnName).append(" ")
							.append(columns.get(columnName)).toString();
			}
			sql = (new StringBuilder(String.valueOf(sql))).append(")")
					.toString();
			stmt.executeUpdate(sql);
			stmt.close();
		}
	}

	/**
	 * Removes an existing table with its existing data.
	 * 
	 * @param tableName
	 *            A string that contains the name of the table that will be
	 *            removed.
	 * @throws SQLException
	 */
	void removeTable(String tableName) throws SQLException {
		stmt = con.createStatement();
		String sql = (new StringBuilder("drop table ")).append(tableName)
				.toString();
		stmt.executeUpdate(sql);
		stmt.close();
	}

	/**
	 * Returns names of all tables in the database.
	 * 
	 * @return A ArrayList that contains the names of the tables in the
	 *         database.
	 * @throws SQLException
	 */
	ArrayList<String> getTables() throws SQLException {
		ArrayList<String> tables = new ArrayList<String>();
		DatabaseMetaData md = con.getMetaData();
		for (ResultSet rs = md.getTables(null, null, "%", null); rs.next(); tables
				.add(rs.getString(3)))
			;
		return tables;
	}

	/**
	 * Add new columns to an existing table.
	 * 
	 * @param tableName
	 *            A string that contains the name of the table to which the new
	 *            columns will be added.
	 * @param columns
	 *            A LinkedHashMap that contains the column names and the types
	 *            of the new columns. <K, V> K - The name of the column. V - The
	 *            Type of the column.
	 * @throws SQLException
	 */
	void addColumn(String tableName, LinkedHashMap<String, String> columns)
			throws SQLException {
		Iterator<String> it = columns.keySet().iterator();
		String sql = (new StringBuilder("alter table ")).append(tableName)
				.append(" add (").toString();
		while (it.hasNext()) {
			String columnName = it.next();
			if (it.hasNext())
				sql = (new StringBuilder(String.valueOf(sql)))
						.append(columnName).append(" ")
						.append(columns.get(columnName)).append(", ")
						.toString();
			else
				sql = (new StringBuilder(String.valueOf(sql)))
						.append(columnName).append(" ")
						.append(columns.get(columnName)).toString();
		}
		sql = (new StringBuilder(String.valueOf(sql))).append(")").toString();
		stmt = con.createStatement();
		stmt.executeUpdate(sql);
		stmt.close();
	}

	/**
	 * Changes the data type of a columns.
	 * 
	 * @param tableName
	 *            A string that contains the name of the table on which the
	 *            columns will be modified.
	 * @param columns
	 *            columns A LinkedHashMap that contains the column names and the
	 *            types of the new columns. <K, V> K - The name of the column. V
	 *            - The Type of the column.
	 * @throws SQLException
	 */
	void modifyColumn(String tableName, LinkedHashMap<String, String> columns)
			throws SQLException {
		Iterator<String> it = columns.keySet().iterator();
		while (it.hasNext()) {
			String columnName = it.next();
			String sql = "Alter Table " + tableName + " modify column "
					+ columnName + " " + columns.get(columnName);
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
		}
		stmt.close();
	}

	/**
	 * Removes existing columns from an existing table.
	 * 
	 * @param tableName
	 *            A string that contains the name of the table from which
	 *            columns will be removed.
	 * @param columns
	 *            An ArrayList thay contains the names of the columns that will
	 *            be removed.
	 * @throws SQLException
	 */
	void removeColumn(String tableName, ArrayList<String> columns)
			throws SQLException {
		Iterator<String> it = columns.iterator();
		while (it.hasNext()) {
			String columnName = it.next();
			String sql = "Alter Table " + tableName + " drop column "
					+ columnName;
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
		}
		stmt.close();
	}

	/**
	 * Renames an existing column.
	 * 
	 * @param tableName
	 *            A string that contains the name of the table on which the
	 *            column will be renamed.
	 * @param currentName
	 *            A string that contains the current name of the column.
	 * @param newName
	 *            A string that contains the new name of the column.
	 * @param dataType
	 *            A string that contains the data type of the column.
	 * @throws SQLException
	 */
	void renameColumn(String tableName, String currentName, String newName,
			String dataType) throws SQLException {
		String sql = (new StringBuilder("alter table ")).append(tableName)
				.append(" change ").append(currentName).append(" ")
				.append(newName).append(" ").append(dataType).toString();
		stmt = con.createStatement();
		stmt.executeUpdate(sql);
		stmt.close();
	}

	/**
	 * Returns names of all columns in an existing table.
	 * 
	 * @return A Vector that contains the names of the columns in the table.
	 * @throws SQLException
	 */
	Vector<String> getColumns(String table) throws SQLException {
		Vector<String> columns = new Vector<String>();
		DatabaseMetaData md = con.getMetaData();
		for (ResultSet rs = md.getColumns(null, null, table, null); rs.next(); columns
				.add(rs.getString(4)))
			;
		return columns;
	}

	private Statement stmt;
	private Connection con;
}