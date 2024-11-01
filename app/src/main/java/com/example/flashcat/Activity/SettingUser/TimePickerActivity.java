package com.example.flashcat.Activity.SettingUser;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerActivity extends Activity implements TimePickerDialog.OnTimeSetListener {
    private boolean timeSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, hour, minute, true);
        timePickerDialog.setOnCancelListener(dialog -> {
            timeSelected = false;
            finish();
        });
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Intent intent = new Intent();
        intent.putExtra("hour", hourOfDay);
        intent.putExtra("minute", minute);
        setResult(RESULT_OK, intent);
        timeSelected = true;
        finish();
    }

    @Override
    public void finish() {
        if (timeSelected) {
            super.finish();
        } else {
            setResult(RESULT_CANCELED);
            super.finish();
        }
    }
}
