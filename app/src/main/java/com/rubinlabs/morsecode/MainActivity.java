package com.rubinlabs.morsecode;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Morse Code Translator");
        setContentView(R.layout.activity_main);

        FloatingActionButton e2m_btn = findViewById(R.id.English2MorseButton);
        FloatingActionButton m2e_btn = findViewById(R.id.Morse2EnglishButton);
        FloatingActionButton settings_btn = findViewById(R.id.SettingsButton);

        e2m_btn.setOnClickListener(v -> startActivityForResult(new Intent(MainActivity.this, EnglishToMorseCodeActivity.class),1));
        m2e_btn.setOnClickListener(v -> startActivityForResult(new Intent(MainActivity.this, MorseCodeToEnglishActivity.class),1));
        settings_btn.setOnClickListener(v -> startActivityForResult(new Intent(MainActivity.this, SettingsActivity.class),1));
    }
}
