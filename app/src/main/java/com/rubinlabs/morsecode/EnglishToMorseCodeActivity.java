package com.rubinlabs.morsecode;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    private MediaPlayer myMediaPlayer;
    private CameraManager myCameraManager;
    private String myCameraID;
    private Thread myThread;
    private int time_unit;
    private boolean useFlashlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("English to Morse Code");
        setContentView(R.layout.activity_english_to_morse_code);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Get preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        useFlashlight = sharedPreferences.getBoolean("flash_switch", true);

        // Content of Activity
        e2mInput = findViewById(R.id.e2mInputTextView);
        e2mOutput = findViewById(R.id.e2mOutputTextView);
        FloatingActionButton translateButton = findViewById(R.id.translateButton);

        // Create Morse Code Translator Engine
        MorseCodeTranslator myTranslator = MorseCodeTranslator.getInstance();

        // Create Media Player
        myMediaPlayer = MediaPlayer.create(this, R.raw.dah);
        time_unit = myMediaPlayer.getDuration() / 3;

        // Create a Camera Manager object
        try {
            myCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            myCameraID = myCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myInput = myTranslator.englishWordToMorseCode(e2mInput.getText().toString());
                char[] inputCharArray = myInput.toCharArray();
                ArrayList<Integer> mediaInstructions = new ArrayList<>();

                e2mOutput.setText(myInput);


                /* Algorithm to play morse code:
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

                myThread = new Thread() {
                    Boolean isRunning = true;

                    @Override
                    public void run() {
                        int i = 0;
                        try {
                            while (i < mediaInstructions.size() && isRunning) {
                                switch (mediaInstructions.get(i)) {

                                    case 0:
                                        if (useFlashlight) { myCameraManager.setTorchMode(myCameraID, true); }
                                        myMediaPlayer.start();
                                        Thread.sleep(time_unit);
                                        break;
                                    case 1:
                                        if (useFlashlight) { myCameraManager.setTorchMode(myCameraID, true); }
                                        myMediaPlayer.start();
                                        Thread.sleep(3 * time_unit);
                                        break;
                                    case 2:
                                        Thread.sleep(time_unit);
                                        break;
                                    case 3:
                                        Thread.sleep(3 * time_unit);
                                        break;
                                    case 4:
                                        Thread.sleep(7 * time_unit);
                                        break;
                                }
                                if (useFlashlight) { myCameraManager.setTorchMode(myCameraID, false); }
                                if (myMediaPlayer.isPlaying()) {
                                    myMediaPlayer.pause();
                                    myMediaPlayer.seekTo(0);
                                }

                                i++;
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }

                    @Override
                    public void interrupt() {
                        super.interrupt();
                        isRunning = false;
                    }
                };

                myThread.start();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

    @Override
    public void onStop() {
        super.onStop();
        myMediaPlayer.release();

        if (myThread != null && myThread.isAlive()) {
            myThread.interrupt();
        }

        // Turn off flash if it is still on
        try {
            if (useFlashlight) { myCameraManager.setTorchMode(myCameraID, false); }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
