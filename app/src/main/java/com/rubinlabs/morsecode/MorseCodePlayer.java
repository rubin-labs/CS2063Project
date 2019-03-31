package com.rubinlabs.morsecode;

/* TODO
* Toggle flashlight
*
* */

import android.content.Context;
import android.media.MediaPlayer;

import java.util.ArrayList;

public class MorseCodePlayer {
    private MediaPlayer mp;
    private int time_unit;

    public MorseCodePlayer(Context activity_context) {
        mp = MediaPlayer.create(activity_context, R.raw.dah);
        time_unit = mp.getDuration() / 3;
        mp.setLooping(true);
    }

    public void playCodes(ArrayList<Integer> codes) {
        for (int i = 0; i < codes.size(); i++) {
            play(codes.get(i));
        }
    }

    public void play(int code) {
        try {
            switch (code) {
                case 0:
                    mp.start();
                    Thread.sleep(time_unit);
                    mp.pause();
                    mp.reset();
                    break;
                case 1:
                    mp.start();
                    Thread.sleep(3 * time_unit);
                    mp.pause();
                    mp.reset();
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
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
