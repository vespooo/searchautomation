package app.service;

import app.entity.SearchPattern;
import app.entity.URLPattern;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface ParameterMapper {

    /**
     * maps parameters {@link app.entity.URLPattern#getParams} in url {@link app.entity.URLPattern#getUrl}
     */
    String map(URLPattern pattern);
}
