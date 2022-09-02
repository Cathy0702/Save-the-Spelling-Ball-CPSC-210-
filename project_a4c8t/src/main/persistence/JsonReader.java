package persistence;

import model.Ball;
import model.ListOfWords;
import model.Word;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads SpellingGame from JSON data stored in file
// This Class references code from: JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ball from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Ball readBall() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBall(jsonObject);
    }

    // EFFECTS: reads Word from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Word readWord() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWord(jsonObject);
    }

    // EFFECTS: reads ListOfWord from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ListOfWords readListOfWord() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseListOfWord(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Ball from JSON object and returns it
    private Ball parseBall(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("spelling game");
        JSONObject ballNum = (JSONObject) jsonArray.get(0);

        int x = ballNum.getInt("xcoordBall");
        int y = ballNum.getInt("ycoordBall");

        Ball ball = new Ball();
        ball.setXandY(x, y);
        return ball;
    }

    // EFFECTS: parses Word from JSON object and returns it
    private Word parseWord(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("spelling game");
        JSONObject theFirstWord = (JSONObject) jsonArray.get(1);

        String firstWord = theFirstWord.getString("word");

        Word newFirstWord = new Word(firstWord);
        return newFirstWord;
    }

    // EFFECTS: parses ListOfWords from JSON object and returns it
    private ListOfWords parseListOfWord(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("spelling game");
        JSONObject listOfWordMemory = (JSONObject) jsonArray.get(2);
        JSONArray listOfWordMemory2 = listOfWordMemory.getJSONArray("listOfWord");
        ListOfWords newListOfWord = new ListOfWords();

        for (Object json: listOfWordMemory2) {
            JSONObject nextWordObject = (JSONObject) json;
            String nextWordString = nextWordObject.getString("word");
            Word nextWord = new Word(nextWordString);
            newListOfWord.addWord(nextWord);
        }

        return newListOfWord;
    }

}