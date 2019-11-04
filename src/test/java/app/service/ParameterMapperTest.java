package app.service;

import app.Application;
import app.entity.SearchPattern;
import app.entity.URLPattern;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= Application.class)
public class ParameterMapperTest {

    @Autowired
    private ParameterMapper parameterMapper;

    @Test
    public void emptyUrlMappingShouldReturnEmptyUrl(){
        HashMap<String, String> params = new HashMap<String, String>() {
            {
                put("a", "b");
            }
        };
        URLPattern pattern = createUrlPattern("", params);

        Assertions.assertThat(parameterMapper.map(pattern)).isEqualTo("");
    }

    @Test
    public void urlParamsShouldBeMapped(){
        String url = "https://restclient/${a}/${b}/";
        String result = "https://restclient/aaa/bbb/";

        HashMap<String, String> params = new HashMap<String, String>() {
            {
                put("a", "aaa");
                put("b", "bbb");
            }
        };

        URLPattern pattern = createUrlPattern(url, params);

        Assertions.assertThat(parameterMapper.map(pattern)).isEqualTo(result);
    }

    private URLPattern createUrlPattern(String url, HashMap<String, String> params) {


        URLPattern urlPattern = new URLPattern();
        urlPattern.setUrl(url);
        urlPattern.setParams(params);

        return urlPattern;
    }

}
