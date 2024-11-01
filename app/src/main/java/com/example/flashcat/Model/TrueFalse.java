package com.example.flashcat.Model;

public class TrueFalse {
    private int ID_TrueFalse = 2;
    private String question;
    private boolean answers;
    public TrueFalse() {
    }

    public TrueFalse(String question, boolean answers) {
        this.question = question;
        this.answers = answers;
    }

    public int getID_TrueFalse() {
        return ID_TrueFalse;
    }

    public void setID_TrueFalse(int ID_TrueFalse) {
        this.ID_TrueFalse = ID_TrueFalse;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isAnswers() {
        return answers;
    }

    public void setAnswers(boolean answers) {
        this.answers = answers;
    }
}
