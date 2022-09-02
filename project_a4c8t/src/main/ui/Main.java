package ui;

// where the code execute

import model.Event;
import model.EventLog;

public class Main {
    public static void main(String[] args) {
        EventLog.getInstance();

        new GameWorld();
        //new SpellingApp();

        for (Event e : EventLog.getInstance()) {
            System.out.println(e.toString() + "\n\n");
        }

    }
}
