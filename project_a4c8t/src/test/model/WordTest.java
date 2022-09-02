package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// Test for Word Class

public class WordTest {

    Word word;

    @BeforeEach
    public void runBefore() {
        word = new Word("apple");
    }

    @Test
    public void testSetWord() {
        word.setWord("orange");
        assertEquals("orange", word.getWord());
    }

    @Test
    public void testBreakWordSingleString() {
        word.setWord("a");
        List<String> myThisBrokenWord = word.breakWord();

        assertEquals(1, myThisBrokenWord.size());
        assertEquals("a",myThisBrokenWord.get(0));
    }

    @Test
    public void testBreakWordAWord() {
        List<String> myThisBrokenWord = word.breakWord();

        assertEquals(5, myThisBrokenWord.size());
        assertEquals("a",myThisBrokenWord.get(0));
        assertEquals("p",myThisBrokenWord.get(1));
        assertEquals("p",myThisBrokenWord.get(2));
        assertEquals("l",myThisBrokenWord.get(3));
        assertEquals("e",myThisBrokenWord.get(4));
    }

    @Test
    public void testBreakWordManyLetters() {
        String newWord = "adafdafbdadafddafdaeagdaklfdkfaldfaeea";
        word.setWord(newWord);
        List<String> myThisBrokenWord = word.breakWord();

        for(int i = 0; i < newWord.length(); i++) {
            assertEquals(String.valueOf(newWord.charAt(i)), myThisBrokenWord.get(i));
        }
        assertEquals(newWord.length(), myThisBrokenWord.size());
    }

    @Test
    public void testCoverWordSingleString() {
        word.setWord("a");
        List<String> myThisCoveredWord = word.coverWord();

        assertEquals(1, myThisCoveredWord.size());
        assertEquals("a",myThisCoveredWord.get(0));
    }

    @Test
    public void testCoverWordAWord() {
        List<String> myThisCoveredWord = word.coverWord();

        List<String> questionPosInMyThisCoveredWord = new ArrayList<>();
        for (String s : myThisCoveredWord) {
            if (s.equals("?")) {
                questionPosInMyThisCoveredWord.add(s);
            }
        }

        assertEquals(5, myThisCoveredWord.size());
        assertTrue(myThisCoveredWord.contains("?"));
        assertTrue(questionPosInMyThisCoveredWord.size() >= 1);
        assertTrue(questionPosInMyThisCoveredWord.size() <= word.getWord().length() - 1);
    }

    @Test
    public void testComposeWordSingleString() {
        word.setWord("a");
        Word myThisComposedWord = word.composeWord();

        assertEquals(1, myThisComposedWord.getWord().length());
        assertEquals("a", myThisComposedWord.getWord());
    }

    @Test
    public void testComposeWordAWord() {
        Word myThisComposedWord = word.composeWord();

        List<String> myThisComposedWordBroken = myThisComposedWord.breakWord();
        List<String> questionPosInMyThisComposedWord = new ArrayList<>();
        for (String s : myThisComposedWordBroken) {
            if (s.equals("?")) {
                questionPosInMyThisComposedWord.add(s);
            }
        }

        assertEquals(5,myThisComposedWord.getWord().length());
        assertTrue(myThisComposedWordBroken.contains("?"));
        assertTrue(questionPosInMyThisComposedWord.size() >= 1);
        assertTrue(questionPosInMyThisComposedWord.size() <= word.getWord().length() - 1);
    }
}
