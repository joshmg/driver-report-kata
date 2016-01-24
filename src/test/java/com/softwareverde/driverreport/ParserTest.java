package com.softwareverde.driverreport;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParserTest {
    Parser _subject;

    @Before
    public void setUp() throws Exception {
        _subject = new Parser();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testParserRetrievesFirstWord() throws Exception {
        // Setup
        final String content = "Mary Had A Little Lamb\nHer fleece was white as snow\n";
        _subject.setContent(content);
        final String expectedFirstWord = "Mary";

        // Action
        final String word = _subject.getNextWord();

        // Assert
        assertEquals("Failed to retrieve the first word.", expectedFirstWord, word);
    }

    @Test
    public void testParserRetrievesSecondWord() throws Exception {
        // Setup
        final String content = "Mary Had A Little Lamb\nHer fleece was white as snow\n";
        _subject.setContent(content);
        final String expectedSecondWord = "Had";
        _subject.getNextWord();

        // Action
        final String word = _subject.getNextWord();

        // Assert
        assertEquals("Failed to retrieve the second word.", expectedSecondWord, word);
    }

    @Test
    public void testParserReturnsEmptyStringWhenPastEndOfContent() throws Exception {
        // Setup
        final String content = "Mary Had";
        _subject.setContent(content);
        final String expectedThirdWord = "";
        _subject.getNextWord();
        _subject.getNextWord();

        // Action
        final String word = _subject.getNextWord();

        // Assert
        assertEquals("Should return empty string when EOF.", expectedThirdWord, word);
    }

    @Test
    public void testParserReturnsWholeStringWhenDeliminatorNotPresent() throws Exception {
        // Setup
        final String content = "MaryHadALittleLamb";
        _subject.setContent(content);
        final String expectedFirstWord = "MaryHadALittleLamb";

        // Action
        final String word = _subject.getNextWord();

        // Assert
        assertEquals("Failed to retrieve the first word when content lacks a deliminator.", expectedFirstWord, word);
    }

    @Test
    public void testParserReturnsEmptyStringWhenRetrievingTheNextWordWithContentHavingNoDeliminator() throws Exception {
        // Setup
        final String content = "MaryHadALittleLamb";
        _subject.setContent(content);
        final String expectedFirstWord = "MaryHadALittleLamb";
        final String expectedSecondWord = "";
        final String firstWord = _subject.getNextWord();

        // Action
        final String secondWord = _subject.getNextWord();

        // Assert
        assertEquals("Failed to retrieve the second word when content is at EOF.", expectedSecondWord, secondWord);
    }

    @Test
    public void testParserReturnsNextWordWhenDelimitedWithNewline() throws Exception {
        // Setup
        final String content = "Mary Had\nA Little Lamb";
        _subject.setContent(content);
        final String expectedSecondWord = "Had";
        _subject.getNextWord();

        // Action
        final String word = _subject.getNextWord();

        // Assert
        assertEquals("Failed to retrieve the next word when it is delimited by a new line.", expectedSecondWord, word);
    }

    @Test
    public void testParserReturnsNewContentAfterOverwritingContent() throws Exception {
        // Setup
        final String originalContent = "Mary Had\nA Little Lamb";
        final String newContent = "Her Fleece Was White as Snow";
        _subject.setContent(originalContent);
        _subject.getNextWord();
        _subject.getNextWord();
        _subject.setContent(newContent);
        _subject.getNextWord();

        final String expectedWord = "Fleece";

        // Action
        final String word = _subject.getNextWord();

        // Assert
        assertEquals("Failed to retrieve the next word after resetting its content.", expectedWord, word);
    }
}
