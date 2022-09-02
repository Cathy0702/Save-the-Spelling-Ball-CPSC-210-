package ui;

import model.Ball;
import model.ListOfWords;
import model.Table;
import model.Word;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


// Represent a SpellingBall app

public class SpellingApp {
    ListOfWords myWordList;
    private Scanner scan;
    private Ball ball;
    private Table table;
    private Boolean keepGoing;
    private static final String JSON_STORE = "./data/spellingGame.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Word firstWord;

    // EFFECTS: construct a SpellingBall app
    public SpellingApp() {
        scan = new Scanner(System.in);
        ball = new Ball();
        table = new Table();
        keepGoing = true;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        firstWord = null;
        runSpelling();
    }

    // EFFECTS: runs the SpellingBall application
    private void runSpelling() {

        while (keepGoing) {
            displayMenu();
            Scanner scan3 = new Scanner(System.in);
            String choice = scan3.nextLine();
            if (choice.equals("q")) {
                keepGoing = false;
            } else if (choice.equals("n")) {
                processNewGameChoice();
            } else if (choice.equals("l")) {
                loadSpellingGame();
            }

        }

        System.out.println("Thanks for using!");


    }


    // MODIFIES: this
    // EFFECTS: initiate a new game by generating and processing the random wordlist
    private void processNewGameChoice() {
        welcomingMessage();
        askForInputWord();
        myWordList.randomizeWords();

        instructionBeforeQuestion();
        System.out.println("Ready? Let's begin!");
        processListOfWord();

    }

    // EFFECTS: display menu
    private void displayMenu() {
        System.out.println("n  ->  Start a new game");
        System.out.println("l  ->  Load the saved game");
        System.out.println("q  ->  Quit");
    }

    // EFFECTS: print the welcome before running the whole program
    private void welcomingMessage() {
        System.out.print("Hi, welcome to use the SpellingBall app! ");
        System.out.println("We are here to help you memorizing words!");
        printLine();
    }

    // EFFECTS: print the instruction before display Question 1
    private void instructionBeforeQuestion() {
        printLine();
        System.out.println("You will get a random word from your word-list with some letters covered.");
        System.out.println("Please follow the instruction: ");
        System.out.print("First, enter the position of the letter you wish to enter; ");
        System.out.println("Then, enter the answer of that position. ");
        System.out.print("For example, if the covered word you get is 'c??', ");
        System.out.println("you can enter '2', then 'a', and if correct, you will get 'ca?'.");
        System.out.print("Everytime you are correct, the ball stays at its original position; ");
        System.out.println("Everytime you are incorrect, the ball moves to the edge for one unit.");
        System.out.println("You have 5 wrong chances for EACH word, but if the ball falls, the game will end.");
        printLine();
    }

    // MODIFIES: this
    // EFFECTS: process user input; display each question, collect, check, and process acceptable position
    //          and answer from user
    private void processListOfWord() {
        for (int i = 0; i < myWordList.length(); i++) {
            while (keepGoing == true) {
                Word myThisCorrectWord = myWordList.getWord(i);
                List<String> myThisBrokenCorrectWord = myThisCorrectWord.breakWord();

                Word myThisQuestion = getThisComposedWord(i);
                String myThisQuestionString = myThisQuestion.getWord();
                List<String> myThisQuestionBrokenWord = myThisQuestion.breakWord();

                printQuestion(myThisQuestionString);

                processOneWord(i, myThisBrokenCorrectWord, myThisQuestionBrokenWord);
                break;
            }

        }
    }


