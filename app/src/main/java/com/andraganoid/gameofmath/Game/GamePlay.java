package com.andraganoid.gameofmath.Game;

import static com.andraganoid.gameofmath.Misc.Sounds.BEST_RESULT;
import static com.andraganoid.gameofmath.Misc.Sounds.FIREWORK;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andraganoid.gameofmath.DataBase.ScoreListCallback;
import com.andraganoid.gameofmath.DataBase.ScoreRepository;
import com.andraganoid.gameofmath.HighScores.HighScoresAdapter;
import com.andraganoid.gameofmath.HighScores.HighScoresTableAdapter;
import com.andraganoid.gameofmath.HighScores.Level;
import com.andraganoid.gameofmath.HighScores.Score;
import com.andraganoid.gameofmath.Misc.FullscreenCallback;
import com.andraganoid.gameofmath.Misc.Sounds;
import com.andraganoid.gameofmath.Operation.Calc;
import com.andraganoid.gameofmath.Operation.Task;
import com.andraganoid.gameofmath.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.plattysoft.leonids.ParticleSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GamePlay extends AppCompatActivity {

    public static Calc calc;
    public static Task task;
    public InterstitialAd fullscreenAd;
    public static boolean adIsShowing, fullscreenIsShowed;
    public SharedPreferences prefs;
    public SharedPreferences.Editor prefsEditor;
    public Typeface alertTypeface;
    public View board;
    public CountDownTimer cdt, intro, fireTimer;
    public Intent boardIntent;
    public boolean goMain, isEnd, goHiScore, colorChange;
    public DisplayMetrics metrics;
    public int shot, last, width, height;
    public Random rand;
    public ParticleSystem ps;
    public Sounds mathSounds;
    public int aCount;

    int[] fireDots = new int[]{
            R.drawable.fw_01,
            R.drawable.fw_02,
            R.drawable.fw_03,
            R.drawable.fw_04,
            R.drawable.fw_05,
            R.drawable.fw_06,
            R.drawable.fw_07,
            R.drawable.fw_08,
            R.drawable.fw_09,
            R.drawable.fw_10
    };
    ObjectAnimator animation;
    Keyframe kf0, kf1, kf2, kf3;
    float[] fx = new float[8];
    float[] fy = new float[8];

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        adIsShowing = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alertTypeface = ResourcesCompat.getFont(this, R.font.luckiestguy);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefsEditor = prefs.edit();
        mathSounds = Sounds.getInstance(getApplicationContext());
        rand = new Random();
        fullscreenIsShowed = false;
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, getString(R.string.ad_mob_math_fullscreen), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        fullscreenAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.d("InterstitialAdError", loadAdError.toString());
                        fullscreenAd = null;
                    }
                });
    }

    public void goFire() {
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        play(BEST_RESULT);
        shot = 25;
        last = 0;
        fireTimer = new CountDownTimer(10000, 200) {
            @Override
            public void onTick(long l) {
                last++;
                if (shot > 0) {
                    if (last > 5 || (rand.nextInt(1000) % 2 == 1)) {
                        doFire();
                    }
                }
            }

            @Override
            public void onFinish() {
            }
        };
        fireTimer.start();
    }

    void doFire() {
        findViewById(R.id.fireworks_wrapper).setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(rand.nextInt(width - 50), rand.nextInt(height - 50), 0, 0);
        findViewById(R.id.fireworks).setLayoutParams(params);
        shot--;
        last = 0;
        play(FIREWORK);
        ps = new ParticleSystem(this,
                200, fireDots[rand.nextInt(1000) % 10],
                1000 + 500 * (rand.nextInt(1000) % 9));
        ps.setSpeedRange(0.1f, 0.4f)
                .setRotationSpeed(300)
                .oneShot(findViewById(R.id.fireworks), 50 + rand.nextInt(150));
    }

    public void startAnimation(View view, int duration) {
        switch (getRnd()) {
            case 0:
                animRotAndShake(view, 15f, 3 * duration);
                break;
            case 1:
                animShake1(view, 16 * duration, 15);
                break;
            case 2:
                String r = "";
                switch (getRnd()) {
                    case 0:
                        r = "rotation";
                        break;
                    case 1:
                        r = "rotationX";
                        break;
                    case 2:
                        r = "rotationY";
                        break;
                }
                int e = (rand.nextInt(1000) % 2) == 1 ? 1 : -1;
                e = duration == 1 ? e : e * 10;
                animSimpleRot(view, r, e);
                break;
        }
    }

    int getRnd() {
        return rand.nextInt(1000) % 3;
    }

    void animRotAndShake(View view, float amplitude, int repeat) {
        kf0 = Keyframe.ofFloat(0, -amplitude);
        kf1 = Keyframe.ofFloat(.25f, 0f);
        kf2 = Keyframe.ofFloat(.5f, amplitude);
        kf3 = Keyframe.ofFloat(1f, 0);
        PropertyValuesHolder pvh = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2, kf3);
        animation = ObjectAnimator.ofPropertyValuesHolder(view, pvh);
        animation.setDuration(200).setRepeatCount(repeat);
        animation.start();
    }

    void animShake1(final View view, final int repeat, int amplitude) {
        int ay = -amplitude;
        for (int i = 0; i < 8; i++) {
            if (i % 2 == 1) {
                fx[i] = 0;
                fy[i] = 0;
            } else {
                fy[i] = ay;
                ay *= -1;
                fx[i] = -amplitude;
                {
                    if (i == 2 || i == 4) {
                        fx[i] = amplitude;
                    }
                }
            }
        }

        aCount = 0;
        intro = new CountDownTimer(5000, 66) {
            @Override
            public void onTick(long l) {
                if (aCount < repeat) {
                    view.setTranslationX(fx[aCount % 8]);
                    view.setTranslationY(fy[aCount % 8]);
                    aCount++;
                } else {
                    view.setTranslationX(0);
                    view.setTranslationY(0);
                    ctstop();
                }
            }

            @Override
            public void onFinish() {
            }
        }.start();
    }

    void ctstop() {
        intro.cancel();
    }

    void animSimpleRot(View view, String rotationType, int rotations) {//ROTATION2
        animation = ObjectAnimator.ofFloat(view, rotationType, 0f, rotations * 360f);
        if (rotations != 1) {
            animation.setInterpolator(new DecelerateInterpolator());
        }
        animation.setDuration((Math.abs(rotations) == 1 ? 1000 : 5000)).start();
    }

    public void play(int sound) {
        if (prefs.getBoolean("sounds", true)) {
            int priority = 1;
            if (sound == 0) {
                sound = getRnd();
                priority = 0;
            }
            mathSounds.playSomeMusic(sound, priority);
        }
    }


    public void goSound(View v) {
        if (v == findViewById(R.id.sound_on_off)) {
            startAnimation(v, 1);
        }
        prefsEditor
                .putBoolean("sounds", !prefs.getBoolean("sounds", true))
                .apply();
        soundState();
    }


    public void soundState() {
        TextView soundBtn = findViewById(R.id.sound_btn);
        TextView sound_on_off = findViewById(R.id.sound_on_off);
        if (prefs.getBoolean("sounds", true)) {
            if (sound_on_off != null) {
                sound_on_off.setText(getString(R.string.sound_on));
            }
            if (soundBtn != null) {
                soundBtn.setText(getString(R.string.sound_on));
            }
        } else {
            if (sound_on_off != null) {
                sound_on_off.setText(getString(R.string.sound_off));
            }
            if (soundBtn != null) {
                soundBtn.setText(getString(R.string.sound_off));
            }
        }
    }

    public void showFullscreenAd(final FullscreenCallback fullscreenCallback) {
        if (fullscreenAd != null) {
            if (fireTimer != null) {
                fireTimer.cancel();
            }

            fullscreenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdClicked() {
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    Log.d("InterstitialAdError", "Ad dismissed fullscreen content.");
                    fullscreenAd = null;
                    fullscreenCallback.afterFullscreenAd();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    Log.e("InterstitialAdError", "Ad failed to show fullscreen content.");
                    fullscreenAd = null;
                    fullscreenCallback.afterFullscreenAd();
                }

                @Override
                public void onAdImpression() {
                    adIsShowing = false;
                    goMain = false;
                    fullscreenCallback.afterFullscreenAd();
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    Log.d("InterstitialAdError", "Ad showed fullscreen content.");
                }
            });

            fullscreenAd.show(this);

            fullscreenIsShowed = true;
            adIsShowing = true;
        } else {
            fullscreenCallback.afterFullscreenAd();
        }
    }

    public void turnTheScreenOff() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void highScoresList() {
        final ConstraintLayout exp = findViewById(R.id.lboards_exp_list_wrapper);
        final ArrayList<Level> levelList = new ArrayList<>();
        levelList.add(new Level(Level.FAST_CALC,
                getString(R.string.fast_calc),
                Arrays.asList(getResources().getStringArray(R.array.fast_calc_levels_description))));
        levelList.add(new Level(Level.EASY_CALC,
                getString(R.string.easy_calc),
                Arrays.asList(getResources().getStringArray(R.array.easy_calc_levels_description))));
        levelList.add(new Level(Level.HEAVY_CALC,
                getString(R.string.heavy_calc),
                Arrays.asList(getResources().getStringArray(R.array.heavy_calc_levels_description))));
        levelList.add(new Level(getString(R.string.cancel), getString(R.string.cancel), null));
        ExpandableListView expListView = (ExpandableListView) findViewById(R.id.lboards_exp_list);
        exp.setVisibility(View.VISIBLE);
        ExpandableListAdapter elAdapter = new HighScoresAdapter(this, levelList);
        expListView.setAdapter(elAdapter);
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                if (groupPosition + 1 == levelList.size()) {
                    exp.setVisibility(View.GONE);
                }
                return false;
            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                showHighScoresList(levelList.get(groupPosition).getGameName(),
                        levelList.get(groupPosition).getLevelNameItem(childPosition),
                        levelList.get(groupPosition).getScreenLevelNameItem(childPosition));
                return false;
            }
        });
    }

    public void showHighScoresList(String gameName, String levelName, String title) {
        (findViewById(R.id.highscore_table)).setVisibility(View.VISIBLE);
        (findViewById(R.id.one_btn)).setVisibility(View.VISIBLE);
        ((TextView) (findViewById(R.id.hs_name))).setText(title);
        switch (gameName) {
            case Level.FAST_CALC:
                new ScoreRepository(getApplicationContext()).getBestTimesList(levelName, scoreCallback);
                break;
            case Level.EASY_CALC:
            case Level.HEAVY_CALC:
                new ScoreRepository(getApplicationContext()).getBestPointsList(levelName, scoreCallback);
                break;
        }
    }

    ScoreListCallback scoreCallback = new ScoreListCallback() {
        @Override
        public void scoreSaved(List<Score> scoreList, String levelName, long lastScoreId) {
        }

        @Override
        public void scoreList(final List<Score> scoreList) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setHighScoreTableAdapter(scoreList, -1L);
                }
            });
        }
    };

    public void setHighScoreTableAdapter(List<Score> scoreList, long lastScoreId) {
        goHiScore = true;
        RecyclerView hsrv = findViewById(R.id.hs_rec_view);
        HighScoresTableAdapter hstAdapter = new HighScoresTableAdapter(scoreList, lastScoreId);
        RecyclerView.LayoutManager hslm = new LinearLayoutManager(this);
        hsrv.setLayoutManager(hslm);
        hsrv.setAdapter(hstAdapter);
        int s = 0;
        for (int i = 0; i < scoreList.size(); i++) {
            if ((int) lastScoreId == scoreList.get(i).getId()) {
                s = i;
                break;
            }
        }
        ((LinearLayoutManager) hslm).scrollToPositionWithOffset(s, 0);
    }
}