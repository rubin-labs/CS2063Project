package com.rubinlabs.morsecode;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EnglishToMorseCodeActivity extends AppCompatActivity {
    private EditText e2mInput;
    private TextView e2mOutput;
    private MorseCodePlayer morseCodePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("English to Morse Code");
        setContentView(R.layout.activity_english_to_morse_code);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Content of Activity
        e2mInput = findViewById(R.id.e2mInputTextView);
        e2mOutput = findViewById(R.id.e2mOutputTextView);
        FloatingActionButton translateButton = findViewById(R.id.translateButton);

        // Create Morse Code Translator Engine
        MorseCodeTranslator mctEngine = MorseCodeTranslator.getInstance();

        // Create MorseCodePlayer
        morseCodePlayer = new MorseCodePlayer(this);

        translateButton.setOnClickListener(view -> {
            String myInput = e2mInput.getText().toString();
            myInput = mctEngine.englishWordToMorseWord(myInput);
            e2mOutput.setText(myInput);

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.reset_button) {
            e2mInput.setText(null);
            e2mInput.setSelection(0);
            e2mOutput.setText(null);
        }

        return super.onOptionsItemSelected(item);
    }

}
