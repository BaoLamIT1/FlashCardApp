package com.example.flashcat.Activity.Practice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flashcat.Activity.HomeActivity;
import com.example.flashcat.Database.DatabaseApp;
import com.example.flashcat.Model.Desk;
import com.example.flashcat.Model.Flashcard;
import com.example.flashcat.R;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.ArrayList;

public class PracticeActivity extends AppCompatActivity {

    Spinner deskSpinner;
    TextView totalQuestions;
    Spinner answerWaySpinner;
    MaterialSwitch trueFalseSwitch;
    MaterialSwitch multiChoiceSwitch;
    MaterialSwitch matchSwitch;
    Button startButton;
    ImageButton backBtn;
    ArrayList<Desk> deskList;
    // Database
    DatabaseApp db;
    // Adapter
    ArrayAdapter<String> deskNameAdapter;

    int totalQuestion;
    boolean isAnswerWithDefinition;
    int questionType; // 0: True/False, 1: Multiple choice, 2: Match
    boolean isPractice;
    int deskID;
    ArrayList<Flashcard> flashcards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        // Init database
        db = new DatabaseApp(this);

        // Get the views
        backBtn = findViewById(R.id.back_practice);
        deskSpinner = findViewById(R.id.sp_select_desk);
        totalQuestions = findViewById(R.id.txt_total_questions);
        answerWaySpinner = findViewById(R.id.sp_select_answer_method);
        trueFalseSwitch = findViewById(R.id.switch_true_false);
        multiChoiceSwitch = findViewById(R.id.switch_multiple_choice);
        matchSwitch = findViewById(R.id.switch_match);
        startButton = findViewById(R.id.btn_practice_startTest);

        // get the data from the intent
        Intent intent = getIntent();
        isPractice = intent.getBooleanExtra("IS_PRACTICE", true);

        // Set value for deskSpinner
        // Get the list of desk from database
        deskList = db.getAllDesk();

        // Extract desk names from the deskList to populate the spinner
        ArrayList<String> deskNames = new ArrayList<>();
        for (Desk desk : deskList) {
            deskNames.add(desk.getName_deck());
        }

        // Set the adapter for deskSpinner
        deskNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, deskNames);
        deskNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deskSpinner.setAdapter(deskNameAdapter);

        // Load data corresponding to the selected desk
        deskSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Desk selectedDesk = deskList.get(position);
                totalQuestion = selectedDesk.getNumber_flashcard();
                totalQuestions.setText(String.valueOf(totalQuestion));
                deskID = selectedDesk.getID_Deck();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                totalQuestions.setText("0");
            }
        });

        // Handle the answer way spinner and set the value for isAnswerWithDefinition if the user selects the first item (Answer with definition) the value is true
        answerWaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    isAnswerWithDefinition = true;
                } else {
                    isAnswerWithDefinition = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                isAnswerWithDefinition = false;
            }
        });

        // Handle the switch allow only one switch to be on at a time
        trueFalseSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trueFalseSwitch.isChecked()) {
                    multiChoiceSwitch.setChecked(false);
                    matchSwitch.setChecked(false);
                    questionType = 0;
                }
            }
        });

        multiChoiceSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (multiChoiceSwitch.isChecked()) {
                    trueFalseSwitch.setChecked(false);
                    matchSwitch.setChecked(false);
                    questionType = 1;
                }
            }
        });

        matchSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (matchSwitch.isChecked()) {
                    trueFalseSwitch.setChecked(false);
                    multiChoiceSwitch.setChecked(false);
                    questionType = 2;
                }
            }
        });

        // Handle the back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PracticeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Handle the start button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the user has selected a question type
                if (!trueFalseSwitch.isChecked() && !multiChoiceSwitch.isChecked() && !matchSwitch.isChecked()) {
                    Toast.makeText(PracticeActivity.this, "Please select at least one question type", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the user has selected a desk
                if (deskSpinner.getSelectedItem() == null) {
                    Dialog dialog = new Dialog(PracticeActivity.this);
                    dialog.show();
                    return;
                }

                // Start the test
                // Check the question type and start the test
                if (questionType == 0) {
                    // Start the true/false test
                    Intent intent = new Intent(PracticeActivity.this, TrueFalseActivity.class);
                    intent.putExtra("ID_DECK", deskID);
                    intent.putExtra("IS_ANSWER_WITH_DEFINITION", isAnswerWithDefinition);
                    intent.putExtra("IS_PRACTICE", isPractice);
                    startActivity(intent);
                } else if (questionType == 1) {
                    // Start the multiple choice test
                    Intent intent = new Intent(PracticeActivity.this, MultipleChoiceActivity.class);
                    intent.putExtra("ID_DECK", deskID);
                    intent.putExtra("IS_ANSWER_WITH_DEFINITION", isAnswerWithDefinition);
                    intent.putExtra("IS_PRACTICE", isPractice);
                    startActivity(intent);
                } else {
                    // Start the match test
                    Intent intent = new Intent(PracticeActivity.this, MatchActivity.class);
                    intent.putExtra("ID_DECK", deskID);
                    intent.putExtra("IS_ANSWER_WITH_DEFINITION", isAnswerWithDefinition);
                    intent.putExtra("IS_PRACTICE", isPractice);
                    startActivity(intent);
                }
            }
        });

    }
}