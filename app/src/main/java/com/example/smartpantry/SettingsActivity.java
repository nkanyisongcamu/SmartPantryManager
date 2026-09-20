package com.example.smartpantry;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS = "smart_pantry_settings";
    private static final String ALERTS = "expiry_alerts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch expirySwitch = findViewById(R.id.switchExpiry);
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);

        expirySwitch.setChecked(prefs.getBoolean(ALERTS, true));

        expirySwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                prefs.edit().putBoolean(ALERTS, isChecked).apply());

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}
