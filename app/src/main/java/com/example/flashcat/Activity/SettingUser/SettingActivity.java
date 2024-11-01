package com.example.flashcat.Activity.SettingUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.example.flashcat.Activity.HomeActivity;
import com.example.flashcat.R;
import com.google.android.material.materialswitch.MaterialSwitch;

public class SettingActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private Button btnEditProfile;
    private Button btnChangePassword;
    private Button btnNotiSetting;
    private Button btnManageStorage;
    private MaterialSwitch swSoundEffect;
    private MaterialSwitch swDarkMode;
    private Button btnAboutUs;
    private Button btnPrivacyPolicy;
    private boolean nightMODE;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findID();

        Intent i = getIntent();
        String name = i.getStringExtra("Name");
        if(name.equals("FlashCat"))
        {
            btnEditProfile.setEnabled(false);
            btnChangePassword.setEnabled(false);
            btnEditProfile.setTextColor(Color.LTGRAY);
            btnChangePassword.setTextColor(Color.LTGRAY);
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(SettingActivity.this, HomeActivity.class);
                i.putExtra("fragmentTag", "user");
                startActivity(i);
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingActivity.this, ChangePasswordActivity.class);
                startActivity(i);
            }
        });
        btnNotiSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingActivity.this, NotificationActivity.class);
                startActivity(i);
            }
        });
        btnManageStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingActivity.this, StorageActivity.class);
                startActivity(i);
            }
        });
        swSoundEffect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        //Xu ly dark mode
        //Lưu mode khi tắt app và quay trở lại vẫn giữ mode đã chọn
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMODE = sharedPreferences.getBoolean("night",false);
        if(nightMODE){
            swDarkMode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }
        swDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Cập nhật giá trị nightMODE khi trạng thái thay đổi
                nightMODE = isChecked;

                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }

                // Lưu giá trị nightMODE
                editor = sharedPreferences.edit();
                editor.putBoolean("night", isChecked);
                editor.apply(); // hoặc editor.commit();
            }
        });
        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void findID()
    {
        btnBack = findViewById(R.id.back_setting);
        btnEditProfile = findViewById(R.id.btn_edit_profile);
        btnChangePassword = findViewById(R.id.btn_change_password);
        btnNotiSetting = findViewById(R.id.btn_notification_settings);
        btnManageStorage = findViewById(R.id.btn_manage_storage);
        swSoundEffect = findViewById(R.id.switch_sound_effect);
        swDarkMode = findViewById(R.id.switch_dark_mode);
        btnAboutUs = findViewById(R.id.btn_about_us);
        btnPrivacyPolicy = findViewById(R.id.btn_privacy_policy);
    }

}