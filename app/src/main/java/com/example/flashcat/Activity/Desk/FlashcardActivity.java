package com.example.flashcat.Activity.Desk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.flashcat.Adapter.FlashcatAdapter;
import com.example.flashcat.Database.DatabaseApp;
import com.example.flashcat.Model.Desk;
import com.example.flashcat.Model.Flashcard;
import com.example.flashcat.R;

import java.util.ArrayList;
import java.util.Stack;

public class FlashcardActivity extends AppCompatActivity implements View.OnDragListener{
    private ImageButton btnBack;
    private RecyclerView recyclerFlashcard;
    private ImageButton btnReturnFlashcard;
    private TextView txtNumber;
    private ImageButton btnFlashcardStar;
    private ArrayList<Flashcard> flashcardArrayList;
    private DatabaseApp db;
    private FlashcatAdapter adapter;
    private String flipFrom;
    private  int idFlashcard;
    private int idDesk;
    private int currentPosition = 0; // Theo dõi vị trí hiện tại của flashcard được hiển thị
    private LinearLayout ll_UnLearned;
    private LinearLayout ll_Learned;
    private TextView txt_Learned;
    private int wordLearned = 0;
    private TextView txt_UnLearned;
    private int wordUnLearned = 0;
    private int size = 0;
    private Desk desk;
    private Stack<Flashcard> flashcardStack = new Stack<>();
    int position = 1; // Biến để lưu trữ vị trí của item đang được kéo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        findID();

        //Nhan du lieu
        Intent intent = getIntent();
        if(intent!=null){
            Bundle extras = intent.getExtras();
            if (extras != null) {
                flipFrom = extras.getString("flipFrom");
                idFlashcard = extras.getInt("idFlashcard");
                idDesk = extras.getInt("idDesk");

            }
        }

        //tao đb
        db = new DatabaseApp(this);
        flashcardArrayList = new ArrayList<>();
        flashcardArrayList = db.getAllFlashcardByDeskID(idDesk);
        desk = db.getDesk(idDesk);
        size = flashcardArrayList.size();

        //keo tha flashcard
        ll_UnLearned.setOnDragListener(this::onDrag);
        ll_Learned.setOnDragListener(this::onDrag);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerFlashcard.setLayoutManager(layoutManager);
        recyclerFlashcard.setNestedScrollingEnabled(false);
        adapter = new FlashcatAdapter(flashcardArrayList,this);
        recyclerFlashcard.setAdapter(adapter);

        //Hiển thị chỉ số flashcard
        txtNumber.setText(String.valueOf(position) + "/" + size);

