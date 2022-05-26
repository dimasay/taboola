import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import third.WordsCounter;
import third.properties.Properties;
import third.properties.PropertiesReader;
import third.utils.FileUtils;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordsCounterTest {
    private WordsCounter wordsCounter;
    private static final String testFile1 = "File1.txt";
    private static final String testFile2 = "File2.txt";
    private static final String testFile3 = "File3.txt";
    private static final String standardFile = "words/standard.txt";


    @BeforeEach
    public void init() {
        Properties properties = PropertiesReader.read();
        wordsCounter = new WordsCounter(properties);
    }

    @Test
    public void load() throws ExecutionException, InterruptedException {
        var result = wordsCounter.load(testFile1, testFile2, testFile3);
        var standard = FileUtils.getDataFromFile(standardFile);

        assertEquals(result.toString(), standard);
    }
}
