import java.sql.*;
import java.util.*;

public class Record
{

    public Record(Connection con)
    {
        stmt = null;
        this.con = con;
    }

    void addRecord(String tableName, Map columnsAndValues)
        throws SQLException
    {
        Iterator it1 = columnsAndValues.keySet().iterator();
        Iterator it2 = columnsAndValues.keySet().iterator();
        String sql = (new StringBuilder("insert into ")).append(tableName).append(" (").toString();
        while(it1.hasNext()) 
        {
            String columnName = (String)it1.next();
            if(it1.hasNext())
                sql = (new StringBuilder(String.valueOf(sql))).append(columnName).append(", ").toString();
            else
                sql = (new StringBuilder(String.valueOf(sql))).append(columnName).toString();
        }
        sql = (new StringBuilder(String.valueOf(sql))).append(") values (").toString();
        while(it2.hasNext()) 
        {
            String columnName = (String)it2.next();
            if(it2.hasNext())
                sql = (new StringBuilder(String.valueOf(sql))).append("\"").append((String)columnsAndValues.get(columnName)).append("\"").append(", ").toString();
            else
                sql = (new StringBuilder(String.valueOf(sql))).append("\"").append((String)columnsAndValues.get(columnName)).append("\"").toString();
        }
        sql = (new StringBuilder(String.valueOf(sql))).append(")").toString();
        stmt = con.createStatement();
        stmt.executeUpdate(sql);
        con.close();
    }

    void removeRecord(String tableName, Map columnsAndValues)
        throws SQLException
    {
        Iterator it = columnsAndValues.keySet().iterator();
        String sql = (new StringBuilder("delete from ")).append(tableName).append(" where ").toString();
        while(it.hasNext()) 
        {
            String columnName = (String)it.next();
            if(it.hasNext())
                sql = (new StringBuilder(String.valueOf(sql))).append(columnName).append(" = ").append((String)columnsAndValues.get(columnName)).append("\"").append(" AND ").toString();
            else
                sql = (new StringBuilder(String.valueOf(sql))).append(columnName).append(" = \"").append((String)columnsAndValues.get(columnName)).append("\"").toString();
        }
        stmt = con.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
    }

    Vector<Vector<String>> getAllRecords(String tableName)
        throws SQLException
    {
        Vector<Vector<String>> records = new Vector<Vector<String>>();
        stmt = con.createStatement();
        String sql = (new StringBuilder("select * from ")).append(tableName).toString();
        ResultSet rs;
        Vector<String> tmp;
        for(rs = stmt.executeQuery(sql); rs.next(); records.add(tmp))
        {
            tmp = new Vector<String>();
            for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                tmp.add(rs.getString(i));

        }

        rs.close();
        stmt.close();
        return records;
    }

    private Statement stmt;
    private Connection con;
}