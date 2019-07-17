package app.search;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SearchController {

    private RestTemplate restTemplate;

    public SearchController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String requestHtml(String url){
        String response  = restTemplate.getForObject(url, String.class);
        System.out.print(response);
        return response;
    }
}
