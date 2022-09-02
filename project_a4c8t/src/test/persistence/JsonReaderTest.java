package persistence;

import model.Ball;
import model.ListOfWords;
import model.Word;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.Test;

// Test for JsonReader
// This Class references code from: JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Ball ball = reader.readBall();
            Word word = reader.readWord();
            ListOfWords listOfWords = reader.readListOfWord();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderInitialCondition() {
        JsonReader reader = new JsonReader("./data/testReaderInitialCondition.json");
        try {
            Ball ball = reader.readBall();
            Word word = reader.readWord();
            ListOfWords listOfWords = reader.readListOfWord();

            assertTrue(ball.getXCood() == ball.getXIni());
            assertTrue(ball.getYCood() == ball.getYIni());
            assertEquals("betty", word.getWord());
            assertEquals(0, listOfWords.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralCondition() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCondition.json");
        try {
            Ball ball = reader.readBall();
            Word word = reader.readWord();
            ListOfWords listOfWords = reader.readListOfWord();

            assertTrue(ball.getYCood() == 25);
            assertTrue(ball.getXCood() == 340);
            assertEquals("betty", word.getWord());
            assertEquals(3, listOfWords.length());
            assertTrue(listOfWords.getWord(0).getWord().equals("betty"));
            assertTrue(listOfWords.getWord(1).getWord().equals("tuesday"));
            assertTrue(listOfWords.getWord(2).getWord().equals("monday"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
