//import java.awt.Dimension;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.Vector;
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
////            char a[] = {
////                '1', '2', '3', '4', '5', '6'
////            };
//            Database db = new Database(con);
//            db.selectDB("test");
//            Table tb = new Table(con);
//            LinkedHashMap<String, String> k = new LinkedHashMap<String, String>();
//            k.put("z","int"); k.put("y","int");
//            ArrayList<String> a = new ArrayList<>();
//            a.add("a");
//            a.add("b");
//            tb.addColumn("a", k);
////            tb.removeColumn("a", a);
////            Record record = new Record(con);
////            Query query = new Query(con);
////            query.createQuery("select country,city from city,country where city.country_id = country.country_id");
////            Vector<Vector<String>> map = query.getQueryResults();
////            map.trimToSize();
////            for(int i = 0; i < map.size(); i++ ){
//////            	for (int a = 0; a < map.get(i).size(); a++){
//////            		
//////            	}
////            	System.out.println(map.get(i));
////            }
////            System.out.println(query.getQueryColumnNames());
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