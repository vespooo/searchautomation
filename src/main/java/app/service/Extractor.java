package app.service;

import app.entity.MinedPattern;
import app.exception.EmptyMinedPatternException;
import app.exception.UncertainMinedPatternException;

import java.util.*;

public interface Extractor {

    /**
     * Extracts a list of phrases between {@link MinedPattern#getStartingWith()} and {@link MinedPattern#getEndingWith()} pattern
     * in a text.
     * @return mined phrases
     */
    List<String> extract(String text, MinedPattern minedPattern);

    /**
     * According to an example list of phrases, determines the pattern in the text, between which are these phrases.
     * @param minedWords example phrases
     * @throws EmptyMinedPatternException
     * @throws UncertainMinedPatternException
     */
    MinedPattern findMinedPattern(List<String> minedWords, String text) throws EmptyMinedPatternException, UncertainMinedPatternException;
}
