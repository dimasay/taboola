package third;

import third.properties.Properties;
import third.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class WordsCounter {
    private final Properties properties;
    private final Map<String, Integer> map;

    public WordsCounter(Properties properties) {
        this.map = new ConcurrentHashMap<>();
        this.properties = properties;
    }

    public Map<String, Integer> load(String... fileNames) throws ExecutionException, InterruptedException {
        map.clear();
        List<CompletableFuture<Map<String, Integer>>> tasks = new ArrayList<>(fileNames.length);

        for (String fileName : fileNames)
            tasks.add(CompletableFuture.supplyAsync(() -> countWords(fileName)));

        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).get();

        return map;
    }

    public void displayStatus() {
        var totalCount = new AtomicInteger();

        map.forEach((word, count) -> {
            System.out.printf("%s\t%s\n", word, count);
            totalCount.getAndIncrement();
        });
    }

    private Map<String, Integer> countWords(String fileName) {
        var fileData = getFileData(fileName);
        var words = prepareData(fileData);
        for (String word : words)
            countWord(word);

        return map;
    }

    private synchronized void countWord(String word) {
        var value = map.putIfAbsent(word, 1);
        if (value != null)
            map.put(word, value + 1);
    }

    private String[] prepareData(String fileData) {
        fileData = fileData.replaceAll("[^a-zA-Z0-9]", " ");
        return fileData.split("\\s+");
    }

    private String getFileData(String fileName) {
        var fullFileName = String.format("%s/%s", properties.getSourceFolder(), fileName);
        return FileUtils.getDataFromFile(fullFileName);
    }
}
