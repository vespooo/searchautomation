package app.entity;

public class SearchPattern {
    private URLPattern urlPattern;
    private MinedPattern minedPattern;

    public URLPattern getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(URLPattern urlPattern) {
        this.urlPattern = urlPattern;
    }

    public MinedPattern getMinedPattern() {
        return minedPattern;
    }

    public void setMinedPattern(MinedPattern minedPattern) {
        this.minedPattern = minedPattern;
    }
}
