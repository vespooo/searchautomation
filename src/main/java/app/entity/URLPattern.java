package app.entity;

import java.util.List;
import java.util.Map;

public class URLPattern {
    private String url;
    private Map<String, String> params;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

 }
