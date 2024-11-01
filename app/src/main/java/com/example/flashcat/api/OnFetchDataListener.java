package com.example.flashcat.api;

import com.example.flashcat.Model.Desk;
import com.example.flashcat.Model.Dictionary.WordItem;
import com.example.flashcat.Model.Flashcard;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.threeten.bp.LocalDateTime;

import java.util.List;

public interface OnFetchDataListener {
    void onFetchData(WordItem wordItem, String message);
    void onFetchData(Desk Desk, int deskMessage);
    void onFetchData(Flashcard flashcard, int flashcardMessage); // Updated method signature
    void onError(String message);
    void onFetchDataList(List<Desk> listDesk);
    void onFetchDataListFlashcard(List<Flashcard> ListFlashcard, int idDesk); // Get all flashcard by idDesk

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
            .create();
}

