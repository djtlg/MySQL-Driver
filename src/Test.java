//import java.awt.Dimension;
//import java.sql.SQLException;
//
//public class Test
//{
//
//    public Test()
//    {
//    }
//
//    public static void main(String args[])
//    {
//        try
//        {
//            ConnectionManager conMgr = new ConnectionManager("localhost", "3306");
//            java.sql.Connection con = conMgr.getConnection("root", "123456");
//            char a[] = {
//                '1', '2', '3', '4', '5', '6'
//            };
//            Database db = new Database(con);
//            db.selectDB("sakila");
//            Table tb = new Table(con);
//            Record record = new Record(con);
//        }
//        catch(ClassNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//        catch(SQLException e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    private static Dimension PREFERRED_SIZE = new Dimension(500, 400);
//
//}