package ui;

import exception.GameOverException;
import exception.InvalidPositionException;
import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

// The window where the game rendered

public class GamePanel extends JFrame implements ActionListener {
    private JButton save;
    private QuestionPanel questionPanel;
    private BallPanel ballPanel;
    private JLabel position;
    private JLabel answer;
    private JTextField inputPosition;
    private JTextField inputAnswer;
    private JButton submit;
    private GameState gameState;
    private Background background;
    private int index;
    private static final String JSON_STORE = "./data/spellingGame.json";
    private JsonWriter jsonWriter;
    private EventPrinter eventPrinter;

    // Construct the main window where the game rendered
    public GamePanel(GameState gameState, int index) {
        super("Spelling Game");
        this.gameState = gameState;
        this.index = index;
        background = new Background();
        jsonWriter = new JsonWriter(JSON_STORE);
        save = new JButton("Save Game");
        saveButton(gameState, index);
        processLoadGame(gameState);
        questionPanel = new QuestionPanel(gameState, index);
        add(questionPanel);
        ballPanel = new BallPanel(gameState);
        add(save);
        add(ballPanel);
        initializeAnswerPanel();
        initializeLabel();
        add(answer);
        add(inputAnswer);
        add(submit);
        setLayout(new FlowLayout());
        setVisible(true);
        setSize(background.getHeight(), background.getWidth());
        eventPrinter = new EventPrinter(this);
        eventPrinter.printEvent();
    }

    // MODIFIES: this
    // EFFECTS: initialize and add the two JLabel with test on it
    private void initializeLabel() {
        position = new JLabel("Enter Position: ");
        answer = new JLabel("Enter Letter: ");

        add(position);
        add(inputPosition);
    }

    // MODIFIES: this
    // EFFECTS: initialize the answer area with two JTestField and one JButton
    //          add action listeners to all three components
    private void initializeAnswerPanel() {
        inputPosition = new JTextField(10);
        inputAnswer = new JTextField(10);
        submit = new JButton("Submit");

        inputPosition.addActionListener(this);
        inputAnswer.addActionListener(this);
        submit.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: if the game is load (not new), load everything from JsonReader to current GamePanel
    //          if the game is new, shuffle the wordlist to prepare for the game
    private void processLoadGame(GameState gameState) {
        if (!(gameState.getFirstWord() == null)) {
            List<String> loadCurrectQuestionWord = gameState.getFirstWord().breakWord();
            gameState.setCurrentQuestionWord(loadCurrectQuestionWord);
            List<String> loadCurrentAnswerWord = gameState.getMyWordList().getWords().get(0).breakWord();
            gameState.setCurrentCorrectWord(loadCurrentAnswerWord);
            ListOfWords loadCurrentListOfWords = gameState.getMyWordList();
            gameState.setMyWordList(loadCurrentListOfWords);
        } else {
            gameState.getMyWordList().randomizeWords();
        }
    }

    // MODIFIES: this
    // EFFECTS: add action listener to the save button by calling the JsonWriter
    //          write the current game state to the saving location
    private void saveButton(GameState gameState, int index) {
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Word firstWord = new Word(String.join("", gameState.getCurrentQuestion()));
                try {
                    jsonWriter.open();
                    jsonWriter.write(gameState.getBall(),
                            firstWord,
                            gameState.getMyWordList().getSubList(index, gameState.getMyWordList()
                                    .length()));
                    jsonWriter.close();
                } catch (FileNotFoundException ea) {
                    System.out.println("Unable to write to file: " + JSON_STORE);
                }
            }
        });
    }


    // MODIFIES: this
    // EFFECTS: override the actionPerformed method from actionListener
    //          if the current question word still contains "?", process input to continue the game;
    //          if the current question word does not contain "?", figure out the game state and end this round
    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("Submit")) {
            if (gameState.getCurrentQuestion().contains("?")) {
                processInput();

            } else {
                endOneRound();
            }

        }
    }

    // MODIFIES: this
    // EFFECTS: if the wordlist is not finished, display the next word in the list
    //          if the wordlist is finished, end the game
    private void endOneRound() {
        dispose();
        showMessageDialog(null, "You have saved Spelling Ball!");
        //index = index + 1;
        if (gameState.getLength() > 1) {
            if (!(gameState.getFirstWord() == null)) {
                gameState.setFirstWord(null);
                //index = index - 1;
                gameState.setMyWordList(gameState.getMyWordList().getSubList(1, gameState.getLength()));
            } else {
                ListOfWords newRoundWordList = gameState.getMyWordList().getSubList(1,gameState.getLength());
                gameState.setMyWordList(newRoundWordList);
            }
            GamePanel gamePanel = new GamePanel(gameState, index);
        } else {
            showMessageDialog(null, "Your word-list is finished! Thanks for using!");
            for (Event e : EventLog.getInstance()) {
                System.out.println(e.toString() + "\n\n");
            }
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: process user's input and update the game panel
    //          if the position text field got a non-integer input, catch the NumberFormatException
    //          if the position is not a question position, catch the InvalidPositionException
    //          if the ball fall, catch the GameOverException and end the game
    private void processInput() {
        try {
            gameState.processOneWord(Integer.parseInt(inputPosition.getText()), inputAnswer.getText());
            questionPanel.removeAll();
            questionPanel.revalidate();
            JLabel label = new JLabel("");
            label.setText(gameState.getCurrentQuestion());
            questionPanel.update(questionPanel.add(label).getGraphics());
            questionPanel.repaint();

            ballPanel.repaint();
        } catch (NumberFormatException ea) {
            showMessageDialog(null, "An integer is expected for position!");
        } catch (InvalidPositionException ea) {
            showMessageDialog(null, "Invalid position!");
        } catch (GameOverException ea) {
            showMessageDialog(null, "Oooops! The ball falls! Game Over!");
            showMessageDialog(null,
                    "Answer for this question is: " + gameState.getCurrentAnswer());
            dispose();
            for (Event e : EventLog.getInstance()) {
                System.out.println(e.toString() + "\n\n");
            }
            System.exit(0);
        }
    }
}
