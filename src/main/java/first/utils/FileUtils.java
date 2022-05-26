package first.utils;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class FileUtils {

    public static <R> List<R> getDataFromFile(String fileName, int lineHeight, int skip,
                                              Function<List<String>, R> function) throws IOException {

        var inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        var reader = new BufferedReader(new InputStreamReader(inputStream));

        List<R> result = new ArrayList<>();
        //We collect a *lineHeight* amount of lines to be processed by function.
        List<String> lineForFunction = new ArrayList<>();
        var startLineNumber = 0;
        var line = "";
        var lineNumber = startLineNumber;
        while (line != null)
            if (lineNumber == lineHeight) {
                result.add(function.apply(lineForFunction));
                lineForFunction.clear();
                //if skip > 0 then we skip as many lines as requested
                lineNumber = startLineNumber - skip;
            } else {
                line = reader.readLine();
                if (lineNumber > -1)
                    lineForFunction.add(line);
                lineNumber++;
            }

        return result;
    }


    public static void saveDataToFile(String fileName, List<String> fileData, String outputFolder) throws FileNotFoundException {
        var outputFolderURI = URI.create(String.format("%s/%s", System.getProperty("user.dir"), outputFolder));
        var outputFilePath = String.format("%s/%s", outputFolderURI, fileName);

        var pw = new PrintWriter(new FileOutputStream(outputFilePath));
        fileData.forEach(pw::println);
        pw.close();
    }
}
