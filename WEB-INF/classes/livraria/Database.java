package livraria;

import java.sql.*;
import com.mysql.jdbc.Driver;

public class Database {
    public static String server = "praiseweb.com.br";
    public static int port = 3306;
    public static String database = "claretiano";
    private static String user = "claretiano";
    private static String password = "WKEjGvHmWG3VtZrh";
    private Connection con;
    
    public Database() {
        String url;
        
        try {
            DriverManager.registerDriver(new Driver());
            
            url = "jdbc:mysql://"
                + server + ":" + String.valueOf(port) + "/"
                + database
                + "?user=" + user
                + "&password=" + password;
            
            con = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Connection getConnection() {
        return con;
    }
}
