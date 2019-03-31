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

import java.util.ArrayList;

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
        MorseCodeTranslator myTranslator = MorseCodeTranslator.getInstance();

        // Create MorseCodePlayer
        morseCodePlayer = new MorseCodePlayer(this);

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myInput = myTranslator.englishWordToMorseCode(e2mInput.getText().toString());
                char[] inputCharArray = myInput.toCharArray();
                ArrayList<Integer> mediaInstructions = new ArrayList<>();

                /*
                 * Character Codes
                 *  0 = dit '.'
                 *  1 = dah '-'
                 *  2 = symbol space
                 *  3 = letter space
                 *  4 = word space
                 */

                for(int i=0; i<inputCharArray.length; i++) {
                    switch(inputCharArray[i]) {
                        case '.':
                            mediaInstructions.add(0);
                            if (!(inputCharArray[i+1] == ' '))
                                mediaInstructions.add(2);
                            break;

                        case '-':
                            mediaInstructions.add(1);
                            if (!(inputCharArray[i+1] == ' '))
                                mediaInstructions.add(2);
                            break;

                        case ' ':
                            if (inputCharArray[i+1] == '/') {
                                mediaInstructions.add(4);
                                i = i + 2;
                            } else {
                                mediaInstructions.add(3);
                            }
                            break;
                    }
                }

                for (int i=0; i<mediaInstructions.size(); i++) {
                    myInput = myInput + mediaInstructions.get(i);
                }
                e2mOutput.setText(myInput);
            }
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
