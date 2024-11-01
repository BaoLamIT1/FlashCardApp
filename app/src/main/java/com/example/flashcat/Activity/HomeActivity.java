package com.example.flashcat.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.flashcat.Activity.Desk.DeskActivity;
import com.example.flashcat.Activity.Practice.PracticeActivity;
import com.example.flashcat.Database.DatabaseApp;
import com.example.flashcat.Fragment.DictionaryFragment;
import com.example.flashcat.Fragment.HomeFragment;
import com.example.flashcat.Fragment.UserFragment;
import com.example.flashcat.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

public class HomeActivity extends AppCompatActivity {
    private DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private BottomNavigationView bottomNavigationView;
    private Dialog dialogDesk;
    private enum CurrentPage {
        HOME, PRACTICE, ADD, DICTIONARY, USER
    }
    String userName= "Linda";

    public String getUserName() {
        return userName;
    }

    private CurrentPage currentPage = CurrentPage.HOME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_home);
        replaceFragment(new HomeFragment());


        bottomNavigationView=  findViewById(R.id.bottomNavigationView);
        if (getIntent() != null) {
            String fragmentTag = getIntent().getStringExtra("fragmentTag");
            Log.d("curr", "onCreate: "+ fragmentTag);

            if (fragmentTag != null) {
                // Hiển thị Fragment tương ứng với fragmentTag
                if (fragmentTag.equals("home")) {
                    HomeFragment homeFragment =new HomeFragment();
                    replaceFragment(homeFragment);
                    currentPage = CurrentPage.HOME;
                } else if (fragmentTag.equals("dictionary")) {
                    replaceFragment(new DictionaryFragment());
                    currentPage = CurrentPage.DICTIONARY;
                    bottomNavigationView.setSelectedItemId(R.id.menu_Dictionary);

                } else if (fragmentTag.equals("user")) {
//                    UserFragment userFragment = new UserFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("UserName", userName);
//                    userFragment.setArguments(bundle);
//                    replaceFragment(userFragment);
                    replaceFragment(new UserFragment());
                    currentPage = CurrentPage.USER;
                    bottomNavigationView.setSelectedItemId(R.id.menu_User);



                }
            }
        }
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int i = item.getItemId();
            if (i == R.id.menu_Home) {
                replaceFragment(new HomeFragment());
                currentPage = CurrentPage.HOME;
            } else if (i == R.id.menu_Practice) {
                showBottomDialog();
                currentPage = CurrentPage.PRACTICE;


            } else if (i == R.id.menu_Add) {
                openDialogNewDesk(Gravity.CENTER);
                currentPage = CurrentPage.ADD;
            } else if (i == R.id.menu_Dictionary) {
                replaceFragment(new DictionaryFragment());
                currentPage = CurrentPage.DICTIONARY;
            } else if (i == R.id.menu_User) {
                replaceFragment(new UserFragment());
                currentPage = CurrentPage.USER;
            }


            return true;
        });
    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout, fragment);
        fragmentTransaction.commit();
    }
    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_practice);

        Button btnPractice = dialog.findViewById(R.id.btn_Practice);
        Button btnTest = dialog.findViewById(R.id.btn_Test);
        btnPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                View overlay = findViewById(R.id.overlay);
                overlay.setVisibility(View.GONE);
                Intent intent = new Intent(HomeActivity.this, PracticeActivity.class);
                // Inlcude a value to indicate that the user wants to make a Practice
                intent.putExtra("IS_PRACTICE", true);
                startActivity(intent);

            }
        });
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                View overlay = findViewById(R.id.overlay);
                overlay.setVisibility(View.GONE);
                Intent intent = new Intent(HomeActivity.this, PracticeActivity.class);
                intent.putExtra("IS_PRACTICE", false);
                startActivity(intent);


            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (currentPage != CurrentPage.HOME) {
                    replaceFragment(new HomeFragment());
                    currentPage = CurrentPage.HOME;
                    bottomNavigationView.setSelectedItemId(R.id.menu_Home);
                    View overlay = findViewById(R.id.overlay);
                    overlay.setVisibility(View.GONE);
                }
            }
        });
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
        View overlay = findViewById(R.id.overlay);
        overlay.setVisibility(View.VISIBLE);

    }
    private  void openDialogNewDesk(int gravity ){
        dialogDesk = new Dialog(this);
        dialogDesk.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogDesk.setContentView(R.layout.layout_dialog_create_desk);
        Window window = dialogDesk.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT) );
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        EditText editNameDesk = dialogDesk.findViewById(R.id.ed_name_desk);
        // Find ImageButton inside the dialog layout
        ImageButton closeButton = dialogDesk.findViewById(R.id.btn_back_dialog_desk);
        if (closeButton != null) {
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeDialogAndReturnToHome();
                }
            });
        }
        Button done = dialogDesk.findViewById(R.id.btn_done_dialog_desk);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDateTime currentDateTime = LocalDateTime.now();
                String currentDateTimeString = currentDateTime.format(DATE_TIME_FORMATTER);
                Intent i = new Intent(HomeActivity.this, DeskActivity.class);
                i.putExtra("NameDesk",editNameDesk.getText().toString());
                i.putExtra("CreatedDay", currentDateTimeString);
                startActivityForResult(i,110);

            }
        });


        dialogDesk.setCanceledOnTouchOutside(false);
        dialogDesk.show();
        View overlay = findViewById(R.id.overlay);
        overlay.setVisibility(View.VISIBLE);
    }
    private void closeDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            View overlay = findViewById(R.id.overlay);
            overlay.setVisibility(View.GONE);
        }
    }
    private void closeDialogAndReturnToHome() {
        closeDialog(dialogDesk);
        replaceFragment(new HomeFragment());
        currentPage = CurrentPage.HOME;
        bottomNavigationView.setSelectedItemId(R.id.menu_Home);
    }

}