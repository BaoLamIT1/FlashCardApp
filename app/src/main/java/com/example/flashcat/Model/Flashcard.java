package com.example.flashcat.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

public class Flashcard implements Parcelable {
    private int ID_Flashcard;
    private String term;
    private String definition;
    private String example;
    private String sound =null;
    private boolean status;
    private org.threeten.bp.LocalDateTime update_day;
    private int ID_Deck;

    public Flashcard() {
    }
    public Flashcard(String term, String definition){
        this.term = term;
        this.definition = definition;
    }
    public Flashcard(int ID_Flashcard, String term, String definition, String example, String sound, boolean status, org.threeten.bp.LocalDateTime update_day, int ID_Deck) {
        this.ID_Flashcard = ID_Flashcard;
        this.term = term;
        this.definition = definition;
        this.example = example;
        this.sound = sound;
        this.status = status;
        this.update_day = update_day;
        this.ID_Deck = ID_Deck;
    }

    protected Flashcard(Parcel in) {
        ID_Flashcard = in.readInt();
        term = in.readString();
        definition = in.readString();
        example = in.readString();
        sound = in.readString();
        status = in.readByte() != 0;
        ID_Deck = in.readInt();
    }

    public static final Creator<Flashcard> CREATOR = new Creator<Flashcard>() {
        @Override
        public Flashcard createFromParcel(Parcel in) {
            return new Flashcard(in);
        }

        @Override
        public Flashcard[] newArray(int size) {
            return new Flashcard[size];
        }
    };

    public int getID_Flashcard() {
        return ID_Flashcard;
    }

    public void setID_Flashcard(int ID_Flashcard) {
        this.ID_Flashcard = ID_Flashcard;
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

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public org.threeten.bp.LocalDateTime getUpdate_day() {
        return update_day;
    }

    public void setUpdate_day(org.threeten.bp.LocalDateTime update_day) {
        this.update_day = update_day;
    }

    public int getID_Deck() {
        return ID_Deck;
    }

    public void setID_Deck(int ID_Deck) {
        this.ID_Deck = ID_Deck;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(String.valueOf(ID_Deck));
        dest.writeString(String.valueOf(ID_Flashcard));
        dest.writeString(term);
        dest.writeString(definition);
        dest.writeString(String.valueOf(update_day));
        dest.writeString(String.valueOf(status));
        dest.writeString(example);
    }
}
