package com.rubinlabs.morsecode;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EnglishToMorseCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_to_morse_code);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Content of Activity
        EditText e2mInput = findViewById(R.id.e2mInputTextView);
        TextView e2mOutput = findViewById(R.id.e2mOutputTextView);
        FloatingActionButton translateButton = findViewById(R.id.translateButton);

        // Create Morse Code Translator Engine
        MorseCodeTranslator mctEngine = MorseCodeTranslator.getInstance();

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myInput = e2mInput.getText().toString();
                myInput = mctEngine.englishWordToMorseWord(myInput);
                e2mOutput.setText(myInput);

            }
        });
    }

}
