package app.entity;

import java.util.List;

public class AnalyzePattern {
    String url;
    String searchWord;
    List<String> minedWords;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public List<String> getMinedWords() {
        return minedWords;
    }

    public void setMinedWords(List<String> minedWords) {
        this.minedWords = minedWords;
    }
}
