package com.example.flashcat.Activity.Practice;

import static android.graphics.Color.TRANSPARENT;
import static android.graphics.Color.parseColor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flashcat.Activity.HomeActivity;
import com.example.flashcat.Database.DatabaseApp;
import com.example.flashcat.Model.Flashcard;
import com.example.flashcat.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    ArrayList<Integer> correctAnswersID;
    ArrayList<Integer> wrongAnswersID;
    ArrayList<Integer> shuffledFlashcardsID;
    ArrayList<Flashcard> shuffledFlashcards;
    ArrayList<PieModel> pieModels;
    // Database
    DatabaseApp db;

    // Views
    TextView correctAnswers;
    TextView wrongAnswers;
    PieChart result;
    RecyclerView rcvAnswerList;
    Button btnPracticeAgain;
    Button btnMakeTest;
    ImageButton btnClose;
    int totalQuestion;
    int totalCorrectAnswers;
    int totalWrongAnswers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Init database
        db = new DatabaseApp(this);

        // Get the views
        correctAnswers = findViewById(R.id.txt_correct_result);
        wrongAnswers = findViewById(R.id.txt_incorrect_result);
        result = findViewById(R.id.pie_chart);
        rcvAnswerList = findViewById(R.id.lst_answers);
        btnPracticeAgain = findViewById(R.id.btn_practice);
        btnMakeTest = findViewById(R.id.btn_test);
        btnClose = findViewById(R.id.btn_close_result);

        // Get data from intent
        Intent intent = getIntent();
        correctAnswersID = intent.getIntegerArrayListExtra("CORRECT_ANSWERS_ID");
        wrongAnswersID = intent.getIntegerArrayListExtra("WRONG_ANSWERS_ID");
        shuffledFlashcardsID = intent.getIntegerArrayListExtra("SHUFFLE_FLASHCARDS_ID");

        // get shuffled flashcards
        shuffledFlashcards = new ArrayList<>();
        for (int i = 0; i < shuffledFlashcardsID.size(); i++) {
            shuffledFlashcards.add(db.getFlashcardByID(shuffledFlashcardsID.get(i)));
        }

        // get total correct answers
        totalCorrectAnswers = correctAnswersID.size();
        // get total wrong answers
        totalWrongAnswers = wrongAnswersID.size();

        // Set the percentage values directly on each PieModel
        // Pie chart data
        ArrayList<PieModel> pieModels = new ArrayList<>();
        pieModels.add(new PieModel("know", totalCorrectAnswers, parseColor("#66BB6A")));
        pieModels.add(new PieModel("learning", totalWrongAnswers, parseColor("#F45F5F")));

        // Set the percentage values directly on each PieModel
        for (PieModel pieModel : pieModels) {
            pieModel.setShowLabel(true); // Show the percentage in each part of the chart

            // Set the background color of the label (percentage) to transparent
            pieModel.setHighlightedColor(TRANSPARENT);

            result.addPieSlice(pieModel);
        }

        result.startAnimation();

        // Set the total correct and wrong answers
        correctAnswers.setText(String.valueOf(totalCorrectAnswers));
        wrongAnswers.setText(String.valueOf(totalWrongAnswers));

        // Handle the click event for the practice again button
        btnPracticeAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the practice activity
                Intent intent = new Intent(ResultActivity.this, PracticeActivity.class);
                startActivity(intent);
            }
        });

        // Handle the click event for the make test button
        btnMakeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the test activity
                Intent intent = new Intent(ResultActivity.this, PracticeActivity.class);
                // Inlcude a value to indicate that the user wants to make a test
                intent.putExtra("IS_PRACTICE", false);
                startActivity(intent);
            }
        });

        // Handle the click event for the close button
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Direct the user back to the Home activity
                Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // TODO: Set the adapter for the recycler view
    }
}