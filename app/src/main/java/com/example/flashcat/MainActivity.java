package com.example.flashcat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.flashcat.Activity.HomeActivity;
import com.example.flashcat.Activity.Login.LoginActivity;
import com.example.flashcat.Database.DatabaseApp;
import com.example.flashcat.Fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private Button btnStart;
    private DatabaseApp db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    111);

        }
        btnStart = findViewById(R.id.btn_Start);
        db= new DatabaseApp(MainActivity.this);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.getAccount().size()>0)
                {
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);
                }
                else{
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }

            }
        });
    }

}