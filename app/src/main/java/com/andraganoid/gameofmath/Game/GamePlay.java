package com.andraganoid.gameofmath.Game;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andraganoid.gameofmath.DataBase.Score;
import com.andraganoid.gameofmath.DataBase.ScoreListCallback;
import com.andraganoid.gameofmath.DataBase.ScoreRepository;
import com.andraganoid.gameofmath.HighScores.HighScoresAdapter;
import com.andraganoid.gameofmath.HighScores.HighScoresTableAdapter;
import com.andraganoid.gameofmath.HighScores.Level;
import com.andraganoid.gameofmath.Misc.MathSounds;
import com.andraganoid.gameofmath.Operation.Calc;
import com.andraganoid.gameofmath.Operation.Task;
import com.andraganoid.gameofmath.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.plattysoft.leonids.ParticleSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.andraganoid.gameofmath.Misc.MathSounds.BEST_RESULT;
import static com.andraganoid.gameofmath.Misc.MathSounds.FIREWORK;

public class GamePlay extends AppCompatActivity {

    public static Calc calc;
    public static Task task;

//    public GoogleSignInClient mGoogleSignInClient;
//    public LeaderboardsClient mLeaderboardsClient;


    public InterstitialAd fullscreenAd;
    public static boolean adIsShowing, fullscreenIsShowed;
    public SharedPreferences prefs;
    public SharedPreferences.Editor prefsEditor;
    public Typeface alertTypeface;
    public View board;
    public CountDownTimer cdt, intro, fireTimer;
    public Intent boardIntent;
    public boolean goMain, isEnd;
    DisplayMetrics metrics;
    int shot, last, width, height;
    Random rand;
    ParticleSystem ps;
    public MathSounds mathSounds;
    public boolean goHiScore;


    int fireDots[] = new int[]{
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
    int aCount;
    public boolean colorChange;


    @Override
    protected void onResume() {
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        adIsShowing = false;
        loadFullscreenAd();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.game_play);
        alertTypeface = ResourcesCompat.getFont(this, R.font.luckiestguy);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefsEditor = prefs.edit();
        mathSounds = MathSounds.getInstance(getApplicationContext());
        rand = new Random();

        fullscreenIsShowed = false;
        if (fullscreenAd == null) {
            fullscreenAd = new InterstitialAd(this);
        }
        fullscreenAd.setAdUnitId(getString(R.string.AD_MOB_MATH_FULLSCREEN));
        fullscreenAd.setAdListener(new AdListener() {


            @Override
            public void onAdClosed() {
                super.onAdClosed();
                adIsShowing = false;
                loadFullscreenAd();
                goMain = false;
                //   finish();

            }
        });


//        mGoogleSignInClient = GoogleSignIn.getClient(this,
//                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build());


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
                if (aCount < repeat) {//1-6
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
        startAnimation(findViewById(R.id.sound_on_off), 1);
        prefsEditor
                .putBoolean("sounds", !prefs.getBoolean("sounds", true))
                .apply();
        soundState();
    }

    public void soundState() {
        if (prefs.getBoolean("sounds", true)) {
            ((TextView) findViewById(R.id.sound_on_off)).setText(getString(R.string.on));
            ((TextView) findViewById(R.id.sound_btn)).setText(getString(R.string.sound_on));

        } else {
            ((TextView) findViewById(R.id.sound_on_off)).setText(getString(R.string.off));
            ((TextView) findViewById(R.id.sound_btn)).setText(getString(R.string.sound_off));
        }
    }


    public void loadFullscreenAd() {
        if (!fullscreenAd.isLoaded()) {
            fullscreenAd.loadAd(new AdRequest.Builder().build());
        }
    }

    public void showFullscreenAd() {
        if (fullscreenAd != null && fullscreenAd.isLoaded()) {
//            if (fireTimer != null) {
//                fireTimer.cancel();
//            }
            fullscreenIsShowed = true;
            adIsShowing = true;
            fullscreenAd.show();
        }
//        else {
//            goMain = false;
//            finish();
//        }

    }


    public void turnTheScreenOff() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }


