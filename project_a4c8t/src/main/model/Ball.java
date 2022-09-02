package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;

// Represent a ball with changeable x and y coordinates

public class Ball implements Writable {

    private static final int RADIUS = 20;
    private static final int DX = 20;
    private static final int DY = 3;
    private static final int X_INI = Background.getWidth() / 2;
    private static final int Y_INI = 260;
    private static final int READY_TO_FALL = X_INI + Table.getWidth() / 2;
    private static final Color COLOR = new Color(238, 0, 0);

    private int xcoord;
    private int ycoord;


    // EFFECTS: construct a ball with initial x and y coordinates
    public Ball() {
        xcoord = X_INI;
        ycoord = Y_INI;
    }

    // REQUIRES: current x coordinate of the ball should be less than or equal to the edge of table
    // MODIFIES: this
    // EFFECTS: if the ball is not on the edge of table, move one unit to the edge, and print the total chance left
    //          if the ball is on the edge of table, do nothing
    public void moveHorizontally() {
        if (!readyToFall()) {
            xcoord = xcoord + DX;
            int chanceLeft = chanceLeft();
            System.out.println("The ball is moving for 1 unit!");
            System.out.println("Now you have " + chanceLeft + " chances left.");
        }
    }

    // REQUIRES: x coordinate of the ball should be less than or equal to the table edge
    //           the ball should start from X_INI and move DX each time
    // EFFECTS: return number of times the ball need to move to the table edge if it moves DX each time
    //          if the ball is at X_INI without moving, return 5
    //          if the ball is at the table edge, return 0
    public int chanceLeft() {
        int chanceLeft = (Table.getXPos() + (Table.getWidth() / 2) - xcoord) / DX;
        return chanceLeft;
    }

    // MODIFIES: this
    // EFFECTS: move the ball one unit down
    public void moveVertically() {
        ycoord = ycoord + DY;
    }

    // REQUIRES: the ball is on the edge of table
    // MODIFIES: this
    // EFFECTS: move the ball one radius out of the edge, then move down until completely
    //          disappear from the background
    public void ballFall() {
        xcoord += RADIUS;
        while (ycoord >= 0 - RADIUS) {
            moveVertically();
        }
        System.out.println("Oooops, the ball falls!");
    }

    // REQUIRES: x coordinate of the ball should be less than or equal to the edge of table
    // EFFECTS: check whether the ball is on the edge of table
    public boolean readyToFall() {
        return chanceLeft() == 0;
    }

    // MODIFIES: this
    // EFFECTS: put the ball back to its original x coordinate; if the ball is at the original x coordinate,
    //          do nothing
    public void setIniX() {
        xcoord = X_INI;
    }

    // MODIFIES: this
    // EFFECTS: set x coordinate of the ball to int x, set y coordinate of the ball to int y
    public void setXandY(int x, int y) {
        xcoord = x;
        ycoord = y;
    }

    // EFFECTS: return the current x coordinate of the ball
    public int getXCood() {
        return xcoord;
    }

    // EFFECTS: return the current y coordinate of the ball
    public int getYCood() {
        return ycoord;
    }

    // EFFECTS: return the distance of horizontal moving each time
    public int getDx() {
        return DX;
    }

    // EFFECTS: return the distance of vertical moving each time
    public int getDy() {
        return DY;
    }

    // EFFECTS: return the initial x coordinate of the ball
    public int getXIni() {
        return X_INI;
    }

    // EFFECTS: return the initial y coordinate of the ball
    public int getYIni() {
        return Y_INI;
    }

    // EFFECTS: return the radius of the ball
    public int getRadius() {
        return RADIUS;
    }

    // EFFECTS: return the position of the table edge, which is the position of the ball ready to fall
    public int getFallingEdge() {
        return READY_TO_FALL;
    }

    // EFFECTS: return the color of the ball
    public Color getColor() {
        return COLOR;
    }


    // EFFECTS: return a Json Object with ball stored
    // This Method references code from: JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("xcoordBall", xcoord);
        json.put("ycoordBall", ycoord);
        return json;
    }


}
