package sk.oracle.usergroup.fn;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
    private static final String DEFAULT_PROPERTIES_FILE = "application.properties";
    private static Properties properties;
    
    static {
        loadProperties();
    }
    
    private static void loadProperties() {
        properties = new Properties();
        try (InputStream inputStream = PropertyLoader.class.getClassLoader()
                .getResourceAsStream(DEFAULT_PROPERTIES_FILE)) {
            
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                System.err.println("Property file '" + DEFAULT_PROPERTIES_FILE + "' not found in the classpath");
            }
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    public static void reloadProperties() {
        loadProperties();
    }
}