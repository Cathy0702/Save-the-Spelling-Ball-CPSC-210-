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

// Test for GameOverException

public class GameOverExceptionTest {
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
        question.add("p");
        question.add("p");
        question.add("l");
        question.add("?");
        gameState.setCurrentQuestionWord(question);
    }

    @Test
    public void testGameOverExceptionInitialBall() {
        try {
            gameState.processOneWord(5,"e");
        } catch (InvalidPositionException e) {
            fail();
        } catch (GameOverException e) {
            fail();
        }
    }

    @Test
    public void testGameOverExceptionMovedBall() {
        try {
            gameState.processOneWord(5,"a");
        } catch (InvalidPositionException e) {
            fail();
        } catch (GameOverException e) {
            fail();
        }
    }

    @Test
    public void testGameOverExceptionWithException() {
        for (int i = 0; i < 4; i++) {
            try {
                gameState.processOneWord(5,"a");
            } catch (InvalidPositionException e) {
                fail();
            } catch (GameOverException e) {
                fail();
            }
        }
        try {
            gameState.processOneWord(5,"a");
            fail();
        } catch (InvalidPositionException e) {
            fail();
        } catch (GameOverException e) {

        }
    }
}
