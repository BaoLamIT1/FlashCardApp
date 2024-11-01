package com.example.flashcat.Model;

public class Word {
    private int id;
    private String word;

    private String minusWord;
    private String definitionWord;

    public Word() {
    }

    public Word(int id, String word, String minusWord,String definitionWord) {
        this.id = id;
        this.word = word;
        this.definitionWord = definitionWord;
        this.minusWord = minusWord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinitionWord() {
        return definitionWord;
    }

    public void setDefinitionWord(String definitionWord) {
        this.definitionWord = definitionWord;
    }

    public String getMinusWord() {
        return minusWord;
    }

    public void setMinusWord(String minusWord) {
        this.minusWord = minusWord;
    }
}

