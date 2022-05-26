package first.properties;

public class Properties {
    private final String patternFilePath;
    private final String sourceFolder;
    private String outputFolder;
    private final String outputFilePrefix;
    private final String illegalSymbol;
    private final String illegalSymbolValue;

    public Properties(String patternFilePath, String sourceFolder, String outputFolder, String outputFilePrefix,
                      String illegalSymbol, String illegalSymbolValue) {
        this.patternFilePath = patternFilePath;
        this.sourceFolder = sourceFolder;
        this.outputFolder = outputFolder;
        this.outputFilePrefix = outputFilePrefix;
        this.illegalSymbol = illegalSymbol;
        this.illegalSymbolValue = illegalSymbolValue;
    }

    public String getPatternFilePath() {
        return patternFilePath;
    }

    public String getSourceFolder() {
        return sourceFolder;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public String getOutputFilePrefix() {
        return outputFilePrefix;
    }

    public String getIllegalSymbol() {
        return illegalSymbol;
    }

    public String getIllegalSymbolValue() {
        return illegalSymbolValue;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }
}
