package app.service;

import app.Application;
import app.entity.MinedPattern;
import app.exception.EmptyMinedPatternException;
import app.exception.UncertainMinedPatternException;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExtractorTest {

    private ExtractorImpl extractor = new ExtractorImpl();

    @Test
    public void emptyPrefix(){
        String text = "aa<";
        String word = "a";
        int shift = 5;
        Assertions.assertThat(extractor.findWordPrefix(text, text.indexOf(word), word.length(), shift)).isEqualTo("");
    }

    @Test
    public void prefixInTheBeginning(){
        String text = ">aa<";
        String word = "a";
        int shift = 5;
        Assertions.assertThat(extractor.findWordPrefix(text, text.indexOf(word), word.length(), shift)).isEqualTo(">");
    }

    @Test
    public void prefixInTheMiddle(){
        String text = "12345>aa<";
        String word = "a";
        int shift = 5;
        Assertions.assertThat(extractor.findWordPrefix(text, text.indexOf(word), word.length(), shift)).isEqualTo("2345>");
    }

    @Test
    public void emptyPostfix(){
        String text = "a";
        String word = "a";
        int shift = 5;
        Assertions.assertThat(extractor.findWordPostfix(text, text.indexOf(word), word.length(), shift)).isEqualTo("");
    }

    @Test
    public void postfixInTheBeginning(){
        String text = ">aa<";
        String word = "a";
        int shift = 5;
        Assertions.assertThat(extractor.findWordPostfix(text, text.indexOf(word), word.length(), shift)).isEqualTo("a<");
    }

    @Test
    public void postfixInTheMiddle(){
        String text = "a<12345";
        String word = "a";
        int shift = 5;
        Assertions.assertThat(extractor.findWordPostfix(text, text.indexOf(word), word.length(), shift)).isEqualTo("<1234");
    }

    @Test
    public void chooseStartPatternIfPatternIsEmpty(){
        String pattern1 = "";
        String pattern2 = "d";
        Assertions.assertThat(extractor.chooseStartPattern(pattern1, pattern2)).isEqualTo("");
    }

    @Test
    public void chooseStartPatternIfPatternsHaveCommonPart(){
        String pattern1 = "ad";
        String pattern2 = "bd";
        Assertions.assertThat(extractor.chooseStartPattern(pattern1, pattern2)).isEqualTo("d");
    }

    @Test
    public void chooseStartPatternIfPatternsAreIdentical(){
        String pattern1 = "ad";
        String pattern2 = "ad";
        Assertions.assertThat(extractor.chooseStartPattern(pattern1, pattern2)).isEqualTo("ad");
    }

    @Test
    public void chooseStartPatternIfPatternsHaveNoCommonPart(){
        String pattern1 = "aj";
        String pattern2 = "bd";
        Assertions.assertThat(extractor.chooseStartPattern(pattern1, pattern2)).isEqualTo("");
    }

    @Test
    public void choosePatternIfPatternIsEmpty(){
        String pattern1 = "";
        String pattern2 = "d";
        Assertions.assertThat(extractor.choosePattern(pattern1, pattern2)).isEqualTo("");
    }

    @Test
    public void choosePatternIfPatternsHaveCommonPart(){
        String pattern1 = "da";
        String pattern2 = "dv";
        Assertions.assertThat(extractor.choosePattern(pattern1, pattern2)).isEqualTo("d");
    }

    @Test
    public void choosePatternIfPatternsAreIdentical(){
        String pattern1 = "ad";
        String pattern2 = "ad";
        Assertions.assertThat(extractor.choosePattern(pattern1, pattern2)).isEqualTo("ad");
    }

    @Test
    public void choosePatternIfPatternsHaveNoCommonPart(){
        String pattern1 = "aj";
        String pattern2 = "bd";
        Assertions.assertThat(extractor.choosePattern(pattern1, pattern2)).isEqualTo("");
    }

    @Test
    public void findMinedPatternWithEmptyText() {
        List<String> list = Arrays.asList("j");
        Assertions.assertThatThrownBy(() -> extractor.findMinedPattern(list, "")).isInstanceOf(EmptyMinedPatternException.class);
    }

    @Test
    public void findMinedPatternWithEmptyWords() {
        List<String> list = new ArrayList<>();
        Assertions.assertThatThrownBy(() -> extractor.findMinedPattern(list, "dfdf")).isInstanceOf(EmptyMinedPatternException.class);
    }

    @Test
    public void findMinedPatternWithEmptyWord() throws EmptyMinedPatternException, UncertainMinedPatternException {
        List<String> list = Arrays.asList("fd", "");
        String text = "dfdf";
        Assertions.assertThat(extractor.findMinedPattern(list, text).getStartingWith()).isEqualTo("d");
        Assertions.assertThat(extractor.findMinedPattern(list, text).getEndingWith()).isEqualTo("f");
    }

    @Test
    public void findMinedPatternWithTwoDifferentPatterns() throws EmptyMinedPatternException, UncertainMinedPatternException {
        List<String> list = Arrays.asList("fd", "ad");
        String text = "ddfdff dadf";
        Assertions.assertThat(extractor.findMinedPattern(list, text).getStartingWith()).isEqualTo("d");
        Assertions.assertThat(extractor.findMinedPattern(list, text).getEndingWith()).isEqualTo("f");
    }

    @Test
    public void findMinedPatternWithCompletelyDifferentPatternsShouldThrowExc() throws EmptyMinedPatternException {
        List<String> list = Arrays.asList("fd", "ad");
        String text = "ddfdff cadf";
        Assertions.assertThatThrownBy( () -> extractor.findMinedPattern(list, text)).isInstanceOf(UncertainMinedPatternException.class);
    }

    @Test
    public void extractWithEmptyText(){
        String text = "";
        MinedPattern pattern = new MinedPattern();
        pattern.setStartingWith(">");
        pattern.setEndingWith("<");
        Assertions.assertThat(extractor.extract(text, pattern)).isEmpty();
    }

    @Test
    public void extractWithPatternText(){
        String text = "";
        MinedPattern pattern = new MinedPattern();
        pattern.setStartingWith(">");
        pattern.setEndingWith("");
        Assertions.assertThat(extractor.extract(text, pattern)).isEmpty();
    }

    @Test
    public void extractCanFindAllWords(){
        String text = "aaa>bb< >ccc< jdlk>d<uhiuh";
        MinedPattern pattern = new MinedPattern();
        pattern.setStartingWith(">");
        pattern.setEndingWith("<");
        Assertions.assertThat(extractor.extract(text, pattern)).containsOnly("bb", "ccc", "d");
    }

}
