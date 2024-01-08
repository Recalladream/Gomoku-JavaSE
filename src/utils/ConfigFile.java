import java.io.InputStream;
import java.util.Properties;

public class ConfigFile {
    private static Properties properties;

    static {
        try {
            InputStream input = ConfigFile.class.getClassLoader().getResourceAsStream("config.properties");
            properties = new Properties();
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
