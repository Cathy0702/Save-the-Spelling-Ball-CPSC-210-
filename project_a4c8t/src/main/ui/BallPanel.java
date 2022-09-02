package ui;

import model.Background;
import model.Ball;
import model.Table;

import javax.swing.*;
import java.awt.*;

// The place where ball and table are rendered
// This Class references code from: SpaceInvader
// Link: https://edge.edx.org/assets/courseware/v1/13595daba554c85e2fe669e686cbff91/
//       asset-v1:UBC+CPSC210+all+type@asset+block/SpaceInvadersStarter.zip

public class BallPanel extends JPanel {
    private GameState gameState;
    private Background background;
    private static final Color COLOR = new Color(167, 232, 220);

    // Construct a ballPanel with fixed size, color, and given gameState
    public BallPanel(GameState gameState) {
        setPreferredSize(new Dimension(background.getWidth(), 400));
        setBackground(COLOR);
        setLayout(new FlowLayout());
        setVisible(true);
        this.gameState = gameState;
        background = new Background();
    }

    // MODIFIES: g
    // EFFECTS: paints game components on this JPanel object
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBall(g);
        drawTable(g);


    }

    // MODIFIES: g
    // EFFECTS: paints Ball components on this JPanel object
    public void drawBall(Graphics g) {
        g.setColor(gameState.getBall().getColor());
        g.fillOval(gameState.getBall().getXCood() - gameState.getBall().getRadius(),
                gameState.getBall().getYCood(),
                gameState.getBall().getRadius() * 2, gameState.getBall().getRadius() * 2);
    }

    // MODIFIES: g
    // EFFECTS: paints Table components on this JPanel object
    public void drawTable(Graphics g) {
        Table table = gameState.getTable();
        g.setColor(table.getColor());
        g.fillRect(table.getXPos() - table.getWidth() / 2,
                table.getYPos(), table.getWidth(), table.getHeight());
    }

}
