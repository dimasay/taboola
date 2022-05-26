import first.Transformator;
import first.TransformatorImpl;
import first.properties.Properties;
import first.properties.PropertiesReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TransformatorTest {
    private final static String INPUT_FILE = "first/input_Q1a .txt";
    private final String OUTPUT_FILE = "transformed_input_Q1a .txt";
    private final String OUTPUT_FILE_STANDART = "Q1a_standard.txt";

    private String INPUT_FILE_WITH_ILLEGAL_NUMERALS = "first/input_Q1b .txt";
    private final String OUTPUT_FILE_WITH_ILLEGAL_NUMERALS = "transformed_input_Q1b .txt";
    private final String OUTPUT_FILE_WITH_ILLEGAL_NUMERALS_STANDART = "Q1b_standard.txt";
    private final static String OUTPUT_FOLDER = "src/test/output";
    private Transformator transformator;

    @BeforeEach
    public void init() throws IOException {
        Properties properties = PropertiesReader.read();
        properties.setOutputFolder(OUTPUT_FOLDER);
        transformator = new TransformatorImpl(properties);
    }


    @Test
    public void transformFile() throws Exception {
        transformFile(INPUT_FILE, OUTPUT_FILE, OUTPUT_FILE_STANDART);
    }

    @Test
    public void transformFileWithIllegalNumerals() throws Exception {
        transformFile(INPUT_FILE_WITH_ILLEGAL_NUMERALS, OUTPUT_FILE_WITH_ILLEGAL_NUMERALS, OUTPUT_FILE_WITH_ILLEGAL_NUMERALS_STANDART);
    }

    private void transformFile(String inputFile, String outputFile, String outputFileStrandart) throws Exception {
        transformator.transformFile(inputFile);
        assertEquals(
                readFile(getFilePath(outputFile)),
                readFile(getFilePath(outputFileStrandart))
        );
    }

    public static String readFile(Path path) throws IOException {
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    private static Path getFilePath(String fileName) {
        var outputFolderURI = URI.create(
                String.format("%s/%s", System.getProperty("user.dir"), OUTPUT_FOLDER));
        var filePath = String.format("%s/%s", outputFolderURI, fileName);

        return Paths.get(filePath);
    }
}
