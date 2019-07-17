package app.service;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StringParameterMapper {
    public String map(String url, Map<String, String> params) {
        StringSubstitutor sub = new StringSubstitutor(params);
        return sub.replace(url);
    }
}
