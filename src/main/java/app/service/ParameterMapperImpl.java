package app.service;

import app.entity.SearchPattern;
import app.entity.URLPattern;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ParameterMapperImpl implements ParameterMapper{

    public String map(URLPattern pattern) {
        StringSubstitutor sub = new StringSubstitutor(pattern.getParams());
        return sub.replace(pattern.getUrl());
    }
}
