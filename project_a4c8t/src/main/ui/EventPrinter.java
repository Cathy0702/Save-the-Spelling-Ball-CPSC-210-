package ui;

import model.Event;
import model.EventLog;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// An event printer

public class EventPrinter {
    JFrame jframe;

    // Construct an event printer
    public EventPrinter(JFrame jframe) {
        this.jframe = jframe;
    }

    // MODIFIES: this
    // EFFECTS: set the default close operation for this jframe, then print all events in eventLog
    //          reset the default close operation
    public void printEvent() {
        jframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        jframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                for (Event ev : EventLog.getInstance()) {
                    System.out.println(ev.toString() + "\n\n");
                }
                jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }
}
