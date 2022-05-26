package first;

import first.properties.Properties;
import first.properties.PropertiesReader;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {
        Properties properties = PropertiesReader.read();
        Transformator transformator = new TransformatorImpl(properties);
        transformator.transformFiles();
    }
}
