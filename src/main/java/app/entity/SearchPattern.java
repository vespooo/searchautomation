package app.entity;

public class SearchPattern {
    private URLPattern urlPattern;
    private MindedPattern minedPattern;

    public URLPattern getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(URLPattern urlPattern) {
        this.urlPattern = urlPattern;
    }

    public MindedPattern getMinedPattern() {
        return minedPattern;
    }

    public void setMinedPattern(MindedPattern minedPattern) {
        this.minedPattern = minedPattern;
    }
}
