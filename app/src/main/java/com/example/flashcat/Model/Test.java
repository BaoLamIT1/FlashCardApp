package com.example.flashcat.Model;

public class Test {
    private int ID_Test;
    private int quantity;
    private TestForm testForm;
    private boolean method;
    private int correct;
    private int ID_Deck;
    public enum TestForm {
        MATCH,
        TRUE_FALSE,
        MULTIPLE_CHOICE
    }

    public Test() {
    }

    public Test(int ID_Test, int quantity, TestForm testForm, boolean method, int correct, int ID_Deck) {
        this.ID_Test = ID_Test;
        this.quantity = quantity;
        this.testForm = testForm;
        this.method = method;
        this.correct = correct;
        this.ID_Deck = ID_Deck;
    }

    public int getID_Test() {
        return ID_Test;
    }

    public void setID_Test(int ID_Test) {
        this.ID_Test = ID_Test;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public TestForm getTestForm() {
        return testForm;
    }

    public void setTestForm(TestForm testForm) {
        this.testForm = testForm;
    }

    public boolean isMethod() {
        return method;
    }

    public void setMethod(boolean method) {
        this.method = method;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getID_Deck() {
        return ID_Deck;
    }

    public void setID_Deck(int ID_Deck) {
        this.ID_Deck = ID_Deck;
    }
}
