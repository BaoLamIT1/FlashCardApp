package com.example.flashcat.Fragment;


import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flashcat.Activity.Desk.DeskActivity;
import com.example.flashcat.Activity.WordActivity;
import com.example.flashcat.Model.Dictionary.WordItem;
import com.example.flashcat.Model.Flashcard;
import com.example.flashcat.Touch.DeskItemTouchHelper;
import com.example.flashcat.Touch.ItemTouchHelperListener;
import com.example.flashcat.Adapter.HomeDeskAdapter;
import com.example.flashcat.Database.DatabaseApp;
import com.example.flashcat.MainActivity;
import com.example.flashcat.Model.Desk;
import com.example.flashcat.R;
import com.example.flashcat.api.OnFetchDataListener;
import com.example.flashcat.api.RequestManagerDesk;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements ItemTouchHelperListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Desk> listDesk;
    private HomeDeskAdapter adapterDesk;
    private RecyclerView recyclerViewDesk;
    private SearchView btnSearch;
    private TextView txtName;
    private Button btnNotification;
    private TextView btnSeeAll;
    public boolean isVertical = true;
    public DatabaseApp db;
    private static final String ARG_USERNAME = "userName";


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }

        listDesk = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeString1 = "2024-03-31 15:30:00";
        String dateTimeString2 = "2024-04-01 10:00:00";
        String dateTimeString3 = "2024-04-02 14:45:00";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            db = new DatabaseApp(getContext());
           // db.addDesk(new Desk(1, "Family", false, LocalDateTime.parse(dateTimeString1, formatter), "11", 4));
//            db.addDesk(new Desk(2, "Office", true, LocalDateTime.parse(dateTimeString2, formatter), "12", 3));
            listDesk = db.getAllDesk();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        btnSearch = rootView.findViewById(R.id.searchDesk);
        txtName = rootView.findViewById(R.id.txtName);
        btnSeeAll = rootView.findViewById(R.id.btnSeeAll);
        recyclerViewDesk = rootView.findViewById(R.id.lst_desk);
        btnSearch.setQueryHint("Search desk...");
        //use firebase
        if(db.getAccount().size()<1){
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if(currentUser!=null){
                String userEmail = currentUser.getEmail();

                Log.d("k", "onCreateView: "+ userEmail);
                if(userEmail!=null){
                    String userId = userEmail.replace("@gmail.com", "");
                    Log.d("k", "onCreateView: "+ userId);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("accounts").child(userId);

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String name = dataSnapshot.child("first_name").getValue(String.class);
                                txtName.setText(name);
                                Log.d("k", "onCreateView: "+ name);
                            }
                            else {
                                Log.d("FirebaseData", "No data exists for userId: " + userId);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            else {
                txtName.setText("FlashCat");

            }
            RequestManagerDesk requestManagerDesk = new RequestManagerDesk(getContext());
            Log.d("list", "onCreateView: "+"1");
            requestManagerDesk.getAllDesks(listener);
            Log.d("list", "onCreateView: "+"1");
        }
        else {
            txtName.setText("FlashCat");

        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewDesk.setLayoutManager(layoutManager);
        adapterDesk =  new HomeDeskAdapter(listDesk,getContext());
        recyclerViewDesk.setAdapter(adapterDesk);

        ItemTouchHelper.SimpleCallback simpleCallback = new DeskItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerViewDesk);
        recyclerViewDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnSeeAll.setPaintFlags(btnSeeAll.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        btnSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVertical) {
                    LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewDesk.setLayoutManager(horizontalLayoutManager);
                    btnSeeAll.setText("See All");
                } else {
                    LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerViewDesk.setLayoutManager(verticalLayoutManager);
                    btnSeeAll.setText("Collapse");
                }
                isVertical = !isVertical;
                adapterDesk.notifyDataSetChanged();
            }
        });
        btnSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterDesk.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterDesk.getFilter().filter(newText);
                return true;
            }
        });
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        // Làm mới dữ liệu của fragment khi nó tiếp tục
        refreshData();
    }

    private void refreshData() {
        // Tải lại hoặc làm mới dữ liệu của fragment ở đây
        listDesk.clear();
        listDesk.addAll(db.getAllDesk());
        adapterDesk.notifyDataSetChanged();
    }
    private List<Desk> getListDesks() {
        List<Desk> list = new ArrayList<>();
        db = new DatabaseApp(getContext());
        list = db.getAllDesk();

        return list;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder) {
        if(viewHolder instanceof  HomeDeskAdapter.DeskViewHolder){
            int indexDelete = viewHolder.getAdapterPosition();
            Desk desk = listDesk.get(indexDelete);

            // Create an AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Are you sure you want to delete this item?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //remove item;
                    adapterDesk.removeItem(indexDelete);
                    db.deleteDesk(desk.getID_Deck());
                    // delete from server
                    deleteDesk(desk.getID_Deck());
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do not remove item, reset view
                    adapterDesk.notifyItemChanged(viewHolder.getAdapterPosition());
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void deleteDesk(int idDeck) {
        RequestManagerDesk requestManagerDesk = new RequestManagerDesk(getContext());
        requestManagerDesk.deleteDesk(new OnFetchDataListener() {
            @Override
            public void onFetchData(WordItem wordItem, String message) {
                // Not used in this context
            }

            @Override
            public void onFetchData(Desk Desk, int message) {
                // Desk deleted successfully
                Toast.makeText(getContext(), "Desk deleted successfully", Toast.LENGTH_SHORT).show();
                // Refresh data after deletion
                refreshData();
            }

            @Override
            public void onFetchData(Flashcard flashcard, int flashcardMessage) {

            }

            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFetchDataList(List<Desk> ListDesk) {
                // Not used in this context
            }

            @Override
            public void onFetchDataListFlashcard(List<Flashcard> ListFlashcard, int idDesk) {

            }
        }, idDeck);
    }



    private OnFetchDataListener listener = new OnFetchDataListener() {
        @Override
        public void onFetchData(WordItem wordItem, String message) {

        }

        @Override
        public void onFetchData(Desk Desk, int message) {

        }

        @Override
        public void onFetchData(Flashcard flashcard, int flashcardMessage) {

        }

        @Override
        public void onError(String message) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            Log.d("error", "onError: " + message);

        }

        @Override
        public void onFetchDataList(List<Desk> ListDesk) {
            if (ListDesk!=null){
              listDesk.addAll(ListDesk);
            }
            else {
                Log.d("loi", "onFetchDataList: ");
            }

        }

        @Override
        public void onFetchDataListFlashcard(List<Flashcard> ListFlashcard, int idDesk) {

        }
    };
}