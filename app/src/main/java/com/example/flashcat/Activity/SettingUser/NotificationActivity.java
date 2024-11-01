package com.example.flashcat.Activity.SettingUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.flashcat.Database.DatabaseApp;
import com.example.flashcat.Model.Desk;
import com.example.flashcat.R;
import com.example.flashcat.Receiver.ReminderReceiver;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NotificationActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private MaterialSwitch swWordReminder;
    private Spinner spDeskRemind;
    private Button btnTimeStart;
    private Button btnTimeFinish;
    private Button btnSaveNotification;

    private DatabaseApp db;
    private static final int REQUEST_TIME_PICKER = 1;
    private static final int REQUEST_TIME_PICKER_FOR_FINISH = 2;
    private long startTime;
    private long endTime;
    private String selectedDesk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        db = new DatabaseApp(this);
        findID();



        ArrayList<Desk> desks = db.getAllDesk();
        ArrayList<String> deskNames = new ArrayList<>();
        for(Desk desk : desks){
            deskNames.add(desk.getName_deck());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,deskNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDeskRemind.setAdapter(adapter);
        spDeskRemind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDesk = deskNames.get(position);
                Intent intent = new Intent("desk-name-event");
                intent.putExtra("deskName", selectedDesk);
                LocalBroadcastManager.getInstance(NotificationActivity.this).sendBroadcast(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có gì được chọn
            }
        });
        if (ActivityCompat.checkSelfPermission(NotificationActivity.this,
                Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED)
        {
            swWordReminder.setChecked(false);
        }else{
            swWordReminder.setChecked(true);
        }

        swWordReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ActivityCompat.checkSelfPermission(NotificationActivity.this,
                        Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[] {Manifest.permission.POST_NOTIFICATIONS}, 1);
                }
                swWordReminder.setChecked(isChecked);
            }
        });
        btnTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePickerActivity();
            }
        });
        btnTimeFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePickerActivityForFinish();
            }
        });
        btnSaveNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(swWordReminder.isChecked()){
                        Intent intent = new Intent(NotificationActivity.this, ReminderReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationActivity.this, 0, intent, PendingIntent.FLAG_MUTABLE);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        long timeButtonClick = System.currentTimeMillis();

                        alarmManager.set(AlarmManager.RTC_WAKEUP, startTime - timeButtonClick, pendingIntent);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, endTime - timeButtonClick,pendingIntent);

                        finish();
                    }

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void findID(){
        btnBack = findViewById(R.id.back_notification);
        swWordReminder = findViewById(R.id.switch_word_reminder);
        spDeskRemind = findViewById(R.id.sp_select_desk_remind);
        btnTimeStart = findViewById(R.id.btn_select_timeStart);
        btnTimeFinish = findViewById(R.id.btn_select_timeFinish);
        btnSaveNotification = findViewById(R.id.btn_notification_save);
    }
    private void openTimePickerActivity() {
        // Tạo Intent để mở TimePickerActivity
        Intent intent = new Intent(NotificationActivity.this, TimePickerActivity.class);
        // Bắt đầu Activity để hiển thị giao diện đồng hồ
        startActivityForResult(intent, REQUEST_TIME_PICKER);
    }
    private void openTimePickerActivityForFinish() {
        Intent intent = new Intent(NotificationActivity.this, TimePickerActivity.class);
        startActivityForResult(intent, REQUEST_TIME_PICKER_FOR_FINISH);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TIME_PICKER && resultCode == RESULT_OK && data != null) {
            int hour = data.getIntExtra("hour",0);
            int minute = data.getIntExtra("minute",0);
            btnTimeStart.setText(String.format("%02d : %02d", hour, minute));
            startTime = 3600000 * hour + 60000 *minute;


        } else if (requestCode == REQUEST_TIME_PICKER_FOR_FINISH && resultCode == RESULT_OK && data != null) {
            int hour = data.getIntExtra("hour", 0);
            int minute = data.getIntExtra("minute", 0);
            btnTimeFinish.setText(String.format("%02d : %02d", hour, minute));
            endTime =  3600000 * hour + 60000 *minute;

        }

    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "LearnReminder";
            String description = "Channel for reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyRemind",name,importance);
            channel.setDescription(description);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}