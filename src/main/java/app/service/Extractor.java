package app.service;

import app.entity.MindedPattern;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class Extractor {
    public List<String> extract(String requestHtml, MindedPattern minedPattern) {
        Set<String> result = new HashSet<>();

        String regexString = Pattern.quote(minedPattern.getStartingWith())
                + "(.*?)"
                + Pattern.quote(minedPattern.getEndingWith());
        Pattern pattern = Pattern.compile(regexString);

        Matcher matcher = pattern.matcher(requestHtml);

        while (matcher.find()) {
            result.add(matcher.group(1));
        }

        return new ArrayList<>(result);
    }
}
