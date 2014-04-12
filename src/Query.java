import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.sql.*;

/**
 * 
 * @author Tolga ILDIZ, Volkan ALCIN
 * 
 *         Query class handles the operatins that is related to custom queries.
 * 
 */
public class Query {

	private Connection con;
	private Statement stmt;
	private Vector<Vector<String>> queryResults;
	private Vector<String> queryColumnNames;

	Query(Connection con) {
		this.con = con;
		stmt = null;
	}

	/**
	 * Creates a query to be run on the database.
	 * 
	 * @param query
	 *            The custom query that user wants to run on the database.
	 * @throws SQLException
	 */
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

	/**
	 * Returns the results of the custom query.
	 * 
	 * @return A vector that contains the results of the query.
	 */
	Vector<Vector<String>> getQueryResults() {
		return queryResults;
	}

	/**
	 * The names of the columns that is used in the query.
	 * 
	 * @return A vector that contains the column names for the custom query.
	 */
	Vector<String> getQueryColumnNames() {
		return queryColumnNames;
	}
}
