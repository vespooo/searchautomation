package app.rest;

import app.entity.SearchPattern;
import app.entity.URLPattern;
import app.search.SearchController;
import app.service.ProcessService;
import app.service.StringParameterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(value = "/url/post", method = RequestMethod.POST)
    public void helloPost(){

        System.out.println("Post");;
    }

    @RequestMapping(value = "/url/put", method = RequestMethod.PUT)
    public void helloPut(){

        System.out.println("Put");;
    }
}
