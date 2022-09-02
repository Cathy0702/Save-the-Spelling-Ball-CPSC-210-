package persistence;

import model.Ball;
import model.ListOfWords;
import model.Word;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.*;

// Represents a writer that writes JSON representation of SpellingGame to file
// This Class references code from: JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of SpellingGame to file
    public void write(Ball b, Word w, ListOfWords low) {
        JSONObject json = b.toJson();
        JSONObject json1 = w.toJson();
        JSONObject json2 = low.toJson();

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(json);
        jsonArray.put(json1);
        jsonArray.put(json2);

        JSONObject jsonF = new JSONObject();
        jsonF.put("spelling game", jsonArray);

        saveToFile(jsonF.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
