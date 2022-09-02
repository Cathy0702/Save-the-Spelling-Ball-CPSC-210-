package ui;

import model.*;
import model.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.JOptionPane.showMessageDialog;

// The window where the user's wordlist is constructed

public class WordPanel extends JFrame implements ActionListener {
    private JTextField inputWord;
    private JTextArea outputWord;
    private JButton finish;
    private ListOfWords listOfInput;
    private Background background;
    private GameState gameState;
    private EventPrinter eventPrinter;

    // Construct a wordPanel with an empty input area, an empty output area and a submit button
    public WordPanel() {
        super("Add Your Words!");
        listOfInput = new ListOfWords();

        setLayout(new FlowLayout());
        setVisible(true);
        setSize(background.getHeight(), background.getWidth());
        eventPrinter = new EventPrinter(this);
        eventPrinter.printEvent();

        background = new Background();
        inputWord = new JTextField(50);
        inputWord.addActionListener(this);
        outputWord = new JTextArea(25, 50);
        outputWord.setEditable(false);

        add(inputWord);
        add(outputWord);

        finish = new JButton("Finish Adding");
        finishButton();
        add(finish);

    }

    // MODIFIES: this
    // EFFECTS: add action listener to the finish button
    //          if wordList is valid, construct a new gamePanel
    //          if wordList is empty, catch the IndexOutOFBoundsException
    private void finishButton() {
        finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameState = new GameState(new Ball(), null, listOfInput);
                try {
                    new GamePanel(gameState, 0);
                    dispose();
                } catch (IndexOutOfBoundsException ea) {
                    showMessageDialog(null, "Cannot start with an empty word-list!");
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: override the actionPerformed method from actionListener
    //          add the input word to the word list
    //          render the output words
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!inputWord.getText().isEmpty()) {
            String input = inputWord.getText();
            String newLine = "\n";
            String initialInput = "";
            Word newWord = new Word(input);
            listOfInput.addWord(newWord);
            outputWord.append(input + newLine);
            inputWord.setText(initialInput);
        } else {
            showMessageDialog(null, "Cannot enter an empty word!");
        }
    }

}