package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Test for ListOfWords Class

public class ListOfWordsTest {

    ListOfWords listOfWords;
    Word testWord;

    @BeforeEach
    public void runBefore() {
        listOfWords = new ListOfWords();
        testWord = new Word("apple");
    }

    @Test
    public void testAddWordEmpty() {
        listOfWords.addWord(testWord);

        assertEquals(1, listOfWords.length());
        assertTrue(listOfWords.getWord(0).getWord().equals("apple"));

    }

    @Test
    public void testAddWordMore() {
        Word secondTestWord = new Word("orange");
        int i;
        for (i = 0; i < 19; i++) {
            listOfWords.addWord(testWord);
        }
        listOfWords.addWord(secondTestWord);

        assertEquals(i+1, listOfWords.length());
        assertTrue(listOfWords.getWord(i).getWord().equals("orange"));

    }

    @Test
    public void testRandomizeWordsEmpty() {
        listOfWords.randomizeWords();

        assertEquals(0,listOfWords.length());
    }

    @Test
    public void testRandomizeWordsNotEmptyWithoutRepeat() {
        Word testWord2 = new Word("orange");
        Word testWord3 = new Word("peach");
        Word testWord4 = new Word("melon");
        listOfWords.addWord(testWord);
        listOfWords.addWord(testWord2);
        listOfWords.addWord(testWord3);
        listOfWords.addWord(testWord4);
        listOfWords.randomizeWords();


        assertEquals(4,listOfWords.length());
        assertTrue(listOfWords.contains(testWord));
        assertTrue(listOfWords.contains(testWord2));
        assertTrue(listOfWords.contains(testWord3));
        assertTrue(listOfWords.contains(testWord4));
    }

    @Test
    public void testRandomizeWordsNotEmptyWithRepeat() {
        Word secondTestWord = new Word("orange");
        int i;
        for (i = 0; i < 19; i++) {
            listOfWords.addWord(testWord);
        }
        listOfWords.addWord(secondTestWord);
        listOfWords.randomizeWords();

        ListOfWords repeatWord = new ListOfWords();
        for (Word w : listOfWords.getWords()) {
            if (w.equals(testWord)) {
                repeatWord.addWord(w);
            }
        }

        assertEquals(i+1, listOfWords.length());
        assertEquals(i, repeatWord.length());
        assertTrue(listOfWords.contains(testWord));
        assertTrue(listOfWords.contains(secondTestWord));


    }

    @Test
    public void testGetWordOneWord() {
        listOfWords.addWord(testWord);
        Word getWordResult = listOfWords.getWord(0);

        assertEquals(testWord,getWordResult);
    }

    @Test
    public void testGetWordMoreWords() {
        Word testWord2 = new Word("orange");
        Word testWord3 = new Word("peach");
        Word testWord4 = new Word("melon");
        listOfWords.addWord(testWord);
        listOfWords.addWord(testWord2);
        listOfWords.addWord(testWord3);
        listOfWords.addWord(testWord4);

        Word getWordResult1 = listOfWords.getWord(0);
        Word getWordResult2 = listOfWords.getWord(1);
        Word getWordResult3 = listOfWords.getWord(2);
        Word getWordResult4 = listOfWords.getWord(3);

        assertEquals(testWord,getWordResult1);
        assertEquals(testWord2,getWordResult2);
        assertEquals(testWord3,getWordResult3);
        assertEquals(testWord4,getWordResult4);
    }

    @Test
    public void testLengthEmpty() {
        assertEquals(0, listOfWords.length());
    }

    @Test
    public void testLengthSeveral() {
        Word testWord2 = new Word("orange");
        Word testWord3 = new Word("peach");
        Word testWord4 = new Word("melon");
        listOfWords.addWord(testWord);
        listOfWords.addWord(testWord2);
        listOfWords.addWord(testWord3);
        listOfWords.addWord(testWord4);

        assertEquals(4, listOfWords.length());
    }

    @Test
    public void testLengthMore() {
        int i;
        for (i = 0; i < 19; i++) {
            listOfWords.addWord(testWord);
        }

        assertEquals(i,listOfWords.length());
    }

    @Test
    public void testContainsEmpty() {
        assertFalse(listOfWords.contains(testWord));
    }

