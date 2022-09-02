package model;

import java.awt.*;

//Represent a table with fixed height, width, x y coordinates, and color

public class Table {

    private static final int HEIGHT  = 20;
    private static final int WIDTH = 200;
    private static final int X_POS = Background.getWidth() / 2;
    private static final int Y_POS = Background.getHeight() - 300;
    private static final Color COLOR = new Color(30, 144, 194);

    // EFFECTS: construct a table
    public Table() {
    }

    // EFFECTS: return the fixed height of the table
    public static int getHeight() {
        return HEIGHT;
    }

    // EFFECTS: return the fixed width of the table
    public static int getWidth() {
        return WIDTH;
    }

    // EFFECTS: return the fixed x coordinate of the table
    public static int getXPos() {
        return X_POS;
    }

    // EFFECTS: return the fixed y coordinate of the table
    public static int getYPos() {
        return Y_POS;
    }

    // EFFECTS: return the color of the table
    public static Color getColor() {
        return COLOR;
    }

}
