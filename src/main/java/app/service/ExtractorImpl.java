package app.service;

import app.entity.MinedPattern;
import app.exception.EmptyMinedPatternException;
import app.exception.UncertainMinedPatternException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.*;


@Service
public class ExtractorImpl implements Extractor {

    public List<String> extract(String text, MinedPattern pattern) {
        Set<String> result = new HashSet<>();

        if (text == null
                || text.isEmpty()
                || pattern == null
                || pattern.getStartingWith() == null
                || pattern.getStartingWith().isEmpty()
                || pattern.getEndingWith() == null
                || pattern.getEndingWith().isEmpty())
            return new ArrayList<>();

        String regexString = Pattern.quote(pattern.getStartingWith())
                + "(.*?)"
                + Pattern.quote(pattern.getEndingWith());
        Pattern searchPattern = Pattern.compile(regexString);

        Matcher matcher = searchPattern.matcher(text);

        while (matcher.find()) {
            result.add(matcher.group(1));
        }

        return new ArrayList<>(result);
    }

    public MinedPattern findMinedPattern(List<String> words, String text) throws EmptyMinedPatternException, UncertainMinedPatternException {
        if (text == null || words == null || text.isEmpty() || words.isEmpty()) throw new EmptyMinedPatternException();

        String startMinePattern = null;
        String endMinePattern = null;
        int shift = 5;

        for (String word : words) {

            if (word == null || word.isEmpty()) continue;

            int startIndex = text.indexOf(word);
            if (startIndex < 0) continue;

            String prefix = findWordPrefix(text, startIndex, word.length(), shift);
            String postfix = findWordPostfix(text, startIndex, word.length(), shift);

            startMinePattern = chooseStartPattern(startMinePattern, prefix);
            endMinePattern = choosePattern(endMinePattern, postfix);
        }

        if (startMinePattern == null
                || endMinePattern == null
                || startMinePattern.isEmpty()
                || endMinePattern.isEmpty())

            throw new UncertainMinedPatternException();

        MinedPattern minedPattern = new MinedPattern();
        minedPattern.setStartingWith(startMinePattern);
        minedPattern.setEndingWith(endMinePattern);

        return minedPattern;
    }

    String chooseStartPattern(String pattern1, String pattern2) {
        return reverse(choosePattern(reverse(pattern1), reverse(pattern2)));
    }

    String choosePattern(String pattern1, String pattern2) {
        if (pattern1 == null) {
            return pattern2;
        }
        return getCommonPrefix(pattern1, pattern2);
    }

    String findWordPostfix(String html, int startIndex, int length, int shift) {
        int postIndex = (startIndex + length + shift <= html.length() ? startIndex + length + shift : html.length());
        return html.substring(startIndex + length, postIndex);
    }

    String findWordPrefix(String html, int startIndex, int length, int shift) {
        int prefixIndex = (startIndex >= shift ? startIndex - shift : 0);
        return html.substring(prefixIndex, startIndex);
    }
}
