import java.sql.*;
import java.util.ArrayList;

public class Database
{

    public Database(Connection con)
    {
        stmt = null;
        this.con = con;
    }

    void addDB(String databaseName)
        throws SQLException
    {
        stmt = con.createStatement();
        String sql = (new StringBuilder("create database ")).append(databaseName).toString();
        stmt.executeUpdate(sql);
        stmt.close();
    }

    void removeDB(String databaseName)
        throws SQLException
    {
        stmt = con.createStatement();
        String sql = (new StringBuilder("drop database ")).append(databaseName).toString();
        stmt.executeUpdate(sql);
        stmt.close();
    }

    ArrayList<String> getDBs()
        throws SQLException
    {
        ArrayList<String> dbs = new ArrayList<String>();
        stmt = con.createStatement();
        String sql = "show databases";
        ResultSet rs;
        for(rs = stmt.executeQuery(sql); rs.next(); dbs.add(rs.getString("database")));
        rs.close();
        stmt.close();
        return dbs;
    }

    void selectDB(String databaseName)
        throws SQLException
    {
        con.setCatalog(databaseName);
    }

    private Statement stmt;
    private Connection con;
}