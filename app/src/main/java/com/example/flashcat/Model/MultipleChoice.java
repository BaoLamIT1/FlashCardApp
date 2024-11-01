package com.example.flashcat.Model;

import java.util.List;

public class MultipleChoice {
    private int ID_MC = 3;
    private String question;
    private List<String> answers;

    public MultipleChoice() {
    }

    public MultipleChoice(String question, List<String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public int getID_MC() {
        return ID_MC;
    }

    public void setID_MC(int ID_MC) {
        this.ID_MC = ID_MC;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
