package com.example.flashcat.Activity.Desk;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flashcat.Activity.HomeActivity;
import com.example.flashcat.Adapter.DeskFlashcatAdapter;
import com.example.flashcat.Database.DatabaseApp;
import com.example.flashcat.Fragment.DeskFragment;
import com.example.flashcat.Fragment.HomeFragment;
import com.example.flashcat.Model.Desk;
import com.example.flashcat.Model.Dictionary.WordItem;
import com.example.flashcat.Model.Flashcard;
import com.example.flashcat.R;
import com.example.flashcat.Touch.FlashcardItemTouchHelper;
import com.example.flashcat.Touch.ItemTouchHelperListener;
import com.example.flashcat.api.OnFetchDataListener;
import com.example.flashcat.api.RequestManagerDesk;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DeskActivity extends AppCompatActivity implements ItemTouchHelperListener {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private ImageButton btnBack ;
    private Button btnStudy;
    private ImageButton btnMore;
    private TextView txtNameDeskSelected;
    private int idDesk;
    private String nameDesk;
    private String nameDeskCreate;
    private String createdDay;

    private ArrayList<Flashcard> listCard;
    private ArrayList<Flashcard> listCardSelected;
    private DatabaseApp db;
    private DeskFlashcatAdapter deskFlashcatAdapter;
    private RecyclerView rcflashcard;
    private ConstraintLayout rootview;

    RequestManagerDesk requestManagerDesk = new RequestManagerDesk(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desk);
        findID();
        db= new DatabaseApp(DeskActivity.this);
        // Thêm Fragment vào framelayout container
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout_desk, new DeskFragment())
                .commit();
        // Nhan du lieu tu intent
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                nameDesk = extras.getString("Name_Desk");
                nameDeskCreate = extras.getString("NameDesk");
                idDesk = extras.getInt("ID_Desk");
                createdDay = extras.getString("CreatedDay");
                Log.d("name", "onClick: " +nameDesk );
                Bundle b = new Bundle();
                b.putInt("idDesk",idDesk);
                DeskFragment fragment = new DeskFragment();
                fragment.setArguments(b);

                // Thêm fragment vào activity
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout_desk, fragment)
                        .commit();
            }
        }
        listCard = new ArrayList<Flashcard>();
        //get list flashcard by desk id from api
        requestManagerDesk.getAllFlashcardsByDeskId(listener,idDesk);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            listCard = db.getAllFlashcardByDeskID(idDesk);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcflashcard.setLayoutManager(layoutManager);
        deskFlashcatAdapter = new DeskFlashcatAdapter(listCard,this);
        rcflashcard.setAdapter(deskFlashcatAdapter);

        if (nameDesk != null) {
            txtNameDeskSelected.setText(nameDesk);
        }
        if(nameDeskCreate!=null)
        {
            txtNameDeskSelected.setText(nameDeskCreate);
//            db.addDesk(new Desk(1,nameDeskCreate,false,LocalDateTime.parse(createdDay,formatter),"12",0));
            Desk desk = new Desk(1,nameDeskCreate,false,LocalDateTime.parse(createdDay,formatter),"1",0);
            // Call API to create desk
            requestManagerDesk.addDesk(listener, desk);

        }
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeskActivity.this, HomeActivity.class);

                startActivity(intent);