    @Test
    public void testContainsDoesContain() {
        Word testWord2 = new Word("orange");
        Word testWord3 = new Word("peach");
        Word testWord4 = new Word("melon");
        listOfWords.addWord(testWord);
        listOfWords.addWord(testWord2);
        listOfWords.addWord(testWord3);
        listOfWords.addWord(testWord4);

        assertTrue(listOfWords.contains(testWord));
    }

    @Test
    public void testContainsDoesNotContain() {
        Word testWord2 = new Word("orange");
        Word testWord3 = new Word("peach");
        Word testWord4 = new Word("melon");
        listOfWords.addWord(testWord2);
        listOfWords.addWord(testWord3);
        listOfWords.addWord(testWord4);

        assertFalse(listOfWords.contains(testWord));
    }

    @Test
    public void testGetSubListEmpty(){
        assertEquals(0,listOfWords.getSubList(0,0).length());
    }

    @Test
    public void testGetSubListSameBound(){
        Word testWord2 = new Word("orange");
        listOfWords.addWord(testWord);
        listOfWords.addWord(testWord2);

        ListOfWords testResult1 = listOfWords.getSubList(0,0);
        ListOfWords testResult2 = listOfWords.getSubList(1,1);
        assertEquals(0,testResult1.length());
        assertEquals(0,testResult2.length());

    }

    @Test
    public void testGetSubListDifferentBound(){
        Word testWord2 = new Word("orange");
        Word testWord3 = new Word("peach");
        Word testWord4 = new Word("melon");
        listOfWords.addWord(testWord);
        listOfWords.addWord(testWord2);
        listOfWords.addWord(testWord3);
        listOfWords.addWord(testWord4);

        ListOfWords testResult1 = listOfWords.getSubList(0,2);
        ListOfWords testResult2 = listOfWords.getSubList(2,3);
        assertEquals(2,testResult1.length());
        assertEquals(1,testResult2.length());
        assertTrue(testResult1.getWord(0).getWord().equals("apple"));
        assertTrue(testResult1.getWord(1).getWord().equals("orange"));
        assertTrue(testResult2.getWord(0).getWord().equals("peach"));

    }

    @Test
    public void testGetSubListExtreme(){
        Word testWord2 = new Word("orange");
        Word testWord3 = new Word("peach");
        Word testWord4 = new Word("melon");
        listOfWords.addWord(testWord);
        listOfWords.addWord(testWord2);
        listOfWords.addWord(testWord3);
        listOfWords.addWord(testWord4);

        ListOfWords testResult1 = listOfWords.getSubList(0,4);
        assertEquals(4,testResult1.length());
        assertTrue(testResult1.getWord(0).getWord().equals("apple"));
        assertTrue(testResult1.getWord(1).getWord().equals("orange"));
        assertTrue(testResult1.getWord(2).getWord().equals("peach"));
        assertTrue(testResult1.getWord(3).getWord().equals("melon"));

    }

    @Test
    public void testSetWordsThisEmpty() {
        Word testWord2 = new Word("orange");
        Word testWord3 = new Word("peach");
        Word testWord4 = new Word("melon");
        ListOfWords newListOfWords = new ListOfWords();
        newListOfWords.addWord(testWord);
        newListOfWords.addWord(testWord2);
        newListOfWords.addWord(testWord3);
        newListOfWords.addWord(testWord4);

        listOfWords.setWords(newListOfWords.getWords());
        assertEquals(4,listOfWords.length());
        assertTrue(listOfWords.contains(testWord));
        assertTrue(listOfWords.contains(testWord2));
        assertTrue(listOfWords.contains(testWord3));
        assertTrue(listOfWords.contains(testWord4));
    }

    @Test
    public void testSetWordsBothHaveWords() {
        Word testWord2 = new Word("orange");
        Word testWord3 = new Word("peach");
        Word testWord4 = new Word("melon");
        ListOfWords newListOfWords = new ListOfWords();
        listOfWords.addWord(testWord);
        listOfWords.addWord(testWord2);
        listOfWords.addWord(testWord3);
        newListOfWords.addWord(testWord4);

        listOfWords.setWords(newListOfWords.getWords());
        assertEquals(1,listOfWords.length());
        assertTrue(listOfWords.contains(testWord4));
    }


}
