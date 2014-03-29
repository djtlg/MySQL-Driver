import java.sql.*;
import java.util.*;

public class Table {

	public Table(Connection con) {
		stmt = null;
		this.con = con;
	}

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
							.append(columns.get(columnName))
							.append(", ").toString();
				else
					sql = (new StringBuilder(String.valueOf(sql)))
							.append(columnName).append(" ")
							.append(columns.get(columnName))
							.toString();
			}
			sql = (new StringBuilder(String.valueOf(sql))).append(")")
					.toString();
			stmt.executeUpdate(sql);
			stmt.close();
		}
	}

	void removeTable(String tableName) throws SQLException {
		stmt = con.createStatement();
		String sql = (new StringBuilder("drop table ")).append(tableName)
				.toString();
		stmt.executeUpdate(sql);
		stmt.close();
	}

	ArrayList<String> getTables() throws SQLException {
		ArrayList<String> tables = new ArrayList<String>();
		DatabaseMetaData md = con.getMetaData();
		for (ResultSet rs = md.getTables(null, null, "%", null); rs.next(); tables
				.add(rs.getString(3)))
			;
		return tables;
	}

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

	void renameColumn(String tableName, String currentName, String newName,
			String dataType) throws SQLException {
		String sql = (new StringBuilder("alter table ")).append(tableName)
				.append(" change ").append(currentName).append(" ")
				.append(newName).append(" ").append(dataType).toString();
		stmt = con.createStatement();
		stmt.executeUpdate(sql);
		stmt.close();
	}

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