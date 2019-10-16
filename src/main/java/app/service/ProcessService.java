package app.service;

import app.entity.AnalyzePattern;
import app.entity.SearchPattern;
import app.search.SearchController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessService {
    private StringParameterMapper stringParameterMapper;
    private SearchController searchController;
    private Extractor extractor;

    @Autowired
    public ProcessService(StringParameterMapper stringParameterMapper, SearchController searchController, Extractor extractor) {
        this.stringParameterMapper = stringParameterMapper;
        this.searchController = searchController;
        this.extractor = extractor;
    }

    public List<String> process(SearchPattern pattern) {
        String url = stringParameterMapper.map(pattern.getUrlPattern().getUrl(), pattern.getUrlPattern().getParams());
        System.out.print(url);
        return extractor.extract(searchController.requestHtml(url) , pattern.getMinedPattern());

    }

    public void analyze(AnalyzePattern pattern, String html) {
        extractor.findStartAndEndMinePattern(pattern.getMinedWords(), html);
    }
}
