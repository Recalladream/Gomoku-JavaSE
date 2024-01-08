import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConifg {
    private static Connection con;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String url=ConfigFile.getProperty("database.url");
            String name=ConfigFile.getProperty("database.name");
            String password=ConfigFile.getProperty("database.password");

            con= DriverManager.getConnection(url,name,password);
        }catch (Exception e){e.printStackTrace();}
    }

    public static Connection getConnection(){
        return con;
    }
}
