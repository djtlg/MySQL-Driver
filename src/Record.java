import java.sql.*;
import java.util.*;

/**
 * 
 * @author Tolga ILDIZ, Volkan Alcin
 * 
 *         Record class includes the necessary operations for record
 *         management on the table level. It can be used to add, remove, and
 *         display the rows of a table in the SQL system.
 * 
 */
public class Record {

	public Record(Connection con) {
		stmt = null;
		this.con = con;
	}

	/**
	 * Adds a new row of data to an existing table.
	 * @param tableName A string that contains the name of the table that the row will be added to.
	 * @param columnsAndValues A LinkedHashMap that contains the names of the columns and the their values.
	 * 		<K,V> K - The name of the column. V - The data data that belongs to the column.
	 * @throws SQLException
	 */
	void addRecord(String tableName,
			LinkedHashMap<String, String> columnsAndValues) throws SQLException {
		Iterator<String> it1 = columnsAndValues.keySet().iterator();
		Iterator<String> it2 = columnsAndValues.keySet().iterator();
		String sql = (new StringBuilder("insert into ")).append(tableName)
				.append(" (").toString();
		while (it1.hasNext()) {
			String columnName = (String) it1.next();
			if (it1.hasNext())
				sql = (new StringBuilder(String.valueOf(sql)))
						.append(columnName).append(", ").toString();
			else
				sql = (new StringBuilder(String.valueOf(sql))).append(
						columnName).toString();
		}
		sql = (new StringBuilder(String.valueOf(sql))).append(") values (")
				.toString();
		while (it2.hasNext()) {
			String columnName = (String) it2.next();
			if (it2.hasNext())
				sql = (new StringBuilder(String.valueOf(sql))).append("\"")
						.append((String) columnsAndValues.get(columnName))
						.append("\"").append(", ").toString();
			else
				sql = (new StringBuilder(String.valueOf(sql))).append("\"")
						.append((String) columnsAndValues.get(columnName))
						.append("\"").toString();
		}
		sql = (new StringBuilder(String.valueOf(sql))).append(")").toString();
		stmt = con.createStatement();
		stmt.executeUpdate(sql);
	}

	/**
	 * Removes a record from an existing table.
	 * @param tableName A string that contains the name of the table that the row will be removed from.
	 * @param columnsAndValues columnsAndValues A LinkedHashMap that contains the names of the columns and the their values.
	 * 		<K,V> K - The name of the column. V - The data data that belongs to the column.
	 * @throws SQLException
	 */
	void removeRecord(String tableName,
			LinkedHashMap<String, String> columnsAndValues) throws SQLException {
		Iterator<String> it = columnsAndValues.keySet().iterator();
		String sql = (new StringBuilder("delete from ")).append(tableName)
				.append(" where ").toString();
		while (it.hasNext()) {
			String columnName = (String) it.next();
			if (it.hasNext())
				sql = (new StringBuilder(String.valueOf(sql)))
						.append(columnName).append(" = \"")
						.append((String) columnsAndValues.get(columnName))
						.append("\"").append(" AND ").toString();
			else
				sql = (new StringBuilder(String.valueOf(sql)))
						.append(columnName).append(" = \"")
						.append((String) columnsAndValues.get(columnName))
						.append("\"").toString();
		}
		stmt = con.createStatement();
		stmt.executeUpdate(sql);
		stmt.close();
	}

	/**
	 * Returns all the records from an exisitng table.
	 * @param A string that contains the name of the table from which the data will be taken.
	 * @return A Vector that contains the data.
	 * @throws SQLException
	 */
	Vector<Vector<String>> getAllRecords(String tableName) throws SQLException {
		Vector<Vector<String>> records = new Vector<Vector<String>>();
		stmt = con.createStatement();
		String sql = (new StringBuilder("select * from ")).append(tableName)
				.toString();
		ResultSet rs;
		Vector<String> tmp;
		for (rs = stmt.executeQuery(sql); rs.next(); records.add(tmp)) {
			tmp = new Vector<String>();
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
				tmp.add(rs.getString(i));

		}
		rs.close();
		stmt.close();
		return records;
	}

	private Statement stmt;
	private Connection con;
}