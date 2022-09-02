package persistence;

import model.Ball;
import model.ListOfWords;
import model.Word;


import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.Test;

// Test for JsonWriter
// This Class references code from: JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonWriterTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            Ball ball = new Ball();
            Word word = new Word("cathy");
            ListOfWords listOfWords = new ListOfWords();

            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test

    public void testWriterInitialCondition() {
        try {
            Ball ball = new Ball();
            Word word = new Word("cathy");
            ListOfWords listOfWords = new ListOfWords();

            JsonWriter writer = new JsonWriter("./data/testWriterInitialCondition.json");
            writer.open();
            writer.write(ball, word, listOfWords);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterInitialCondition.json");
            ball = reader.readBall();
            word = reader.readWord();
            listOfWords = reader.readListOfWord();

            assertTrue(ball.getXCood() == ball.getXIni());
            assertTrue(ball.getYCood() == ball.getYIni());
            assertEquals("cathy", word.getWord());
            assertEquals(0, listOfWords.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneralCondition() {
        try {
            Ball ball = new Ball();
            Word word = new Word("cathy");
            ListOfWords listOfWords = new ListOfWords();

            ball.moveHorizontally();
            word.setWord("betty");
            listOfWords.addWord(word);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCondition.json");
            writer.open();
            writer.write(ball, word, listOfWords);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCondition.json");
            ball = reader.readBall();
            word = reader.readWord();
            listOfWords = reader.readListOfWord();

            assertTrue(ball.getXCood() == ball.getXIni() + ball.getDx());
            assertTrue(ball.getYCood() == ball.getYIni());
            assertEquals("betty", word.getWord());
            assertEquals(1, listOfWords.length());
            assertTrue(listOfWords.getWord(0).getWord().equals("betty"));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
