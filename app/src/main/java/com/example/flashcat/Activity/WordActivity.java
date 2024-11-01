package com.example.flashcat.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flashcat.Activity.Desk.CreateFlashcardActivity;
import com.example.flashcat.Adapter.MeaningAdapter;
import com.example.flashcat.Adapter.MinusAdapter;
import com.example.flashcat.Database.DatabaseApp;
import com.example.flashcat.Fragment.DictionaryFragment;
import com.example.flashcat.Model.Desk;
import com.example.flashcat.Model.Dictionary.Phonetics;
import com.example.flashcat.Model.Dictionary.WordItem;
import com.example.flashcat.Model.Flashcard;
import com.example.flashcat.Model.Word;
import com.example.flashcat.R;
import com.example.flashcat.api.OnFetchDataListener;
import com.example.flashcat.api.RequestManager;

import java.util.ArrayList;
import java.util.List;

public class WordActivity extends AppCompatActivity {

    private ImageButton back;
    private TextView word;
    private RecyclerView minus;
    private MinusAdapter minusAdapter;
    private MeaningAdapter meaningAdapter;
    private RecyclerView meaing;
    private Button btnCreateFlashCard;
    private String searchWord;
    private ProgressDialog progressDialog;
    private DatabaseApp db;
    private WordItem item;
    private ArrayList<Word> wordArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        findID();
        //nhan tu khoa search
        Intent intent = getIntent();
        if(intent!=null){
            searchWord = intent.getStringExtra("search_query");
        }

        //database
        db= new DatabaseApp(WordActivity.this);
        item = new WordItem();

        wordArrayList = new ArrayList<>();
        //tao loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        //api
        RequestManager manager = new RequestManager(WordActivity.this);
        manager.getWordMeaning(listener,searchWord);

        //su kien button CreateFC
        btnCreateFlashCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordActivity.this, CreateFlashcardActivity.class);
                intent.putExtra("word_FC", item.getWord());
                intent.putExtra("definition_FC",item.getMeanings().get(0).getDefinitions().get(0).getDefinition());
                intent.putExtra("example_FC",item.getMeanings().get(0).getDefinitions().get(0).getExample());
                ArrayList<Phonetics> phonetics = new ArrayList<>();
                phonetics = item.getPhonetic();
                for(Phonetics p : phonetics)
                {
                    if(p.getAudio()!=null);
                    {
                        intent.putExtra("sound_FC", p.getAudio());
                        break;
                    }
                }
                startActivity(intent);
            }
        });
        // su kien button Back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WordActivity.this, HomeActivity.class);
                i.putExtra("fragmentTag" ,"dictionary");
                startActivity(i);
            }
        });
    }
    public void findID(){
        back = findViewById(R.id.back_word);
        word = findViewById(R.id.txt_word);
        minus = findViewById(R.id.list_item_minus);
        meaing = findViewById(R.id.meaning);
        btnCreateFlashCard = findViewById(R.id.btn_create_word);

    }
    private OnFetchDataListener listener = new OnFetchDataListener() {
        @Override
        public void onFetchData(WordItem wordItem, String message) {
            if(wordItem==null){
                Toast.makeText(WordActivity.this, "no data found!!!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(wordItem!=null){
                showData(wordItem);
            }

        }

        @Override
        public void onFetchData(Desk Desk, int message) {

        }

        @Override
        public void onFetchData(Flashcard flashcard, int flashcardMessage) {

        }

        @Override
        public void onError(String message) {
            Toast.makeText(WordActivity.this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFetchDataList(List<Desk> listDesk) {

        }

        @Override
        public void onFetchDataListFlashcard(List<Flashcard> ListFlashcard, int idDesk) {

        }


    };

    private void showData(WordItem wordItem) {
        word.setText(wordItem.getWord());
        minus.setHasFixedSize(true);
        minus.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        minusAdapter = new MinusAdapter(wordItem.getPhonetic(),WordActivity.this);
        minus.setAdapter(minusAdapter);
        meaing.setHasFixedSize(true);
        meaing.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        meaningAdapter = new MeaningAdapter(WordActivity.this,wordItem.getMeanings());
        meaing.setAdapter(meaningAdapter);
        item = wordItem;
        //add data
        String minusWord="";
        ArrayList<Phonetics> phonetics = new ArrayList<>();
        phonetics = item.getPhonetic();
        for(Phonetics p : phonetics)
        {
            if(p.getText()!=null);
            {
                minusWord = p.getText();
                break;
            }
        }
        if (!db.isWordExists(wordItem.getWord())) {
            // Từ chưa tồn tại trong cơ sở dữ liệu, vì vậy thêm vào
            db.addWord(new Word(1, wordItem.getWord(), minusWord, wordItem.getMeanings().get(0).getDefinitions().get(0).getDefinition()));
        } else {
            // Từ đã tồn tại trong cơ sở dữ liệu
            Log.d("Database", "Word '" + word + "' already exists in database");
        }

        progressDialog.dismiss();
    }


}