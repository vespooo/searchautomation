package app.service;

import app.entity.MinedPattern;
import app.entity.SearchPattern;
import app.exception.EmptyMinedPatternException;
import app.exception.UncertainMinedPatternException;
import app.restclient.RequestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessService {
    private ParameterMapper parameterMapper;
    private RequestController requestController;
    private Extractor extractor;

    @Autowired
    public ProcessService(ParameterMapper parameterMapper, RequestController requestController, Extractor extractor) {
        this.parameterMapper = parameterMapper;
        this.requestController = requestController;
        this.extractor = extractor;
    }

    /**
     * Constructs requests using {@link app.entity.URLPattern} and {@link ParameterMapper}.
     * Sends requests throw {@link org.springframework.web.client.RestTemplate} to get html page
     * And then extracts a list of phrases between {@link MinedPattern} pattern in the html.
     * @param pattern
     * @return list of mined phrases
     */
    public List<String> extract(SearchPattern pattern) {
        String url = parameterMapper.map(pattern.getUrlPattern());
        String html = requestController.requestHtml(url);
        return extractor.extract(html, pattern.getMinedPattern());

    }

    /**
     * According to short list of phrases, determines the pattern in the text, between which are these phrases.
     * And then extracts all phrases between determined pattern.
     * @param words
     * @param text
     * @return list of mined phrases in text
     * @throws EmptyMinedPatternException
     * @throws UncertainMinedPatternException
     */
    public List<String> analyze(List<String> words, String text) throws EmptyMinedPatternException, UncertainMinedPatternException {
        MinedPattern minedPattern = extractor.findMinedPattern(words, text);
        return extractor.extract(text, minedPattern);
    }
}
