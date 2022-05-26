package first.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesReader {
    public static first.properties.Properties read() {
        var rootPath = Paths.get("src", "main", "resources");
        var appConfigPath = rootPath + "/application.properties";
        var properties = new Properties();

        try {
            properties.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var patternFilePath = properties.getProperty("pattern_file");
        var sourceFolder = properties.getProperty("source_folder");
        var outputFolder = properties.getProperty("output_folder");
        var outputFilePrefix = properties.getProperty("output_file_prefix");
        var illegalSymbol = properties.getProperty("illegal_symbol");
        var illegalSymbolValue = properties.getProperty("illegal_symbol_value");

        return new first.properties.Properties(patternFilePath, sourceFolder, outputFolder, outputFilePrefix, illegalSymbol, illegalSymbolValue);
    }
}