    public void highScoresList() {

        final ConstraintLayout exp = findViewById(R.id.lboards_exp_list_wrapper);

        final ArrayList <Level> levelList = new ArrayList <Level>();

        levelList.add(new Level(Level.FAST_CALC,
                getString(R.string.fast_calc),
                Arrays.asList(getResources().getStringArray(R.array.fast_calc_levels_description))));

        levelList.add(new Level(Level.EASY_CALC,
                getString(R.string.easy_calc),
                Arrays.asList(getResources().getStringArray(R.array.easy_calc_levels_description))));

        levelList.add(new Level(Level.HEAVY_CALC,
                getString(R.string.heavy_calc),
                Arrays.asList(getResources().getStringArray(R.array.heavy_calc_levels_description))));
//
//        levelList.add(new Level(Level.FAST_CALC,
//                Arrays.asList(getResources().getStringArray(R.array.fast_calc_levels)),
//                Arrays.asList(getResources().getStringArray(R.array.fast_calc_levels_description))));
//
//        levelList.add(new Level(Level.EASY_CALC,
//                Arrays.asList(getResources().getStringArray(R.array.easy_calc_levels)),
//                Arrays.asList(getResources().getStringArray(R.array.easy_calc_levels_description))));
//
//        levelList.add(new Level(Level.HEAVY_CALC,
//                Arrays.asList(getResources().getStringArray(R.array.heavy_calc_levels)),
//                Arrays.asList(getResources().getStringArray(R.array.heavy_calc_levels_description))));

        levelList.add(new Level(getString(R.string.cancel), getString(R.string.cancel), null));

//        for (Level level : levelList) {
//            System.out.println("LEVEL LIST" + level.getGameName());
//            System.out.println("LEVEL LIST" + level.getScreenGameName());
//            System.out.println("LEVEL LIST" + level.getLevelName().size());
//            System.out.println("LEVEL LIST" + level.getScreenLevelName().size());
//            System.out.println("LEVEL LIST" + level.getLevelDesc().size());
//
//        }


        ExpandableListView expListView = (ExpandableListView) findViewById(R.id.lboards_exp_list);


        exp.setVisibility(View.VISIBLE);
        ExpandableListAdapter elAdapter = new HighScoresAdapter(this, levelList);

        // setting list adapter
        expListView.setAdapter(elAdapter);

        // Listview Group click listener
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

        // Listview Group expanded listener
//        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) { Toast.makeText(GamePlay.this, levelList.get(groupPosition).getGameName(), Toast.LENGTH_SHORT).show();
////                Toast.makeText(getApplicationContext(),
////                        listDataHeader.get(groupPosition) + " Expanded",
////                        Toast.LENGTH_SHORT).show();
//            }
//        });

        // Listview Group collasped listener
//        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) { Toast.makeText(GamePlay.this, levelList.get(groupPosition).getGameName(), Toast.LENGTH_SHORT).show();
////                Toast.makeText(getApplicationContext(),
////                        listDataHeader.get(groupPosition) + " Collapsed",
////                        Toast.LENGTH_SHORT).show();
//
//            }
//        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                showHighScoresList(levelList.get(groupPosition).getGameName(),
                        levelList.get(groupPosition).getLevelNameItem(childPosition),
                        levelList.get(groupPosition).getScreenLevelNameItem(childPosition));

                return false;
            }
        });


    }

    public void showHighScoresList(String gameName, String levelName, String title) {

        Toast.makeText(getApplicationContext(), levelName, Toast.LENGTH_LONG).show();
        System.out.println("showLeaderboard: " + gameName + levelName);

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
        public void scoreSaved(List <Score> scoreList, String levelName) {

        }

        @Override
        public void scoreList(final List <Score> scoreList) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setHighScoreTableAdapter(scoreList);
                }
            });
        }
    };

    public void setHighScoreTableAdapter(List <Score> scoreList) {
        goHiScore = true;
        RecyclerView hsrv = new RecyclerView(this);
        hsrv = findViewById(R.id.hs_rec_view);
        HighScoresTableAdapter hstAdapter = new HighScoresTableAdapter(scoreList);
        RecyclerView.LayoutManager hslm = new LinearLayoutManager(this);
        hsrv.setLayoutManager(hslm);
        hsrv.setAdapter(hstAdapter);

    }

}