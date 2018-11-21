package DB;

import org.sqlite.JDBC;

import java.sql.*;
import java.util.Iterator;
import java.util.List;

public class JDBCInitializer {
    private static Connection connection;

    private static int lines = 0;
    public static void  connect() {
        connection = null;

        try {
            String url = "jdbc:sqlite:AC.db";
            DriverManager.registerDriver(new JDBC());
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
            System.out.println("connection success");
        } catch (SQLException exception) {
            exception.printStackTrace();

        }
    }
    public static String checkifexists(Integer article){
        String sqluni = "SELECT ean FROM good WHERE  article="+article;
        String result = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqluni);
            result =  rs.getString(1);
            rs.close();
            } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
