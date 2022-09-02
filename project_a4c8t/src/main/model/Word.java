package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Represent a word

public class Word implements Writable {

    private String word;

    // EFFECTS: construct a word
    public Word(String theWord) {
        word = theWord;
        EventLog.getInstance().logEvent(new Event("A new word: " + theWord + " is constructed."));
    }

    // EFFECTS: return the String word of this word
    public String getWord() {
        return word;
    }

    // MODIFIES: this
    // EFFECTS: set the String word of this word to String s (change this word)
    public void setWord(String s) {
        word = s;
        EventLog.getInstance().logEvent(new Event("The word is set to: " + s + "."));
    }

    // REQUIRES: the Word should not have an empty string as its word field
    // EFFECTS: return a list of String, that each string is corresponding with a single letter in this Word; the
    //          number and the sequence of single letter strings in the list of string should be same as the
    //          number and the sequence of the letters in this original Word
    public List<String> breakWord() {
        List<String> listOfLetter = new ArrayList<>();

        for (int i = 0; i < word.length(); i++) {
            listOfLetter.add(String.valueOf(word.charAt(i)));
        }
        return listOfLetter;
    }

    // REQUIRES: the Word should not have an empty string as its word field
    // EFFECTS: return a list of single letter String, that each string is corresponding with a single
    //          letter in this Word with random letter replace by "?";
    //          if the Word is a single String, no "?" should be added in
    //          if the Word has letters more than 1, the number of "?" should be more than or equal
    //          to 1, but not exceed the number of letters in this original word minus 1
    public List<String> coverWord() {
        List<String> listOfLetter = breakWord();

        List<Integer> listOfRandomInt = new ArrayList<>();
        Random rand = new Random();
        int upperbound = word.length() - 1;
        for (int i = 0; i < word.length() - 1; i++) {
            listOfRandomInt.add(rand.nextInt(upperbound));
        }

        for (int i : listOfRandomInt) {
            listOfLetter.set(i, "?");
        }

        return listOfLetter;

    }

    // REQUIRES: the Word should not have an empty string as its word field
    // EFFECTS: return a Word that is same as this Word with some letters covered with "?"
    //          if the Word is a single String, no "?" should be added in, return an exactly same looking Word
    //          if the Word has letters more than 1, the number of "?" should be more than or equal
    //          to 1, but not exceed the number of letters in this original word minus 1
    public Word composeWord() {
        List<String> coveredBrokenWord = coverWord();

        String coveredString = String.join("", coveredBrokenWord);
        Word coveredWord = new Word(coveredString);
        EventLog.getInstance().logEvent(new Event("Word: " + this.getWord()
                + " is composed to become a question word."));

        return coveredWord;

    }

    // EFFECTS: return a Json Object with word stored
    // This Method references code from: JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("word", word);
        EventLog.getInstance().logEvent(new Event("Current question word is saved."));
        return json;
    }


}