//                Desk desk = db.getDesk(idDesk);
//                desk.setCreate_day(getCurrentTime());
//                db.updateDesk(idDesk,desk);
                finish();

            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new FlashcardItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(rcflashcard);
    }
    public void findID(){
        btnBack = findViewById(R.id.back_desk);
        btnMore = findViewById(R.id.action_more_desk);
        txtNameDeskSelected = findViewById(R.id.txt_NameDesk_selected);
        rcflashcard = findViewById(R.id.list_flashcard_desk);
        rootview = findViewById(R.id.rootview);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int it = item.getItemId();
        if(it == android.R.id.home){
            Intent intent = new Intent(DeskActivity.this, HomeActivity.class);

            startActivity(intent);
            finish();

            return true; // Xử lý sự kiện thành công
        }
        return super.onOptionsItemSelected(item);
    }
    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.action_more_desk));
        popupMenu.getMenuInflater().inflate(R.menu.menu_desk, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Xử lý sự kiện
                int it= item.getItemId();
                if(it == R.id.menu_Edit){
                    Intent i = new Intent(DeskActivity.this, EditDeskActivity.class);
                    i.putExtra("ID_Desk",idDesk);
                    i.putExtra("NameDesk", nameDesk);
                    startActivityForResult(i,200);
                }
                else if(it == R.id.menu_Delete){
                    AlertDialog.Builder builder = new AlertDialog.Builder(DeskActivity.this);
                    builder.setMessage("Are you sure you want to delete this item?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(DeskActivity.this, HomeActivity.class);
//                                    db.deleteDesk(idDesk);

                                    // Call API to delete desk
                                    RequestManagerDesk requestManagerDesk = new RequestManagerDesk(DeskActivity.this);
                                    requestManagerDesk.deleteDesk(listener, idDesk);
                                    startActivity(intent);
                                    finish();

                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                return true;
            }
        });
        popupMenu.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200 && resultCode == 210)
        {
            if (data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    nameDesk = extras.getString("NameDesk");
                    Log.d("desk", "onActivityResult: " + nameDesk);
                    //nameDeskCreate = extras.getString("NameDesk");
                    idDesk = extras.getInt("ID_Desk");
                    createdDay = extras.getString("CreatedDay");
                    Log.d("name", "onClick: " +nameDesk );
                    txtNameDeskSelected.setText(nameDesk);
                    Bundle b = new Bundle();
                    b.putInt("idDesk",idDesk);
                    DeskFragment fragment = new DeskFragment();
                    fragment.setArguments(b);

                    // Thêm fragment vào activity
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_layout_desk, fragment)
                            .commit();
                    listCard = new ArrayList<Flashcard>();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        listCard = db.getAllFlashcardByDeskID(idDesk);
                    }
                    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    rcflashcard.setLayoutManager(layoutManager);
                    deskFlashcatAdapter = new DeskFlashcatAdapter(listCard,this);
                    rcflashcard.setAdapter(deskFlashcatAdapter);
                }
            }

        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder) {
        if(viewHolder instanceof  DeskFlashcatAdapter.DeskFlashcatViewHolder){
            int indexDelete = viewHolder.getAdapterPosition();
            Flashcard flashcardDelete = listCard.get(indexDelete);
            String termDelete = listCard.get(indexDelete).getTerm();
            // Create an AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(DeskActivity.this);
            builder.setMessage("Are you sure you want to delete this item?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Remove item
                    deskFlashcatAdapter.removeItem(indexDelete);
                    db.deleteFlashcard(flashcardDelete.getID_Flashcard());
                    Desk desk = db.getDesk(idDesk);
                    desk.setNumber_flashcard(desk.getNumber_flashcard()-1);
                    db.updateDesk(idDesk,desk);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do not remove item, reset view
                    deskFlashcatAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    //get current time
    public LocalDateTime getCurrentTime(){
        return LocalDateTime.now();
    }

    // Handle API response
    private OnFetchDataListener listener = new OnFetchDataListener() {
        @Override
        public void onFetchData(WordItem wordItem, String message) {
            // Handle WordItem data if needed
        }

        @Override
        public void onFetchData(Desk Desk, int message) {
            // Handle Desk data if needed
        }

        @Override
        public void onFetchData(Flashcard flashcard, int flashcardMessage) {

        }

        @Override
        public void onError(String message) {
            Toast.makeText(DeskActivity.this, message, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onError: " + message);
        }

        @Override
        public void onFetchDataList(List<Desk> ListDesk) {
            // Handle List<Desk> data if needed
        }

        @Override
        public void onFetchDataListFlashcard(List<Flashcard> ListFlashcard, int idDesk) {
            if(ListFlashcard != null){
                listCard.addAll(ListFlashcard);
                idDesk = idDesk;
            }
            else{
                Toast.makeText(DeskActivity.this, "List flashcard is empty", Toast.LENGTH_SHORT).show();
            }

        }
    };
}