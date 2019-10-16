package app.service;

import app.entity.MinedPattern;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Extractor {
    public List<String> extract(String html, MinedPattern minedPattern) {
        Set<String> result = new HashSet<>();

        String regexString = Pattern.quote(minedPattern.getStartingWith())
                + "(.*?)"
                + Pattern.quote(minedPattern.getEndingWith());
        Pattern pattern = Pattern.compile(regexString);

        Matcher matcher = pattern.matcher(html);

        while (matcher.find()) {
            result.add(matcher.group(1));
        }

        return new ArrayList<>(result);
    }

    public void findStartAndEndMinePattern(List<String> minedWords, String html) {
        Set startMinePattern = new HashSet();
        Set endMinePattern = new HashSet();
        int shift =5;
        System.out.println(html);
        for (String word : minedWords){
            int startIndex = html.indexOf(word);
            int length = word.length();
            if (startIndex <0 ) continue;
            int preIndex = ( startIndex >= shift ? startIndex -shift : 0);
            int postIndex = ( startIndex + length + shift < html.length()-1 ? startIndex + length + shift : html.length()-1);
            System.out.println(html.substring(preIndex, startIndex)
                    + " + " + html.substring(startIndex, startIndex + length)
                    + " + " + html.substring(startIndex+ length, postIndex));
        }

    }
}
