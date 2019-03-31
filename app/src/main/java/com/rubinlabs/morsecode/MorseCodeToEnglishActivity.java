package com.rubinlabs.morsecode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MorseCodeToEnglishActivity extends AppCompatActivity {
    private EditText m2eInput;
    private TextView m2eOutput;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Morse Code to English");
        setContentView(R.layout.activity_morse_code_to_english);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // content
        m2eInput = findViewById(R.id.m2eInputTextView);
        m2eOutput = findViewById(R.id.m2eOutputTextView);
        FloatingActionButton morse_btn = findViewById(R.id.morseButton);
        Button char_btn = findViewById(R.id.nextCharacterButton);
        Button word_btn = findViewById(R.id.nextWordButton);

        // translator
        MorseCodeTranslator mct = MorseCodeTranslator.getInstance();


        m2eInput.setOnTouchListener((v, event) -> {
            v.onTouchEvent(event);
            InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return true;
        });

        morse_btn.setOnClickListener(view -> {
            String new_text = m2eInput.getText() + ".";
            m2eInput.setText(new_text);
            m2eInput.setSelection(new_text.length());
        });

        morse_btn.setOnLongClickListener(v -> {
            String new_text = m2eInput.getText() + "-";
            m2eInput.setText(new_text);
            m2eInput.setSelection(new_text.length());
            return  true;
        });

        char_btn.setOnClickListener(view -> {
            String new_text = m2eInput.getText() + " ";
            m2eInput.setText(new_text);
            m2eInput.setSelection(new_text.length());
            m2eOutput.setText(mct.morseWordToEnglishWord(new_text));
        });

        word_btn.setOnClickListener(view -> {
            String new_text = m2eInput.getText() + " / ";
            m2eInput.setText(new_text);
            m2eInput.setSelection(new_text.length());
            m2eOutput.setText(mct.morseWordToEnglishWord(new_text));
        });

        m2eInput.requestFocus();


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
            m2eInput.setText(null);
            m2eInput.setSelection(0);
            m2eOutput.setText(null);
        }

        return super.onOptionsItemSelected(item);
    }
}