        // Hiển thị flashcard hiện tại
        displayFlashcard(currentPosition);
        recyclerFlashcard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setTag("Drag");
                ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
                String[] mineTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData data = new ClipData(v.getTag().toString(), mineTypes, item);

                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);

                v.startDrag(data,dragShadowBuilder,v,0);
                return true;
            }
        });
        recyclerFlashcard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.setTag("Drag");
                ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
                String[] mineTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData data = new ClipData(v.getTag().toString(), mineTypes, item);

                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);

                v.startDrag(data,dragShadowBuilder,v,0);
                return true;
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnReturnFlashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flashcardStack.isEmpty()) {
                    Flashcard lastDraggedFlashcard = flashcardStack.pop();
                    flashcardArrayList.add(0,lastDraggedFlashcard);
                    adapter.notifyDataSetChanged();
                    Log.d("return", "onClick: " + lastDraggedFlashcard.getTerm());
                    if (lastDraggedFlashcard.isStatus() == true) {
                        wordLearned--;
                    } else {
                        wordUnLearned--;
                    }

                    // Update the text views displaying the number of learned and unlearned words
                    txt_Learned.setText(String.valueOf(wordLearned));
                    txt_UnLearned.setText(String.valueOf(wordUnLearned));
                    // Update the position and the flashcard number display
                    position--;
                    txtNumber.setText(String.valueOf(position) + "/" + size);
                }
            }
        });
    }
    private int findPositionById(int idFlashcard) {
        for (int i = 0; i < flashcardArrayList.size(); i++) {
            if (flashcardArrayList.get(i).getID_Flashcard() == idFlashcard) {
                return i;
            }
        }
        return -1;
    }
    private void displayFlashcardSide(String side) {
        if (side != null) {
            db = new DatabaseApp(FlashcardActivity.this);
            Flashcard flashcard = db.getFlashcardByID(idFlashcard);
            if (flashcard != null) {
                if (side.equals("front")) {

                } else if (side.equals("back")) {
                }
            }
        }
    }
    public  void findID(){
        btnBack = findViewById(R.id.back_flashcard);
        recyclerFlashcard = findViewById(R.id.list_item_flashcard);
        btnReturnFlashcard = findViewById(R.id.btn_return_flashcard);
        txtNumber = findViewById(R.id.txt_number_flashcard);
        ll_UnLearned = findViewById(R.id.ll_lose);
        txt_Learned = findViewById(R.id.txt_win);
        ll_Learned = findViewById(R.id.ll_win);
        txt_UnLearned = findViewById(R.id.txt_lose);
    }

    // Phương thức để hiển thị số thứ tự của flashcard hiện tại
    private void displayCurrentFlashcardNumber() {
        int firstVisibleItemPosition = ((LinearLayoutManager) recyclerFlashcard.getLayoutManager()).findFirstVisibleItemPosition();
        if (firstVisibleItemPosition != RecyclerView.NO_POSITION) {
            txtNumber.setText(String.valueOf(firstVisibleItemPosition + 1) + "/" + size);
        }
    }
    // Phương thức để hiển thị một flashcard tại một vị trí cụ thể
    private void displayFlashcard(int position) {
        if (position >= 0 && position < flashcardArrayList.size()) {
            Flashcard flashcard = flashcardArrayList.get(position);
            adapter.setCurrentPosition(position); // Cập nhật vị trí hiện tại của adapter
            adapter.notifyDataSetChanged(); // Thông báo cho adapter về sự thay đổi dữ liệu
        }
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();

        switch(action){
            case DragEvent.ACTION_DRAG_STARTED:
                if(event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))
                    return true;
                return false;
            case DragEvent.ACTION_DRAG_ENTERED:
                if (v.getBackground() != null) {
                    v.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                    v.invalidate();
                }
                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                if (v.getBackground() != null) {
                    v.getBackground().clearColorFilter();
                    v.invalidate();
                }
                return true;
            case DragEvent.ACTION_DROP:

                ClipData.Item item = event.getClipData().getItemAt(0);
                String dragDate = item.getText().toString();
                if (v.getBackground() != null) {
                    v.getBackground().clearColorFilter();
                    v.invalidate();
                }
                View vw = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) vw.getParent();

                owner.removeView(vw);

                LinearLayout container = (LinearLayout) v;
                container.addView(vw);
                vw.setVisibility(View.GONE);
                // Xử lý khi kéo vào ll_Learned
                if (v.getId() == R.id.ll_win) {
                    wordLearned++;
                    position++;
                    flashcardArrayList.get(currentPosition).setStatus(true);
                    txt_Learned.setText(String.valueOf(wordLearned));
                    Log.d("learn", "onDrag: wordlearned " + wordLearned);
                    //xu ly status cho desk khi da hoc thuoc het
                    if(wordLearned == size)
                    {
                        desk.setStatus_deck(true);
                        db.updateDesk(idDesk,desk);
                    }
                }
                // Xử lý khi kéo vào ll_UnLearned
                else if (v.getId() == R.id.ll_lose) {
                    wordUnLearned++;
                    position++;
                    flashcardArrayList.get(currentPosition).setStatus(false);
                    txt_UnLearned.setText(String.valueOf(wordUnLearned));
                }
                db.updateFlashcard(flashcardArrayList.get(currentPosition).getID_Flashcard(),flashcardArrayList.get(currentPosition));
                //Them vao stack
                flashcardStack.push(flashcardArrayList.get(currentPosition));
                // Cập nhật danh sách dữ liệu sau khi item được kéo đi
                flashcardArrayList.remove(currentPosition);
                if (flashcardArrayList.size()!=0) {
                    // Tạo RecyclerView mới
                    RecyclerView newRecyclerView = new RecyclerView(this);
                    // Thiết lập LayoutManager và Adapter cho RecyclerView mới
                    newRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
                    adapter = new FlashcatAdapter(flashcardArrayList, this);
                    newRecyclerView.setAdapter(adapter);
                    // Đặt RecyclerView mới vào ViewGroup của parent của RecyclerView cũ
                    owner.addView(newRecyclerView);
                    settouch(newRecyclerView);

                    // Hiển thị số thứ tự của flashcard hiện tại
                    txtNumber.setText(String.valueOf(position) + "/" + size);

                } else {
                    // Handle the case where flashcardArrayList is empty
                    Intent i = new Intent(FlashcardActivity.this,FlashcardResultActivity.class);
                    Bundle b = new Bundle();

                    b.putInt("Know", wordLearned);
                    b.putInt("Learning", wordUnLearned);
                    b.putInt("idFlashcard", idFlashcard);
                    b.putInt("idDesk",idDesk);
                    i.putExtras(b);
                    startActivityForResult(i,200);
                }

                // Thông báo cho adapter biết về sự thay đổi
                adapter.notifyDataSetChanged();
                displayFlashcard(currentPosition);

                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                if (v.getBackground() != null) {
                    v.getBackground().clearColorFilter();
                    v.invalidate();
                }
                return true;
            default:
                break;
        }
        return false;
    }
    public void settouch(RecyclerView rc){
        rc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setTag("Drag");
                ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
                String[] mineTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData data = new ClipData(v.getTag().toString(), mineTypes, item);

                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);

                v.startDrag(data,dragShadowBuilder,v,0);
                return true;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200)
            finish();
    }
}