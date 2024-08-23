package services;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties config = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }
            config.load(input);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to load configuration properties", ex);
        }
    }

    public static String getProperty(String key) {
        return config.getProperty(key);
    }
}

