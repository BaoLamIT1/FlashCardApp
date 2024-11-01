package com.example.flashcat.Model;

public class Match {
    private int ID_Match = 1;
    private String term;
    private String definition;

    public Match() {
    }

    public Match(String term, String definition) {
        this.term = term;
        this.definition = definition;
    }

    public int getID_Match() {
        return ID_Match;
    }

    public void setID_Match(int ID_Match) {
        this.ID_Match = ID_Match;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
