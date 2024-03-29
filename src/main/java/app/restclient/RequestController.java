package app.restclient;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Rest client
 */
@Service
public class RequestController {

    private RestTemplate restTemplate;

    public RequestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public String requestHtml(String url){
        return restTemplate.getForObject(url, String.class);
    }
}
