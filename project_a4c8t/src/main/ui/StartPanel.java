package ui;

import model.Background;
import model.Ball;
import model.ListOfWords;
import model.Word;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// The starting panel of the Spelling Game

public class StartPanel extends JFrame {

    private Background background;
    private JLabel title;
    private JLabel title2;
    private JLabel title3;
    private JButton newGame;
    private JButton loadGame;
    private static final String JSON_STORE = "./data/spellingGame.json";
    private JsonReader jsonReader;
    private GameState gameState;
    private EventPrinter eventPrinter;

    // Construct a starting panel of the Spelling Game
    public StartPanel() {
        super("Spelling Ball");
        initializeTitle();
        newGame = new JButton("New Game");
        loadGame = new JButton("Load Game");
        jsonReader = new JsonReader(JSON_STORE);
        background = new Background();

        newGameButton();

        loadGameButton();

        add(title);
        add(title2);
        add(title3);
        add(newGame);
        add(loadGame);
        setLayout(new FlowLayout());
        setVisible(true);
        setSize(background.getHeight(), background.getWidth());
        eventPrinter = new EventPrinter(this);
        eventPrinter.printEvent();
    }

    // MODIFIES: this
    // EFFECTS: initialize the title of the game with proper location and font
    private void initializeTitle() {
        title = new JLabel("Save the");
        title2 = new JLabel("Spelling");
        title3 = new JLabel("Ball!");
        title.setFont(new Font("Verdana", Font.PLAIN, 80));
        title2.setFont(new Font("Verdana", Font.PLAIN, 80));
        title3.setFont(new Font("Verdana", Font.PLAIN, 80));
    }

    // MODIFIES: this
    // EFFECTS: load the game; read the game state from the saving location
    private void loadGameButton() {
        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Ball ball = jsonReader.readBall();
                    Word firstWord = jsonReader.readWord();
                    ListOfWords myWordList = jsonReader.readListOfWord();
                    gameState = new GameState(ball, firstWord, myWordList);
                    GamePanel gamePanel = new GamePanel(gameState, 0);
                    dispose();
                } catch (IOException ex) {
                    System.out.println("Unable to read from file: " + JSON_STORE);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: add the action listener to the newGame button
    //          call the wordPanel to start a new game
    private void newGameButton() {
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WordPanel wordPanel = new WordPanel();
                dispose();
            }
        });
    }
}
