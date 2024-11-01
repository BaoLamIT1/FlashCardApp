package com.example.flashcat.Model.Dictionary;

import java.util.ArrayList;
import java.util.List;

public class WordItem {
    private String word = "";
    private ArrayList<Meaning> meanings=null;
    private ArrayList<Phonetics> phonetics =null;
   // private List<String> sourceUrls;


    // Constructor

    public WordItem() {
    }

    public WordItem(String word, ArrayList<Meaning> meanings, ArrayList<Phonetics> phonetic) {
        this.word = word;
        this.meanings = meanings;
        this.phonetics = phonetic;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public ArrayList<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(ArrayList<Meaning> meanings) {
        this.meanings = meanings;
    }

    public ArrayList<Phonetics> getPhonetic() {
        return phonetics;
    }

    public void setPhonetic(ArrayList<Phonetics> phonetic) {
        this.phonetics = phonetic;
    }
}
