package com.example.flashcat.Activity.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flashcat.Activity.HomeActivity;
import com.example.flashcat.Database.DatabaseApp;
import com.example.flashcat.Model.Account;
import com.example.flashcat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText edEmailLogin;
    private TextInputEditText edPasswordLogin;
    private CheckBox cbRemember;
    private TextView btnForgot;
    private Button btnLogin;
    private Button btnLocal;
    private TextView btnSignup;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";
    private static final String REMEMBER_ME_KEY = "rememberMe";
    private DatabaseApp db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findID();
        Intent intent = getIntent();
        if(intent != null) {
            String email = intent.getStringExtra("email");
            String password = intent.getStringExtra("password");
            if(email != null && password != null) {
                edEmailLogin.setText(email);
                edPasswordLogin.setText(password);
            }
        }
        loadRememberMeStatus();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
       btnSignup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(LoginActivity.this, SignupActivity.class);
               startActivity(i);
           }
       });
       btnLocal.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               db= new DatabaseApp(LoginActivity.this);
               db.deleteAllTables();
               db.addAccount(new Account("local","FlashCard","FlashCard","No","12345678","dsadsa","dsdsad"));
               Intent i = new Intent(LoginActivity.this, HomeActivity.class);
               startActivity(i);
           }
       });
    }
    public void findID()
    {
        edEmailLogin = findViewById(R.id.ed_email_login);
        edPasswordLogin = findViewById(R.id.ed_password_login);
        cbRemember = findViewById(R.id.cb_Remember);
        btnForgot = findViewById(R.id.btn_Forgot);
        btnLogin = findViewById(R.id.btn_Login);
        btnLocal = findViewById(R.id.btn_Local);
        btnSignup = findViewById(R.id.btn_signup_login);
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
    }
    public void login(){
        db = new DatabaseApp(this);
        db.deleteAllTables();
        String email, pass;
        email = edEmailLogin.getText().toString();
        pass = edPasswordLogin.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Please enter password!",Toast.LENGTH_SHORT).show();
            return;
        }
        if (cbRemember.isChecked()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(EMAIL_KEY, email);
            editor.putString(PASSWORD_KEY, pass);
            editor.putBoolean(REMEMBER_ME_KEY, true);
            editor.apply();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Login successful",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void loadRememberMeStatus() {
        boolean rememberMe = sharedPreferences.getBoolean(REMEMBER_ME_KEY, false);
        if (rememberMe) {
            String email = sharedPreferences.getString(EMAIL_KEY, "");
            String password = sharedPreferences.getString(PASSWORD_KEY, "");
            edEmailLogin.setText(email);
            edPasswordLogin.setText(password);
            cbRemember.setChecked(true);
        }
    }
}