package DB;

import ObjectsProject.Location;
import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class JDBCLocation {
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
    public static Location checkifexists(Integer article) {
        if (article != null) {
            String sqluni = "SELECT alley, gondol, element FROM location WHERE  article=" + article;
            Location result = new Location();
            try {
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sqluni);
                if (rs!=null) {
                    result.setAlley(rs.getInt("alley"));
                    result.setGondol(rs.getInt("gondol"));
                    result.setElement(rs.getInt("element"));
                }
                rs.close();
                return result;

            } catch (SQLException e) {

                return null;
            }
        }
        else return null;
    }

    public static Location checkForUnknown(Integer segment, Integer family, Integer category){

            String sqluni2 = "SELECT alley, gondol, element FROM location WHERE  segment="+segment+" AND category="+category+" AND family="+family;
            List<Location>  locations = new ArrayList<>();

            try {
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sqluni2);
                while (rs.next()) {
                    Location result = new Location();
                    result.setAlley(rs.getInt("alley"));
                    result.setGondol(rs.getInt("gondol"));
                    result.setElement(rs.getInt("element"));
                    if (!result.getAlley().equals(0)) {
                        locations.add(result);
                    }
                }
                rs.close();

            } catch (SQLException e) {
                e.printStackTrace();

            }
        Location result = findLocationForEmpty(locations);
            if (result == null) {
                result.setAlley(0);
                result.setGondol(0);
                result.setElement(0);
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

    private static Location findLocationForEmpty(List<Location> inp) {
        Location extra = new Location();
        List <Location> locations = inp;
        Integer alley = getMaxFromAlley(locations);
        Integer gondol = getMaxFromGondol(locations, alley);
        Integer element = getMaxFromElement(locations, alley, gondol);
        extra.setAlley(alley);
        extra.setGondol(gondol);
        extra.setElement(element);
        return extra;
    }

    private static Integer getMaxFromAlley(List<Location> locations){
        HashMap<Integer,Integer> search = new HashMap<>();
        Integer max = 0;
        Integer adress = 0;
        Iterator it = locations.iterator();
        while (it.hasNext()){
            Location next = (Location) it.next();
            Integer alley = next.getAlley();
            if (search.containsKey(alley)){
                Integer value = search.get(alley);
                search.replace(alley, value, ++value);
            }
            else {
                search.put(alley, 0);
            }
        }
        Iterator it2 = search.keySet().iterator();
        while (it2.hasNext()) {
            Integer adr = (Integer) it2.next();
            if (search.get(adr) != null) {
                Integer val = search.get(adr);

                if (val > max) {
                    max = val;
                    adress = adr;
                }
            }
        }
        return adress;
    }


    private static Integer getMaxFromGondol(List<Location> locations, Integer alleyy){
        HashMap <Integer,Integer> search = new HashMap<>();
        Integer max = 0;
        Integer adress = 0;
        Iterator it = locations.iterator();
        while (it.hasNext()) {
            Location next = (Location) it.next();
            if (next.getAlley().equals(alleyy)) {
                Integer gondol = next.getGondol();
                if (search.containsKey(gondol)) {
                    Integer value = search.get(gondol);
                    search.replace(gondol, value, ++value);
                } else {
                    search.put(gondol, 0);
                }
            }
        }
        Iterator it2 = search.keySet().iterator();
        while (it2.hasNext()){
            Integer adr = (Integer) it2.next();
            Integer val = search.get(adr);
            if (val!=null && val>max){
                max = val;
                adress = adr;
            }
        }
        return adress;
    }

    private static Integer getMaxFromElement(List<Location> locations, Integer alleyy, Integer gondol){
        HashMap <Integer,Integer> search = new HashMap<>();
        Integer max = 0;
        Integer adress = 0;
        Iterator it = locations.iterator();
        while (it.hasNext()) {
            Location next = (Location) it.next();
            if (next.getAlley().equals(alleyy) && next.getGondol().equals(gondol)) {
                Integer element = next.getElement();
                if (search.containsKey(element)) {
                    Integer value = search.get(element);
                    search.replace(gondol, value, ++value);
                } else {
                    search.put(element, 0);
                }
            }
        }
        Iterator it2 = search.keySet().iterator();
        while (it2.hasNext()){
            Integer adr = (Integer) it2.next();
            Integer val = search.get(adr);
            if (val!=null && val>max){
                max = val;
                adress = adr;
            }
        }
        return adress;
    }
}
