package com.rubinlabs.morsecode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MorseCodeToEnglishActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse_code_to_english);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // content
        EditText morse_edittext = findViewById(R.id.m2eInputTextView);
        TextView english_textview = findViewById(R.id.m2eOutputTextView);
        FloatingActionButton morse_btn = findViewById(R.id.morseButton);
        Button char_btn = findViewById(R.id.nextCharacterButton);
        Button word_btn = findViewById(R.id.nextWordButton);

        // translator
        MorseCodeTranslator mct = MorseCodeTranslator.getInstance();

        morse_edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });

        morse_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_text = morse_edittext.getText() + ".";
                morse_edittext.setText(new_text);
                morse_edittext.setSelection(new_text.length());
            }
        });

        morse_btn.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                String new_text = morse_edittext.getText() + "-";
                morse_edittext.setText(new_text);
                morse_edittext.setSelection(new_text.length());
                return  true;
            }
        });

        char_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_text = morse_edittext.getText() + " ";
                morse_edittext.setText(new_text);
                morse_edittext.setSelection(new_text.length());
                english_textview.setText(mct.morseWordToEnglishWord(new_text));
            }
        });

        word_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_text = morse_edittext.getText() + " / ";
                morse_edittext.setText(new_text);
                morse_edittext.setSelection(new_text.length());
                english_textview.setText(mct.morseWordToEnglishWord(new_text));
            }
        });

        morse_edittext.requestFocus();


    }

}
