package model;

// Represent a background of the game with fixed height and width

public class Background {

    private static final int HEIGHT = 601;
    private static final int WIDTH = 601;

    // EFFECTS: construct a background
    public Background() {

    }

    // EFFECTS: return the fixed height of the background
    public static int getHeight() {
        return HEIGHT;
    }

    // EFFECTS: return the fixed width of the background
    public static int getWidth() {
        return WIDTH;
    }
}