    // MODIFIES: this
    // EFFECTS: if position input is -1, quit the game
    //          if position input is 0, save the game status
    //          if position input is others, process one word from the user input word-list; collect acceptable
    //          position and answer from user,
    //          check answer with the correct answer, and decide to end or continue the game
    private void processOneWord(int i, List<String> myThisBrokenCorrectWord, List<String> myThisQuestionBrokenWord) {
        while (ball.chanceLeft() >= 1 && myThisQuestionBrokenWord.contains("?")) {
            int position = askForInputPosition();
            if (position == -1) {
                keepGoing = false;
                break;
            } else if (position == 0) {
                String myQuestionWordThisRound = String.join("", myThisQuestionBrokenWord);
                Word correctWord = new Word(myQuestionWordThisRound);
                saveSpellingGame(i, myWordList, correctWord);
            } else {
                askForLetterInputProcessResult(myThisBrokenCorrectWord, myThisQuestionBrokenWord, position);
            }
        }

    }

    // MODIFIES: this
    // EFFECTS: process position input and letter input, check answer with the correct answer, and decide to
    //          end or continue the game
    private void askForLetterInputProcessResult(List<String> myThisBrokenCorrectWord,
                                                List<String> myThisQuestionBrokenWord, int position) {
        position = checkPosition(position, myThisQuestionBrokenWord);
        int indexFromPos = position - 1;

        String answer = askForInputAnswer();
        printLine();

        checkAnswer(myThisBrokenCorrectWord, myThisQuestionBrokenWord,
                indexFromPos, answer);

        checkResultAtEnd(myThisQuestionBrokenWord, myThisBrokenCorrectWord);
        printLine();
    }

    // EFFECTS: print the word with random letters cover with question marks
    //          print two lines
    private void printQuestion(String s) {
        printLine();
        System.out.println(s);
        printLine();
    }

    // REQUIRES: at least 1 string is in this listOfString
    // MODIFIES: int i
    // EFFECTS: check if position int i is acceptable for this broken Word
    //          if position i is less or equal to zero, or position i is larger than the maximum index of the list,
    //          or the letter at position i (index i-1) is not a question position, print sorry message,
    //          and ask for a new position until position i is a question position, return new position i
    //          if position i is a question position, return position i
    private int checkPosition(int i, List<String> listOfString) {
        while (i <= 0 || i > listOfString.size() - 1 || !listOfString.get(i - 1).equals("?")) {
            System.out.println("Sorry, this is not a question position; please enter your position again!");
            printLine();
            i = askForInputPosition();
        }
        return i;
    }

    // REQUIRES: myThisBrokenCorrectWord and myThisBrokenWord should be two lists that only contain single
    //           letter strings; they should have same length
    //           indexFromPos should be less or equal to the maximum index of the two lists of String
    //           answer should be a single letter string
    // MODIFIES: this
    //           List<String> myThisBrokenWord
    // EFFECTS: compare with myThisBrokenCorrectWord, check if String at index indexFromPos is same as
    //          String answer
    //          if true, print correct message, set index indexFromPos of myThisBrokenWord to String answer
    //          if false, move this Ball horizontally for 1 unit
    //          finally, compose print current state of myThisBrokenWord
    private void checkAnswer(List<String> myThisBrokenCorrectWord, List<String> myThisBrokenWord,
                             int indexFromPos, String answer) {
        if (myThisBrokenCorrectWord.get(indexFromPos).equals(answer)) {
            System.out.println("Correct!");
            myThisBrokenWord.set(indexFromPos, answer);
        } else {
            System.out.println("Incorrect!");
            ball.moveHorizontally();
        }
        System.out.println(String.join("", myThisBrokenWord));
    }

    // MODIFIES: this
    // EFFECTS: if the ball is ready to fall, which means that there is no chance left for this round,
    //          let the ball from the table, print the correct word and exit the game
    //          if there is no question position in brokenWord, print congrats message and set this
    //          ball back to initial position
    //          if none of conditions above is met, do nothing
    private void checkResultAtEnd(List<String> brokenWord, List<String> brokenCorrectWord) {
        if (ball.readyToFall()) {
            ball.ballFall();
            printLine();
            System.out.println("The answer is: ");
            System.out.println(String.join("", brokenCorrectWord));
            System.exit(0);
        } else if (!brokenWord.contains("?")) {
            System.out.println("Perfect, you got that word and save the SpellingBall!");
            ball.setIniX();
        }
    }

