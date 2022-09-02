package exception;

import model.Ball;
import model.ListOfWords;
import model.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.GameState;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

// Test for InvalidPositionException

public class InvalidPositionExceptionTest {
    GameState gameState;

    @BeforeEach
    public void runBefore() {
        Ball ball = new Ball();
        Word firstWord = new Word("");
        ListOfWords listOfWords = new ListOfWords();
        gameState = new GameState(ball,firstWord,listOfWords);
        List<String> answer = new ArrayList<>();
        answer.add("a");
        answer.add("p");
        answer.add("p");
        answer.add("l");
        answer.add("e");
        gameState.setCurrentCorrectWord(answer);
        List<String> question = new ArrayList<>();
        question.add("a");
        question.add("?");
        question.add("p");
        question.add("?");
        question.add("?");
        gameState.setCurrentQuestionWord(question);
    }

    @Test
    public void testProcessOneWordWithoutException() {
        try {
            gameState.processOneWord(2,"p");
        } catch (InvalidPositionException e) {
            fail();
        } catch (GameOverException e) {
            fail();
        }
    }

    @Test
    public void testProcessOneWordNegativePosition() {
        try {
            gameState.processOneWord(-1,"p");
            fail();
        } catch (InvalidPositionException e) {

        } catch (GameOverException e) {
            fail();
        }
    }

    @Test
    public void testProcessOneWordZeroPosition() {
        try {
            gameState.processOneWord(0,"p");
            fail();
        } catch (InvalidPositionException e) {

        } catch (GameOverException e) {
            fail();
        }
    }

    @Test
    public void testProcessOneWordMoreThanLengthPosition() {
        try {
            gameState.processOneWord(6,"p");
            fail();
        } catch (InvalidPositionException e) {

        } catch (GameOverException e) {
            fail();
        }
    }

    @Test
    public void testProcessOneWordNotQuestionPosition() {
        try {
            gameState.processOneWord(1,"p");
            fail();
        } catch (InvalidPositionException e) {

        } catch (GameOverException e) {
            fail();
        }
    }

    @Test
    public void testProcessOneWordNotQuestionPosition2() {
        try {
            gameState.processOneWord(3,"p");
            fail();
        } catch (InvalidPositionException e) {

        } catch (GameOverException e) {
            fail();
        }
    }
}
