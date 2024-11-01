package com.example.flashcat.Activity.Practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.flashcat.Database.DatabaseApp;
import com.example.flashcat.Model.Flashcard;
import com.example.flashcat.R;

import java.util.ArrayList;

public class MatchActivity extends AppCompatActivity {
    ArrayList<Flashcard> flashcards;
    ArrayList<String> terms;
    ArrayList<String> definitions;
    ArrayList<Flashcard> shuffledFlashcards;
    ArrayList<Integer> shuffledFlashcardsID;
    int deckID;
    boolean isAnswerWithDefinition;
    DatabaseApp db;
    boolean isPractice;

    // Views
    ImageButton btnClose;
    TextView txtCurrentTimeCounter;
    TextView Card1;
    TextView Card2;
    TextView Card3;
    TextView Card4;
    TextView Card5;
    TextView Card6;
    TextView Card7;
    TextView Card8;
    TextView Card9;
    TextView Card10;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        // Init database
        db = new DatabaseApp(this);

        // Get the views
        btnClose = findViewById(R.id.btn_end_match);
        txtCurrentTimeCounter = findViewById(R.id.txt_time_Match);
        Card1 = findViewById(R.id.txt_match_card1);
        Card2 = findViewById(R.id.txt_match_card2);
        Card3 = findViewById(R.id.txt_match_card3);
        Card4 = findViewById(R.id.txt_match_card4);
        Card5 = findViewById(R.id.txt_match_card5);
        Card6 = findViewById(R.id.txt_match_card6);
        Card7 = findViewById(R.id.txt_match_card7);
        Card8 = findViewById(R.id.txt_match_card8);
        Card9 = findViewById(R.id.txt_match_card9);
        Card10 = findViewById(R.id.txt_match_card10);

        // Handle close button
        btnClose.setOnClickListener(v -> {
            finish();
        });
    }
}
