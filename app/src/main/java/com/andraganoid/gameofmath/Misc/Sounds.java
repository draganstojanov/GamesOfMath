package com.andraganoid.gameofmath.Misc;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.andraganoid.gameofmath.R;

import java.util.HashMap;

public class Sounds {

    private SoundPool soundPool = null;
    private HashMap <Integer, Integer> sounds;
    //  private int soundsToLoad;
    public static final int FIREWORK = 0;
    public static final int BEST_RESULT = 3;
    public static final int TIME_IS_OUT = 4;
    public static final int GET_BONUS = 5;
    public static final int USE_BONUS = 6;
    public static final int RIGHT_ANSWER = 7;
    public static final int WRONG_ANSWER = 8;
    public static final int LOST_LIFE = 9;
    public static final int REWARD = 10;
    public static final int START = 11;
  //  public static final int SILENCE = 12;
    public static final int NO_BONUS = 12;


    private static Sounds mathSounds = null;
    private Context mContext;
    //   private SoundReady readyCallback = null;


//    public Sounds(Context context) {
//        //  this();
//        mContext = context;
//    }


    public static Sounds getInstance(Context context) {

        if (mathSounds == null) {
            mathSounds = new Sounds(context);
        }
        return mathSounds;
    }


    private Sounds(Context context) {

        mContext = context;

        new AsyncTask <Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                initSoundPool();
                return null;
            }
        }.execute();


    }


    private void initSoundPool() {

        sounds = new HashMap <>();


        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool(13, AudioManager.STREAM_MUSIC, 0);
        } else {
            AudioAttributes attrs = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(15)
                    .setAudioAttributes(attrs)
                    .build();
        }

        sounds.put(FIREWORK, soundPool.load(mContext, R.raw.firework1, 1));
        sounds.put(1, soundPool.load(mContext, R.raw.firework2, 1));
        sounds.put(2, soundPool.load(mContext, R.raw.firework3, 1));
        sounds.put(BEST_RESULT, soundPool.load(mContext, R.raw.new_best, 1));
        sounds.put(TIME_IS_OUT, soundPool.load(mContext, R.raw.beep_timer, 1));
        sounds.put(GET_BONUS, soundPool.load(mContext, R.raw.get_bonus, 1));
        sounds.put(USE_BONUS, soundPool.load(mContext, R.raw.use_bonus, 1));
        sounds.put(RIGHT_ANSWER, soundPool.load(mContext, R.raw.right_answer, 1));
        sounds.put(WRONG_ANSWER, soundPool.load(mContext, R.raw.wrong_answer, 1));
        sounds.put(LOST_LIFE, soundPool.load(mContext, R.raw.fail_game, 1));
        sounds.put(REWARD, soundPool.load(mContext, R.raw.game_reward, 1));
        sounds.put(START, soundPool.load(mContext, R.raw.beep_start, 1));
      //  sounds.put(SILENCE, soundPool.load(mContext, R.raw.silence, 1));
        sounds.put(NO_BONUS, soundPool.load(mContext, R.raw.no_bonus, 1));
        //  soundsToLoad = sounds.size();
//
//        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
//            @Override
//            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//                Log.d("SOUNDPOOL: ", "loaded soundid: " + sampleId);
//                if (--soundsToLoad == 0 && readyCallback != null) {
//                    readyCallback.onSoundReady();
//                }
//            }
//        });
//

    }


    public void playSomeMusic(int sound, int priority) {
        try {
            soundPool.play(sounds.get(sound), 1, 1, priority, 0, 1f);
        } catch (NullPointerException e) {
            Log.e("playSomeMusic: ", e.toString());
        }


    }

}

