package com.example.flashcat.Activity.Desk;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.flashcat.Adapter.EditDeskAdapter;
import com.example.flashcat.Database.DatabaseApp;
import com.example.flashcat.Model.Desk;
import com.example.flashcat.Model.Dictionary.WordItem;
import com.example.flashcat.Model.Flashcard;
import com.example.flashcat.R;
import com.example.flashcat.api.OnFetchDataListener;
import com.example.flashcat.api.RequestManagerDesk;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class EditDeskActivity extends AppCompatActivity {
     private ImageButton btnBack;
     private TextInputEditText edDesk;
     private RecyclerView recyclerViewFlashcard;
     private Button btnSaveEditDesk;
     private FloatingActionButton floatingActionAdd;
     private int idDesk;
    Desk desk = null;
    private String nameDesk;
    private ArrayList<Flashcard> listFlashcard;
    private EditDeskAdapter adapter;
    RequestManagerDesk requestManagerDesk = new RequestManagerDesk(this);
    private DatabaseApp db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_desk);
        findID();

        db = new DatabaseApp(EditDeskActivity.this);
        listFlashcard = new ArrayList<Flashcard>();
        //nhan id can sua
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                idDesk = extras.getInt("ID_Desk");
                nameDesk = extras.getString("NameDesk");
            }}
        edDesk.setText(nameDesk);

        // Call the API
        requestManagerDesk.getAllFlashcardsByDeskId(listener, idDesk);

//        listFlashcard = db.getAllFlashcardByDeskID(idDesk);
        adapter = new EditDeskAdapter(listFlashcard,EditDeskActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewFlashcard.setLayoutManager(layoutManager);
        recyclerViewFlashcard.setAdapter(adapter);
        floatingActionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditDeskActivity.this, CreateFlashcardActivity.class);
                startActivity(i);
            }
        });
        btnSaveEditDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(Flashcard list: listFlashcard )
                {
//                    db.updateFlashcard(list.getID_Flashcard(),list);
                    requestManagerDesk.updateFlashcard(listener, list.getID_Flashcard(), list);
                }
                nameDesk = edDesk.getText().toString();
//                Desk desk = db.getDesk(idDesk);


                // Call the API to get desk by id
                requestManagerDesk.getDeskById(listener, idDesk);

                // Check if the desk object is not null
                if (desk != null) {
                    desk.setName_deck(nameDesk);
//                    db.updateDesk(idDesk,desk);

                    // Update desk in server using API
                    requestManagerDesk.updateDesk(listener, idDesk, desk);

                    Intent i = new Intent(EditDeskActivity.this,DeskActivity.class);
                    i.putExtra("ID_Desk",idDesk);
                    i.putExtra("NameDesk", nameDesk);
                    setResult(210,i);
                    finish();
                } else {
                    // Handle the case where the desk object is null, perhaps show an error message
                    Toast.makeText(EditDeskActivity.this, "Desk not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void findID(){
        btnBack = findViewById(R.id.back_editDesk);
        edDesk = findViewById(R.id.ed_name_desk_edit);
        recyclerViewFlashcard = findViewById(R.id.list_flashcard);
        floatingActionAdd = findViewById(R.id.floating_action_add);
        btnSaveEditDesk = findViewById(R.id.action_save_edit_desk);
    }

    //Handle the API response
    private OnFetchDataListener listener = new OnFetchDataListener() {


        @Override
        public void onFetchData(WordItem wordItem, String message) {

        }

        @Override
        public void onFetchData(Desk Desk, int message) {
            if (message != 0) {
                // Update desk in local database
                desk = Desk;
                Log.d("J", "onFetchData: " + desk.getName_deck());

            } else {
                // Handle the case where the desk object is null, perhaps show an error message
                Toast.makeText(EditDeskActivity.this, "Desk not found", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFetchData(Flashcard flashcard, int flashcardMessage) {

        }

        @Override
        public void onError(String message) {

        }

        @Override
        public void onFetchDataList(List<Desk> listDesk) {

        }

        @Override
        public void onFetchDataListFlashcard(List<Flashcard> ListFlashcard, int idDesk) {
            listFlashcard.clear();
            listFlashcard.addAll(ListFlashcard);
            adapter.notifyDataSetChanged();


        }
    };

}