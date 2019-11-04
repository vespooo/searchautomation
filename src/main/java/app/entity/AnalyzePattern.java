package app.entity;

import java.util.List;

public class AnalyzePattern {
    private String url;
    private List<String> minedWords;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getMinedWords() {
        return minedWords;
    }

    public void setMinedWords(List<String> minedWords) {
        this.minedWords = minedWords;
    }
}
