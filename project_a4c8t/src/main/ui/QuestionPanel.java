package ui;

import javax.swing.*;
import java.awt.*;

// The panel where the question word rendered

public class QuestionPanel extends JPanel {
    private JLabel questionPos;
    private GameState gameState;
    private int index;

    // Construct a panel where the question word rendered
    public QuestionPanel(GameState gameState, int index) {
        this.index = index;
        this.gameState = gameState;
        String question;
        if (!(gameState.getFirstWord() == null)) {
            question = gameState.getCurrentQuestion();
        } else {
            question = gameState.processListOfWord(index);
        }
        questionPos = new JLabel(question);
        questionPos.setLayout(new FlowLayout());
        questionPos.setVisible(true);
        questionPos.setOpaque(true);
        add(questionPos);

        setLayout(new FlowLayout());
        setVisible(true);


    }
}
