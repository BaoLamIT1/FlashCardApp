package com.example.flashcat.Activity.Practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flashcat.Database.DatabaseApp;
import com.example.flashcat.Model.Flashcard;
import com.example.flashcat.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MultipleChoiceActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Flashcard> flashcards;
    ArrayList<Flashcard> shuffledFlashcards;
    ArrayList<Integer> shuffledFlashcardsID;
    ArrayList<Integer> correctAnswersID;
    ArrayList<Integer> wrongAnswersID;
    ArrayList<Integer> shuffledAnswerList;
    int deckID;
    boolean isAnswerWithDefinition;
    int totalQuestion;
    int currentQuestion = 0;
    boolean isPractice;

    // Views
    ImageButton btnClose;
    TextView txtCurrentQuestion;
    TextView txtQuestion;
    TextView txtAnswerA;
    TextView txtAnswerB;
    TextView txtAnswerC;
    TextView txtAnswerD;

    // Database
    DatabaseApp db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        // Database
        db = new DatabaseApp(this);

        // Get the views
        btnClose = findViewById(R.id.btn_close_multiple_choice);
        txtCurrentQuestion = findViewById(R.id.txt_number_multiple_choice);
        txtQuestion = findViewById(R.id.txt_question);
        txtAnswerA = findViewById(R.id.btn_answerA);
        txtAnswerB = findViewById(R.id.btn_answerB);
        txtAnswerC = findViewById(R.id.btn_answerC);
        txtAnswerD = findViewById(R.id.btn_answerD);

        // Initialize ArrayLists
        correctAnswersID = new ArrayList<>();
        wrongAnswersID = new ArrayList<>();

        // Set click listeners
        txtAnswerA.setOnClickListener(this);
        txtAnswerB.setOnClickListener(this);
        txtAnswerC.setOnClickListener(this);
        txtAnswerD.setOnClickListener(this);
        btnClose.setOnClickListener(this);

        // Get data from intent
        Intent intent = getIntent();
        deckID = intent.getIntExtra("ID_DECK", 0);
        isAnswerWithDefinition = intent.getBooleanExtra("IS_ANSWER_WITH_DEFINITION", false);
        isPractice = intent.getBooleanExtra("IS_PRACTICE", true);

        // get flashcards from database corresponding to deckID
        flashcards = db.getAllFlashcardByDeskID(deckID);

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
    }


    private void showQuestion(int questionIndex) {
        // Set the current question
        txtCurrentQuestion.setText("Question " + (currentQuestion + 1) + "/" + totalQuestion);

        // Debugging log
        Log.d("DEBUG", "isAnswerWithDefinition: " + isAnswerWithDefinition);
        Log.d("DEBUG", "isPractice: " + isPractice);

        // Get the current flashcard
        Flashcard currentFlashcard = shuffledFlashcards.get(questionIndex);

        // Decide randomly whether the correct answer location will be from A, B, C, or D
        Random random = new Random();
        int correctAnswerLocation = random.nextInt(4); // 0, 1, 2, 3

        // Set the term and definition based on the question type
        if (isAnswerWithDefinition) {
            // Set the question
            txtQuestion.setText(currentFlashcard.getTerm());
            // Debugging log
            Log.d("DEBUG", "Setting question with term");
            switch (correctAnswerLocation) {
                case 0:
                    txtAnswerA.setText(currentFlashcard.getDefinition());
                    txtAnswerB.setText(getRandomDefinition(currentFlashcard.getDefinition()));
                    txtAnswerC.setText(getRandomDefinition(currentFlashcard.getDefinition()));
                    txtAnswerD.setText(getRandomDefinition(currentFlashcard.getDefinition()));
                    break;
                case 1:
                    txtAnswerB.setText(currentFlashcard.getDefinition());
                    txtAnswerA.setText(getRandomDefinition(currentFlashcard.getDefinition()));
                    txtAnswerC.setText(getRandomDefinition(currentFlashcard.getDefinition()));
                    txtAnswerD.setText(getRandomDefinition(currentFlashcard.getDefinition()));
                    break;
                case 2:
                    txtAnswerC.setText(currentFlashcard.getDefinition());
                    txtAnswerA.setText(getRandomDefinition(currentFlashcard.getDefinition()));
                    txtAnswerB.setText(getRandomDefinition(currentFlashcard.getDefinition()));
                    txtAnswerD.setText(getRandomDefinition(currentFlashcard.getDefinition()));
                    break;
                case 3:
                    txtAnswerD.setText(currentFlashcard.getDefinition());
                    txtAnswerA.setText(getRandomDefinition(currentFlashcard.getDefinition()));
                    txtAnswerB.setText(getRandomDefinition(currentFlashcard.getDefinition()));
                    txtAnswerC.setText(getRandomDefinition(currentFlashcard.getDefinition()));
                    break;
            }

        } else {
            // Set the question
            txtQuestion.setText(currentFlashcard.getDefinition());
            // Debugging log
            Log.d("DEBUG", "Setting question with definition");

            switch (correctAnswerLocation) {
                case 0:
                    txtAnswerA.setText(currentFlashcard.getTerm());
                    txtAnswerB.setText(getRandomTerm(currentFlashcard.getTerm()));
                    txtAnswerC.setText(getRandomTerm(currentFlashcard.getTerm()));
                    txtAnswerD.setText(getRandomTerm(currentFlashcard.getTerm()));
                    break;
                case 1:
                    txtAnswerB.setText(currentFlashcard.getTerm());
                    txtAnswerA.setText(getRandomTerm(currentFlashcard.getTerm()));
                    txtAnswerC.setText(getRandomTerm(currentFlashcard.getTerm()));
                    txtAnswerD.setText(getRandomTerm(currentFlashcard.getTerm()));
                    break;
                case 2:
                    txtAnswerC.setText(currentFlashcard.getTerm());
                    txtAnswerA.setText(getRandomTerm(currentFlashcard.getTerm()));
                    txtAnswerB.setText(getRandomTerm(currentFlashcard.getTerm()));
                    txtAnswerD.setText(getRandomTerm(currentFlashcard.getTerm()));
                    break;
                case 3:
                    txtAnswerD.setText(currentFlashcard.getTerm());
                    txtAnswerA.setText(getRandomTerm(currentFlashcard.getTerm()));
                    txtAnswerB.setText(getRandomTerm(currentFlashcard.getTerm()));
                    txtAnswerC.setText(getRandomTerm(currentFlashcard.getTerm()));
                    break;
            }
        }
    }

    private String getRandomDefinition(String correctDefinition) {
        String randomDefinition;
        do {
            randomDefinition = flashcards.get(new Random().nextInt(flashcards.size())).getDefinition();

        } while (randomDefinition.equals(correctDefinition));

        return randomDefinition;
    }

    private String getRandomTerm(String correctTerm) {
        String randomTerm;
        do {
            randomTerm = flashcards.get(new Random().nextInt(flashcards.size())).getTerm();
        } while (randomTerm.equals(correctTerm));

        return randomTerm;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_answerA) {
            checkAnswer(txtAnswerA.getText().toString());
        } else if (v.getId() == R.id.btn_answerB) {
            checkAnswer(txtAnswerB.getText().toString());
        } else if (v.getId() == R.id.btn_answerC) {
            checkAnswer(txtAnswerC.getText().toString());
        } else if (v.getId() == R.id.btn_answerD) {
            checkAnswer(txtAnswerD.getText().toString());
        } else if (v.getId() == R.id.btn_close_multiple_choice) {
            finish();
        }
    }

    private void checkAnswer(String selectedAnswer) {
        Flashcard currentFlashcard = shuffledFlashcards.get(currentQuestion);

        if (isAnswerWithDefinition) {
            if(isPractice)
            {
                if (selectedAnswer.equals(currentFlashcard.getDefinition())) {
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                    correctAnswersID.add(currentFlashcard.getID_Flashcard());
                } else {
                    Toast.makeText(this, "Wrong! The correct answer is: " + currentFlashcard.getDefinition(), Toast.LENGTH_SHORT).show();
                    wrongAnswersID.add(currentFlashcard.getID_Flashcard());
                }
            } else {
                if (selectedAnswer.equals(currentFlashcard.getDefinition())) {
                    // Add the current question id to the correct answer id list
                    correctAnswersID.add(currentFlashcard.getID_Flashcard());
                } else {
                    // Add the current question id to the wrong answer id list
                    wrongAnswersID.add(currentFlashcard.getID_Flashcard());
                }
            }
        } else
        {
            if (isPractice)
            {
                if (selectedAnswer.equals(currentFlashcard.getTerm())) {
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                    correctAnswersID.add(currentFlashcard.getID_Flashcard());
                } else {
                    Toast.makeText(this, "Wrong! The correct answer is: " + currentFlashcard.getTerm(), Toast.LENGTH_SHORT).show();
                    wrongAnswersID.add(currentFlashcard.getID_Flashcard());
                }
            } else {
                if (selectedAnswer.equals(currentFlashcard.getTerm())) {
                    // Add the current question id to the correct answer id list
                    correctAnswersID.add(currentFlashcard.getID_Flashcard());
                } else {
                    // Add the current question id to the wrong answer id list
                    wrongAnswersID.add(currentFlashcard.getID_Flashcard());
                }
            }
        }

        currentQuestion++;

        if (currentQuestion < totalQuestion) {
            showQuestion(currentQuestion);
        } else {
            // Direct the user to the result activity
            Intent intent = new Intent(MultipleChoiceActivity.this, ResultActivity.class);

            intent.putExtra("CORRECT_ANSWERS_ID", correctAnswersID);
            intent.putExtra("WRONG_ANSWERS_ID", wrongAnswersID);
            intent.putExtra("SHUFFLE_FLASHCARDS_ID", shuffledFlashcardsID);

            startActivity(intent);
        }
    }
}
