package third.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesReader {
    public static third.properties.Properties read() {
        var rootPath = Paths.get("src", "main", "resources");
        var appConfigPath = rootPath + "/application.properties";
        var properties = new Properties();

        try {
            properties.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new third.properties.Properties(properties.getProperty("words_source_folder"));
    }

}
