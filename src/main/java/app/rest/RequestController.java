package app.rest;

import app.entity.AnalyzePattern;
import app.entity.SearchPattern;
import app.entity.URLPattern;
import app.search.SearchController;
import app.service.ProcessService;
import app.service.StringParameterMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class RequestController {

    private ProcessService processService;

    @Autowired
    public RequestController(ProcessService processService) {
        this.processService = processService;
    }

    @RequestMapping(value = "/url", method = RequestMethod.POST, consumes = {"application/json"})
    public List<String> minedText(@RequestBody SearchPattern pattern){

        return processService.process(pattern);
    }

    @RequestMapping(value = "/analyze", method = RequestMethod.POST, consumes = {"application/json"})
    public void analyze(@RequestBody AnalyzePattern pattern) {
        Document doc = null;
        try {
            doc = Jsoup.connect(pattern.getUrl())
                    .userAgent("Mozilla/5.0")
                    .get();

            processService.analyze(pattern, doc.html());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @RequestMapping(value = "/test", method = RequestMethod.POST, consumes = {"text/plain"})
    public String test(@RequestBody String url){

        return url;
    }

}
