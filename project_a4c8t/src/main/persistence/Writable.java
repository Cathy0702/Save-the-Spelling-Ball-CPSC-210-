package persistence;

import org.json.JSONObject;

public interface Writable {

    // EFFECTS: returns this as JSON object
    // This Class references code from: JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    JSONObject toJson();
}
