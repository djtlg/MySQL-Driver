import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.sql.*;

public class Query {

	private Connection con;
	private Statement stmt;
	private Vector<Vector<String>> queryResults;
	private Vector<String> queryColumnNames;

	Query(Connection con) {
		this.con = con;
		stmt = null;
	}

	void createQuery(String query) throws SQLException {
		queryResults = new Vector<Vector<String>>();
		queryColumnNames = new Vector<String>();
		stmt = con.createStatement();
		ResultSet rs;
		Vector<String> tmp;
		for (rs = stmt.executeQuery(query); rs.next(); queryResults.add(tmp)) {
			tmp = new Vector<String>();
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
				tmp.add(rs.getString(i));
		}
		for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
			queryColumnNames.add(rs.getMetaData().getColumnName(i));
		queryResults.trimToSize();
		rs.close();
		stmt.close();
	}

	Vector<Vector<String>> getQueryResults() {
		return queryResults;
	}

	Vector<String> getQueryColumnNames() {
		return queryColumnNames;
	}
}
