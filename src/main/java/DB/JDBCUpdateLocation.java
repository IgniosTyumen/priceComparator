package DB;

import ObjectsProject.Location;
import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUpdateLocation {
    private static Connection connection;

    private static int lines = 0;
    public static void  connect() {
        connection = null;

        try {
            String url = "jdbc:sqlite:location.db";
            DriverManager.registerDriver(new JDBC());
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
            System.out.println("connection success");
        } catch (SQLException exception) {
            exception.printStackTrace();

        }
    }

    private static Location setLocationToDefault(Location location){
        Location result = location;
        if (result.getFamily()==null) result.setFamily(0);
        if (result.getArticle()==null) result.setArticle(0);
        if (result.getSegment()==null) result.setSegment(0);
        if (result.getCategory()==null) result.setCategory(0);
        if (result.getAlley()==null) result.setAlley(0);
        if (result.getGondol()==null) result.setGondol(0);
        if (result.getElement()==null) result.setElement(0);
        return result;
    }
    public static void checkifexists(Location location, int maxLines){
        //language=sql
        location = JDBCUpdateLocation.setLocationToDefault(location);
        String sqluni = "INSERT OR REPLACE INTO location (id,article,segment,category,family,alley,gondol,element) " +
                "VALUES ((SELECT id FROM location WHERE article="+location.getArticle()+"),"+location.getArticle()+","+location.getSegment()+","+location.getCategory()+","+location.getFamily()+","+location.getAlley()+","+location.getGondol()+","+location.getElement()+")";

        try {
            Statement statement = connection.createStatement();
            statement.execute(sqluni);
            System.out.println("Добавление переимплантации"+location.toString());
            lines++;
            if (lines>10_000 || lines==maxLines) {
                System.out.println("10k or all lines prepared");
                connection.commit();
                System.out.println("10k or all lines commitet");
                lines=0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void close(){
        try {
            connection.commit();
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
