package com.example.flashcat.Model.Dictionary;

import java.util.List;

public class Definition {
    private String definition="";
    private String example="";

    private List<String> synonyms=null;
    private List<String> antonyms=null;

    // Constructor
//    public Definition(String definition, String example) {
//        this.definition = definition;
//        this.example = example;
//    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public List<String> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(List<String> antonyms) {
        this.antonyms = antonyms;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }


    public String getExample() {
        return example;
    }


    public void setExample(String example) {
        this.example = example;
    }

    @Override
    public String toString() {
        return "Definition{" +
                "definition='" + definition + '\'' +
                ", example='" + example + '\'' +
                '}';
    }

}
