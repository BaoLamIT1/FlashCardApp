package com.example.flashcat.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flashcat.Activity.HomeActivity;
import com.example.flashcat.Activity.Login.LoginActivity;
import com.example.flashcat.Activity.SettingUser.SettingActivity;
import com.example.flashcat.Activity.SyncActivity;
import com.example.flashcat.Database.DatabaseApp;
import com.example.flashcat.Model.Desk;
import com.example.flashcat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {
    private Button btnSync;
    private Button btnLogin;
    private Button btnLogout;
    private TextView txtComplete;
    private TextView txtOnProgress;
    private TextView txtDesk;
    private DatabaseApp db;
    private ArrayList<Desk> deskArrayList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int complete = 0;
    private int onprogress = 0;
    private HomeActivity homeActivity;
    private TextView txtName;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        ImageButton btnSetting = rootView.findViewById(R.id.btn_setting);
        btnSync = rootView.findViewById(R.id.btn_sync);
        btnLogin = rootView.findViewById(R.id.btn_Login);
        btnLogout = rootView.findViewById(R.id.btn_Logout);
        txtComplete = rootView.findViewById(R.id.txt_number_complete);
        txtOnProgress = rootView.findViewById(R.id.txt_number_onProgress);
        txtDesk = rootView.findViewById(R.id.txt_number_desk);
        txtName = rootView.findViewById(R.id.txt_username);
        homeActivity = (HomeActivity) getActivity();
        db = new DatabaseApp(getContext());
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
        }
        else {
            txtName.setText("FlashCat");
        }
        if(!txtName.getText().equals("FlashCat"))
        {
            btnSync.setEnabled(false);
            btnLogout.setEnabled(false);
            btnSync.setTextColor(Color.LTGRAY);
            btnLogout.setTextColor(Color.LTGRAY);
        }
        else if(txtName.getText().equals("FlashCat"))
        {
            btnLogin.setEnabled(false);
            btnLogin.setTextColor(Color.LTGRAY);
        }


        deskArrayList = new ArrayList<>();
        deskArrayList = db.getAllDesk();

        txtDesk.setText(String.valueOf(deskArrayList.size()));
        for (Desk desk:deskArrayList){
            if(desk.isStatus_deck()==true){
                complete++;
            }
            else if(desk.isStatus_deck()==false){
                onprogress++;
            }
        }
        txtComplete.setText(String.valueOf(complete));
        txtOnProgress.setText(String.valueOf(onprogress));
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), SettingActivity.class);
                i.putExtra("Name",txtName.getText());
                startActivity(i);
            }
        });
        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), SyncActivity.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        return rootView;
    }

}