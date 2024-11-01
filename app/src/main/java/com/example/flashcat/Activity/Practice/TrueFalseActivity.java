package com.example.flashcat.Activity.Practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.flashcat.Database.DatabaseApp;
import com.example.flashcat.Model.Flashcard;
import com.example.flashcat.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TrueFalseActivity extends AppCompatActivity {

    ArrayList<Flashcard> flashcards;
    ArrayList<String> terms;
    ArrayList<String> definitions;
    ArrayList<Flashcard> shuffledFlashcards;
    ArrayList<Integer> shuffledFlashcardsID;
    int deckID;
    boolean isAnswerWithDefinition;
    DatabaseApp db;
    ImageButton btnClose;
    TextView txtCurrentQuestion;
    TextView txtTerm;
    TextView txtDefinition;
    Button btnTrue;
    Button btnFalse;
    boolean isPractice;
    int currentQuestion = 0;
    int totalQuestion;
    ArrayList<Integer> correctAnswersID;
    ArrayList<Integer> wrongAnswersID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_false);

        // Init database
        db = new DatabaseApp(this);

        // Get the views
        btnClose = findViewById(R.id.btn_close_multiple_choice);
        txtCurrentQuestion = findViewById(R.id.txt_number_multiple_choice);
        txtTerm = findViewById(R.id.txt_term);
        txtDefinition = findViewById(R.id.txt_definition);
        btnTrue = findViewById(R.id.btn_true);
        btnFalse = findViewById(R.id.btn_false);

        // Get data from PracticeActivity
        Intent intent = getIntent();
        deckID = intent.getIntExtra("ID_DECK", 0);
        isAnswerWithDefinition = intent.getBooleanExtra("IS_ANSWER_WITH_DEFINITION", false);
        isPractice = intent.getBooleanExtra("IS_PRACTICE", true);

        // get flashcards from database corresponding to deckID
        flashcards = db.getAllFlashcardByDeskID(deckID);

        // Get all terms and definitions
        terms = new ArrayList<>();
        definitions = new ArrayList<>();
        for (Flashcard flashcard : flashcards) {
            terms.add(flashcard.getTerm());
            definitions.add(flashcard.getDefinition());
        }

        // Shuffle flashcards list
        Collections.shuffle(flashcards);
        shuffledFlashcards = flashcards;

        // Convert the shuffled flashcards list to a list of shuffled flashcard IDs
        shuffledFlashcardsID = new ArrayList<>();
        for (Flashcard flashcard : shuffledFlashcards) {
            shuffledFlashcardsID.add(flashcard.getID_Flashcard());
        }

        // Set total question
        totalQuestion = flashcards.size();

        // Initialize the first question
        showQuestion(currentQuestion);

        // Initialize the correct and wrong answers list
        correctAnswersID = new ArrayList<>();
        wrongAnswersID = new ArrayList<>();

        // Set up the click listeners for the buttons
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
    }

    private void showQuestion(int questionIndex) {
        // Set current question number
        txtCurrentQuestion.setText("Question " + (questionIndex + 1) + "/" + totalQuestion);

        // Get the current flashcard
        Flashcard currentFlashcard = shuffledFlashcards.get(questionIndex);

        // Decide randomly whether this will be a true or false question
        Random random = new Random();
        boolean isTrueQuestion = random.nextBoolean();

        // Set the term and definition based on the question type
        if (isTrueQuestion) {
            txtTerm.setText(currentFlashcard.getTerm());
            txtDefinition.setText(currentFlashcard.getDefinition());
        } else {
            // Get a random definition
            int randomIndex = random.nextInt(definitions.size());
            String randomDefinition = definitions.get(randomIndex);

            // Set the term and definition
            txtTerm.setText(currentFlashcard.getTerm());
            txtDefinition.setText(randomDefinition);
        }
    }

    private void checkAnswer(boolean isTrue) {
        Flashcard currentFlashcard = shuffledFlashcards.get(currentQuestion);

        if (isTrue == db.isExistTermWithDefinition(currentFlashcard.getTerm(), txtDefinition.getText().toString())) {
            if (isPractice)
            {
                // Set the text color to green
                txtTerm.setTextColor(Color.GREEN);
                txtDefinition.setTextColor(Color.GREEN);
            }
            // Increment the total correct answer
            correctAnswersID.add(currentFlashcard.getID_Flashcard());

        } else {
            if (isPractice)
            {
                // Set the text color to red
                txtTerm.setTextColor(Color.RED);
                txtDefinition.setTextColor(Color.RED);
            }
            // Add the current question to the wrong answers list
            wrongAnswersID.add(currentFlashcard.getID_Flashcard());
        }

        // Schedule a task to reset the text color after 1 seconds
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // Increment the current question
                currentQuestion++;
                // Check if the current question is the last question
                if (currentQuestion < totalQuestion) {
                    // Reset the text color to black
                    txtTerm.setTextColor(Color.BLACK);
                    txtDefinition.setTextColor(Color.BLACK);

                    // Show the next question
                    showQuestion(currentQuestion);
                } else {
                    // Put result to intent
                    Intent intent = new Intent(TrueFalseActivity.this, ResultActivity.class);
                    intent.putExtra("CORRECT_ANSWERS_ID", correctAnswersID);
                    intent.putExtra("WRONG_ANSWERS_ID", wrongAnswersID);
                    intent.putExtra("SHUFFLE_FLASHCARDS_ID", shuffledFlashcardsID);

                    startActivity(intent);

                }
            }
        }, 1000);


    }
}
