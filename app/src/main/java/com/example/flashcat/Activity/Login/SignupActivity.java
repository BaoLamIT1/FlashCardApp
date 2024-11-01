package com.example.flashcat.Activity.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flashcat.Model.Account;
import com.example.flashcat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private TextInputEditText edFirstName;
    private TextInputEditText edLastName;
    private TextInputEditText edEmailSignup;
    private TextInputEditText edUsername;
    private TextInputEditText edPasswordSignup;
    private TextInputEditText edRetypePassword;
    private Button btnRegister;
    private TextView btnSignin;
    private FirebaseAuth mAuth;
    private Account acc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findID();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
    public void findID(){
        edFirstName = findViewById(R.id.ed_first_name);
        edLastName = findViewById(R.id.ed_last_name);
        edEmailSignup = findViewById(R.id.ed_email_singup);
        edUsername = findViewById(R.id.ed_username);
        edPasswordSignup = findViewById(R.id.ed_password_signup);
        edRetypePassword = findViewById(R.id.ed_retype_password);
        btnRegister = findViewById(R.id.btn_Register);
        btnSignin = findViewById(R.id.btn_signin);
        mAuth = FirebaseAuth.getInstance();
    }
    public void register(){
        String fname,lname,uname,email, pass,repass;
        fname = edFirstName.getText().toString();
        lname = edLastName.getText().toString();
        email = edEmailSignup.getText().toString();
        uname = edUsername.getText().toString();
        pass = edPasswordSignup.getText().toString();
        repass = edRetypePassword.getText().toString();
        String userId = email.replace("@gmail.com", "");
        acc = new Account(uname,fname,lname,pass,email);
        if(TextUtils.isEmpty(fname)){
            Toast.makeText(this, "Please enter first name!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(lname)){
            Toast.makeText(this, "Please enter last name!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(uname)){
            Toast.makeText(this, "Please enter user name!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Please enter password!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(repass)){
            Toast.makeText(this, "Please re-enter password!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!acc.validate_first_name(acc.getFirst_name())){
            Toast.makeText(this, "First name is not in the correct format",Toast.LENGTH_SHORT).show();
            edFirstName.getError() ;
            return;
        }
        if(!acc.validate_last_name(acc.getLast_name())){
            Toast.makeText(this, "Last name is not in the correct format",Toast.LENGTH_SHORT).show();
            edLastName.getError();
            return;
        }
        if(!acc.validate_password(acc.getPassword())){
            Toast.makeText(this, "User name is not in the correct format",Toast.LENGTH_SHORT).show();
            edPasswordSignup.getError();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseRef = database.getReference("accounts");
                    Account account = new Account(uname,fname, lname,pass, email);
                    databaseRef.child(userId).setValue(account)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"Register successful",Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                                        i.putExtra("email", email);
                                        i.putExtra("password", pass);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(getApplicationContext(),"Failed to save user data",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(),"Register failed",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}