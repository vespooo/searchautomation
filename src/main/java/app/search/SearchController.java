package app.search;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Rest client
 */
@Service
public class SearchController {

    private RestTemplate restTemplate;

    public SearchController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public String requestHtml(String url){
        return restTemplate.getForObject(url, String.class);
    }
}
