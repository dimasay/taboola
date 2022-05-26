package third;

import third.properties.Properties;
import third.properties.PropertiesReader;
import third.utils.FileUtils;

import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties properties = PropertiesReader.read();
        WordsCounter wordsCounter = new WordsCounter(properties);
        wordsCounter.load("File1.txt", "File2.txt", "File3.txt");

        wordsCounter.displayStatus();
    }
}
