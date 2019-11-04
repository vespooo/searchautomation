package app.rest;

import app.entity.AnalyzePattern;
import app.entity.MinedPattern;
import app.entity.SearchPattern;
import app.exception.MinedPatternException;
import app.service.ProcessService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
public class SearchController {

    private ProcessService processService;

    @Autowired
    public SearchController(ProcessService processService) {
        this.processService = processService;
    }

    /**
     * Constructs requests using {@link app.entity.URLPattern} to get html page
     * And then extracts a list of phrases between {@link MinedPattern} in the html.
     * @param pattern JSON request body
     * @return list of mined phrases
     */
    @RequestMapping(value = "/extract", method = RequestMethod.POST, consumes = {"application/json"})
    public List<String> extract(@RequestBody SearchPattern pattern){

        return processService.extract(pattern);
    }

    /**
     * Gets an html from a url.
     * And then extracts all phrases between the same pattern in the html, determined using example phrases.
     * @param pattern JSON request body
     * @return list of mined phrases in html
     */
    @RequestMapping(value = "/analyze", method = RequestMethod.POST, consumes = {"application/json"})
    public List<String> analyze(@RequestBody AnalyzePattern pattern) {
        Document doc = null;
        try {
            doc = Jsoup.connect(pattern.getUrl())
                    .userAgent("Mozilla/5.0")
                    .get();

            return processService.analyze(pattern.getMinedWords(), doc.html());

        } catch (IOException e) {
            e.printStackTrace();
            //TODO
        } catch (MinedPatternException e) {
            //TODO
        }

        return Collections.EMPTY_LIST;
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST, consumes = {"text/plain"})
    public String test(@RequestBody String url){

        return url;
    }

}
