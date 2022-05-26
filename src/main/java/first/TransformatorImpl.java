package first;

import first.properties.Properties;
import first.utils.FileUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransformatorImpl implements Transformator {
    private static final int NUMERAL_WIDTH = 3;
    private static final int SPACE_BETWEEN_NUMERALS = 1;
    private List<Integer> patternHashCodes;
    private final Properties properties;

    public TransformatorImpl(Properties properties) throws IOException {
        this.properties = properties;
        loadPatternHashCodes();
    }

    @Override
    public void transformFiles() {
        getAllFileNamesFromResource().forEach(this::transformFile);
    }

    @Override
    public boolean transformFile(String fileName) {
        try {
            var fileData = FileUtils.getDataFromFile(fileName, NUMERAL_WIDTH, SPACE_BETWEEN_NUMERALS, this::transformLine);
            var splitFileName = fileName.split("/");
            var outputFileName = properties.getOutputFilePrefix() + splitFileName[splitFileName.length - 1];
            FileUtils.saveDataToFile(outputFileName, fileData, properties.getOutputFolder());
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private List<String> getAllFileNamesFromResource() {
        var resource = getClass().getClassLoader().getResource(properties.getSourceFolder());
        List<String> fileNames;

        try (var files = Files.walk(Paths.get(resource.toURI()))) {
            fileNames = files
                    .filter(Files::isRegularFile)
                    .map(this::getFileName)
                    .collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return fileNames;
    }

    private String getFileName(Path path) {
        var parts = path.toUri().getPath().split("/");
        return String.format("%s/%s", parts[parts.length - 2], parts[parts.length - 1]);
    }

    private String transformLine(List<String> textFromFile) {
        var lineHashCodes = getNumeralHashCodes(textFromFile);
        var numerals = new StringBuilder();
        var hasIllegalSymbols = false;

        for (var hashCode : lineHashCodes) {
            var numeral = properties.getIllegalSymbolValue();
            var isIllegal = true;
            for (var i = 0; i < patternHashCodes.size(); i++)
                if (hashCode.equals(patternHashCodes.get(i))) {
                    numeral = String.valueOf(i);
                    isIllegal = false;
                    break;
                }

            if (!hasIllegalSymbols)
                hasIllegalSymbols = isIllegal;

            numerals.append(numeral);
        }
        if (hasIllegalSymbols)
            numerals.append(properties.getIllegalSymbol());

        return numerals.toString();
    }

    private void loadPatternHashCodes() throws IOException {
        patternHashCodes = FileUtils.getDataFromFile(properties.getPatternFilePath(), NUMERAL_WIDTH, SPACE_BETWEEN_NUMERALS, this::getNumeralHashCodes)
                .get(0);
    }

    private List<Integer> getNumeralHashCodes(List<String> line) {
        //Every number line consist of 3 lines. That means I can get length of first line, and it will be equals to another
        var lineLength = line.get(0).length();
        List<Integer> hashCodes = new ArrayList<>();

        for (var col = 0; col < lineLength; col = col + NUMERAL_WIDTH)
            hashCodes.add(getNumeralHashCode(line, col));

        return hashCodes;
    }

    private int getNumeralHashCode(List<String> line, int col) {
        var numeral = new StringBuilder();
        for (var row = 0; row < NUMERAL_WIDTH; row++)
            numeral.append(line.get(row), col, col + NUMERAL_WIDTH);

        return numeral.toString().hashCode();
    }
}
