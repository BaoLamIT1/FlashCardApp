package com.example.flashcat.Model;


import java.time.LocalDateTime;

public class Desk {
    private int ID_Deck;
    private String name_deck;
    private boolean status_deck;
    private org.threeten.bp.LocalDateTime create_day;

    private String ID_Account;
    private int number_flashcard;
    public Desk() {
    }

    public Desk(int ID_Deck, String name_deck, boolean status_deck, org.threeten.bp.LocalDateTime create_day, String ID_Account, int number_flashcard) {
        this.ID_Deck = ID_Deck;
        this.name_deck = name_deck;
        this.status_deck = status_deck;
        this.create_day = create_day;
        this.ID_Account = ID_Account;
        this.number_flashcard = number_flashcard;
    }

    public int getID_Deck() {
        return ID_Deck;
    }

    public void setID_Deck(int ID_Deck) {
        this.ID_Deck = ID_Deck;
    }

    public String getName_deck() {
        return name_deck;
    }

    public void setName_deck(String name_deck) {
        this.name_deck = name_deck;
    }

    public boolean isStatus_deck() {
        return status_deck;
    }

    public void setStatus_deck(boolean status_deck) {
        this.status_deck = status_deck;
    }

    public org.threeten.bp.LocalDateTime getCreate_day() {
        return create_day;
    }

    public void setCreate_day(org.threeten.bp.LocalDateTime create_day) {
        this.create_day = create_day;
    }

    public String getID_Account() {
        return ID_Account;
    }

    public void setID_Account(String ID_Account) {
        this.ID_Account = ID_Account;
    }

    public int getNumber_flashcard() {
        return number_flashcard;
    }

    public void setNumber_flashcard(int number_flashcard) {
        this.number_flashcard = number_flashcard;
    }
    public Desk (String name_deck, org.threeten.bp.LocalDateTime create_day ){
        this.name_deck = name_deck;
        this.create_day = create_day;
    }
}
