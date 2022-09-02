package ui;

import exception.GameOverException;
import exception.InvalidPositionException;
import model.Ball;
import model.ListOfWords;
import model.Table;
import model.Word;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Represent a game state, with all methods need to process the game
// Variant: currentCorrectWord.size() == currentQuestionWord.size()

public class GameState {
    private ListOfWords myWordList;
    private List<String> currentCorrectWord;
    private List<String> currentQuestionWord;
    private Ball ball;
    private Table table;
    private static final String JSON_STORE = "./data/spellingGame.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Word firstWord;

    // Construct a GameState with a ball on initial position, a null firstWord, and an empty WordList
    public GameState(Ball ball, Word firstWord, ListOfWords listOfWords) {
        myWordList = listOfWords;
        currentCorrectWord = new ArrayList<>();
        currentQuestionWord = new ArrayList<>();
        this.ball = ball;
        this.firstWord = firstWord;
        table = new Table();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // REQUIRE: i <= this.myWordList.size() - 1
    // MODIFIES: this
    // EFFECTS: process the word with index i in the word list, return the word with random question marks
    public String processListOfWord(int i) {
        Word myThisCorrectWord = myWordList.getWord(i);
        List<String> myThisBrokenCorrectWord = myThisCorrectWord.breakWord();

        Word myThisQuestion = getThisComposedWord(i);
        String myThisQuestionString = myThisQuestion.getWord();
        List<String> myThisQuestionBrokenWord = myThisQuestion.breakWord();

        currentCorrectWord = myThisBrokenCorrectWord;
        currentQuestionWord = myThisQuestionBrokenWord;

        return myThisQuestionString;
    }

    // MODIFIES: this
    // EFFECTS: process user's answer
    public void processOneWord(int i, String answer) throws InvalidPositionException, GameOverException {
        askForLetterInputProcessResult(currentCorrectWord, currentQuestionWord, i, answer);
    }

    // MODIFIES: this
    // EFFECTS: if the position <= this.myThisQuestionBrokenWord.size(), check user's answer
    //          if the position is invalid, throw InvalidPositionException
    public void askForLetterInputProcessResult(List<String> myThisBrokenCorrectWord,
                                               List<String> myThisQuestionBrokenWord,
                                               int position,
                                               String answer) throws InvalidPositionException, GameOverException {
        position = checkPosition(position, myThisQuestionBrokenWord);
        if (!(position == -1)) {
            int indexFromPos = position - 1;
            checkAnswer(myThisBrokenCorrectWord, myThisQuestionBrokenWord,
                    indexFromPos, answer);
            checkResultAtEnd(myThisQuestionBrokenWord, myThisBrokenCorrectWord);
        } else {
            throw new InvalidPositionException();
        }
    }

    // REQUIRES: at least 1 string is in this listOfString
    // MODIFIES: int i
    // EFFECTS: check if position int i is acceptable for this broken Word
    //          if position i is less or equal to zero, or position i is larger than the maximum index of the list,
    //          or the letter at position i (index i-1) is not a question position, return -1
    //          if position i is a question position, return position i
    public int checkPosition(int i, List<String> listOfString) {
        while (i <= 0 || i > listOfString.size()  || !listOfString.get(i - 1).equals("?")) {
            return -1;
        }
        return i;
    }

    // REQUIRES: indexFromPos <= this.myThisQuestionBrokenWord.size()
    //           answer should be a single letter string
    // MODIFIES: this
    //           List<String> myThisBrokenWord
    // EFFECTS: compare with myThisBrokenCorrectWord, check if String at index indexFromPos is same as
    //          String answer
    //          if true, set index indexFromPos of myThisBrokenWord to String answer
    //          if false, move this Ball horizontally for 1 unit
    //          finally, compose print current state of myThisBrokenWord
    public void checkAnswer(List<String> myThisBrokenCorrectWord, List<String> myThisBrokenWord,
                            int indexFromPos, String answer) {
        if (myThisBrokenCorrectWord.get(indexFromPos).equals(answer)) {
            myThisBrokenWord.set(indexFromPos, answer);
        } else {
            ball.moveHorizontally();
        }
        currentQuestionWord = myThisBrokenWord;
        System.out.println(String.join("", myThisBrokenWord));
    }


    // REQUIRES: int i <= this.myWordList.size() - 1
    // EFFECTS: find the word in myWordList with index int i, cover
    //          random letters in this word with question marks, and return that word
    public Word getThisComposedWord(int i) {
        Word myThisWord = myWordList.getWord(i).composeWord();
        return myThisWord;
    }

    // MODIFIES: this
    // EFFECTS: if the ball is ready to fall, which means that there is no chance left for this round,
    //          let the ball from the table, throw GameOverException
    //          if there is no question position in brokenWord, set this
    //          ball back to initial position
    //          if none of conditions above is met, do nothing
    public void checkResultAtEnd(List<String> brokenWord, List<String> brokenCorrectWord) throws GameOverException {
        if (ball.readyToFall()) {
            ball.ballFall();
            throw new GameOverException();
        } else if (!brokenWord.contains("?")) {
            ball.setIniX();
        }
    }

    // EFFECTS: saves the SpellingGame status to file
    public void saveSpellingGame(int i, ListOfWords low, Word w) {
        try {
            jsonWriter.open();
            jsonWriter.write(ball, w, low.getSubList(i, low.length()));
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: loads the SpellingGame status to file
    public void loadSpellingGame() {
        try {
            ball = jsonReader.readBall();
            firstWord = jsonReader.readWord();
            myWordList = jsonReader.readListOfWord();
            System.out.println("Loaded spellingGame from " + JSON_STORE);
            //processLoadWord();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: return the Ball of this game state
    public Ball getBall() {
        return ball;
    }

    // EFFECTS: return the Table of this game state
    public Table getTable() {
        return table;
    }

    // EFFECTS: return the CurrentQuestion of this game state
    public String getCurrentQuestion() {
        return String.join("", currentQuestionWord);
    }

    // EFFECTS: return the CurrentAnswer of this game state
    public String getCurrentAnswer() {
        return String.join("", currentCorrectWord);
    }

    // EFFECTS: return the length of this.myWordList
    public int getLength() {
        return myWordList.length();
    }

    // EFFECTS: return the FirstWord of this game state
    public Word getFirstWord() {
        return firstWord;
    }

    // EFFECTS: return myWordList of this game state
    public ListOfWords getMyWordList() {
        return myWordList;
    }

    // MODIFIES: this
    // EFFECTS: set setCurrentCorrectWord to parameter setCurrentCorrectWord
    public void setCurrentCorrectWord(List<String> currentCorrectWord) {
        this.currentCorrectWord = currentCorrectWord;
    }

    // MODIFIES: this
    // EFFECTS: set currentQuestionWord to parameter currentQuestionWord
    public void setCurrentQuestionWord(List<String> currentQuestionWord) {
        this.currentQuestionWord = currentQuestionWord;
    }

    // MODIFIES: this
    // EFFECTS: set myWordList to parameter low
    public void setMyWordList(ListOfWords low) {
        myWordList = low;
    }

    // MODIFIES: this
    // EFFECTS: set firstWord to parameter firstWord
    public void setFirstWord(Word firstWord) {
        this.firstWord = firstWord;
    }

    // MODIFIES: return true if the ball is falling
    //           return false otherwise
    public boolean isGameOver() {
        return ball.getYCood() > ball.getYIni();
    }
}
