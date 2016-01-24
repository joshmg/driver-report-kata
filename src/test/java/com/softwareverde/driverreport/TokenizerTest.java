package com.softwareverde.driverreport;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TokenizerTest {
    Tokenizer _subject;

    @Before
    public void setUp() throws Exception {
        _subject = new Tokenizer();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testTokenizerRetrievesFirstWord() throws Exception {
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
    public void testTokenizerRetrievesSecondWord() throws Exception {
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
    public void testTokenizerReturnsEmptyStringWhenPastEndOfContent() throws Exception {
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
    public void testTokenizerReturnsWholeStringWhenDeliminatorNotPresent() throws Exception {
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
    public void testTokenizerReturnsEmptyStringWhenRetrievingTheNextWordWithContentHavingNoDeliminator() throws Exception {
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
    public void testTokenizerReturnsNextWordWhenDelimitedWithNewline() throws Exception {
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
    public void testTokenizerReturnsNewContentAfterOverwritingContent() throws Exception {
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

    @Test
    public void testTokenizerReturnsFirstWordAgainAfterReset() throws Exception {
        // Setup
        final String content = "Mary Had\nA Little Lamb";
        final String expectedWord = "Mary";

        _subject.setContent(content);
        _subject.getNextWord();
        _subject.getNextWord();

        // Action
        _subject.reset();
        final String word = _subject.getNextWord();

        // Assert
        assertEquals("Failed to retrieve the original first word after reset.", expectedWord, word);
    }

    @Test
    public void testTokenizerHasNextWordWhenNotAtEndOfContent() throws Exception {
        // Setup
        final String content = "Mary Had\nA Little Lamb";
        final Boolean expectedHasNext = true;

        _subject.setContent(content);
        _subject.getNextWord();

        // Action
        final Boolean hasNext = _subject.hasNextWord();

        // Assert
        assertEquals("HasNextWord fails when a next word is available.", expectedHasNext, hasNext);
    }

    @Test
    public void testTokenizerHasNextWordWhenAtEndOfContent() throws Exception {
        // Setup
        final String content = "Mary Had\nA Little Lamb";
        final Boolean expectedHasNext = false;

        _subject.setContent(content);
        _subject.getNextWord(); // Mary
        _subject.getNextWord(); // Had
        _subject.getNextWord(); // A
        _subject.getNextWord(); // Little
        _subject.getNextWord(); // Lamb

        // Action
        final Boolean hasNext = _subject.hasNextWord();

        // Assert
        assertEquals("HasNextWord fails when a next word is available.", expectedHasNext, hasNext);
    }
}