    // REQUIRES: int i should be less than or equal to the maximum index of myWordList
    // EFFECTS: print the question number, and find the word in myWordList with index int i, cover
    //          random letters in this word with question marks, and return that word
    private Word getThisComposedWord(int i) {
        //int questionNum = i + 1;
        System.out.println("Question " + ": ");
        Word myThisWord = myWordList.getWord(i).composeWord();
        return myThisWord;
    }

    // EFFECTS: get and return a user input int position
    private int askForInputPosition() {
        System.out.println("Enter the position of your answer:");
        System.out.println("(enter 0 to save current game status, enter -1 to quit the game)");
        try {
            Scanner scan5 = new Scanner(System.in);
            int position = scan5.nextInt();
            return position;
        } catch (InputMismatchException e) {
            System.out.println("This is not an Integer!");
            int position = askForInputPosition();
            return position;
        }
    }

    // EFFECTS: get and return a user input String answer
    private String askForInputAnswer() {
        System.out.println("Enter your answer:");
        Scanner scan2 = new Scanner(System.in);
        String answer = scan2.nextLine();

        return answer;
    }

    // MODIFIES: this
    // EFFECTS: repeatedly ask user to input words and add them into myWordList
    //          if user inputs "0", stop asking and end this method
    //          if user inputs an empty string, print sorry message, do not add anything
    //          to myWordList, and ask for input again
    private void buildWordList() {
        String string = "";
        while (!string.equals("0")) {
            System.out.println("Add your word:");
            string = scan.nextLine();
            if (string.equals("")) {
                System.out.println("Sorry, cannot put in an empty word!");
            } else if (!string.equals("0")) {
                Word word = new Word(string);
                myWordList.addWord(word);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: print the instruction of word-list building, and build a complete word-list from the user
    private void askForInputWord() {
        myWordList = new ListOfWords();

        System.out.println("Now start to build your word-list! Enter '0' to finish entering.");
        buildWordList();

    }

    // EFFECTS: print a line
    private void printLine() {
        System.out.println("__________________________________________________");
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

    // MODIFIES: this
    // EFFECTS: process the first loaded word by get the question and correct answer from loading, collecting
    //          user's answer until this word finished, stop the game or continue with rest of the list
    public void processLoadWord() {
        Word myCurrentCorrectWord = myWordList.getWord(0);

        String myThisQuestionString = firstWord.getWord();
        List<String> myThisQuestionBrokenWord = firstWord.breakWord();
        List<String> myThisCorrectBrokenWord = myCurrentCorrectWord.breakWord();

        printQuestion(myThisQuestionString);

        while (ball.chanceLeft() >= 1 && myThisQuestionBrokenWord.contains("?")) {
            int position = askForInputPosition();
            if (position == -1) {
                keepGoing = false;
                break;
            } else if (position == 0) {
                String myQuestionWordThisRound = String.join("", myThisQuestionBrokenWord);
                Word correctWord = new Word(myQuestionWordThisRound);
                saveSpellingGame(0, myWordList, correctWord);
            } else {
                askForLetterInputProcessResult(myThisCorrectBrokenWord, myThisQuestionBrokenWord, position);
            }
            if (!myThisQuestionBrokenWord.contains("?")) {
                myWordList = myWordList.getSubList(1, myWordList.length());
            }
        }
        processListOfWord();
    }

    // EFFECTS: loads the SpellingGame status to file
    public void loadSpellingGame() {
        try {
            ball = jsonReader.readBall();
            firstWord = jsonReader.readWord();
            myWordList = jsonReader.readListOfWord();
            System.out.println("Loaded spellingGame from " + JSON_STORE);
            processLoadWord();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
