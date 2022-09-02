package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represent a List of Words

public class ListOfWords implements Writable {

    private List<Word> listOfWords;

    // EFFECTS: construct a ListOfWords with an empty List
    public ListOfWords() {
        listOfWords = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("A new empty word-list has been constructed."));
    }

    // MODIFIES: this
    // EFFECTS: add the Word w to the end of this ListOfWords
    public void addWord(Word w) {
        listOfWords.add(w);
        EventLog.getInstance().logEvent(new Event("Word: "  + w.getWord() + " is add to word-list."));
    }

    // MODIFIES: this
    // EFFECTS: randomize the sequence of words in this ListOfWords without change the length
    public void randomizeWords() {
        Collections.shuffle(listOfWords);
        EventLog.getInstance().logEvent(new Event("Word-list is randomized."));
    }

    // EFFECTS: return the list of words
    public List<Word> getWords() {
        return listOfWords;
    }

    // REQUIRES: there should be at least one Word in this ListOfWords; int i should be less or equal to the
    //           largest index of this ListOfWords
    // EFFECTS: return the word with index i in this ListOfWords
    public Word getWord(int i) {
        return listOfWords.get(i);
    }

    // EFFECTS: return the length of this ListOfWords; if it is empty, return 0
    public int length() {
        return listOfWords.size();
    }

    // EFFECTS: return whether the ListOfWords contains the Word w or not; if the ListOfWords is empty,
    //          return false
    public boolean contains(Word w) {
        return listOfWords.contains(w);
    }

    // MODIFIES: this
    // EFFECTS: set listOfWords to the parameter List<Word> low
    public void setWords(List<Word> low) {
        listOfWords = low;
        EventLog.getInstance().logEvent(new Event("Word-list is reset."));
    }

    // REQUIRES: 0 <= int i <= int e <= this.length()
    // EFFECTS: return a listOfWords that is the sub list of this listOfWords from index i to index e
    //          if this listOfWords is empty, return an empty list
    public ListOfWords getSubList(int i, int e) {
        List<Word> los =  listOfWords.subList(i,e);
        ListOfWords newlistOfWord = new ListOfWords();
        newlistOfWord.setWords(los);
        return newlistOfWord;
    }

    // EFFECTS: return a Json Object with ListOfWords stored
    // This Method references code from: JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("listOfWord", listOfWords);
        EventLog.getInstance().logEvent(new Event("Current word-list status is saved."));
        return json;
    }



}
