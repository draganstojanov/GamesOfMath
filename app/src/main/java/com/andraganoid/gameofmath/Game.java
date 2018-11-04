package com.andraganoid.gameofmath;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andraganoid.gameofmath.Fast.FastSettings;
import com.andraganoid.gameofmath.Easy.EasySettings;
import com.andraganoid.gameofmath.Heavy.HeavySettings;
import com.andraganoid.gameofmath.Misc.About;
import com.andraganoid.gameofmath.Operation.Task;
import com.andraganoid.gameofmath.Practice.PracticeSettings;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.plattysoft.leonids.ParticleSystem;

import java.util.Random;


public class Game extends AppCompatActivity implements RewardedVideoAdListener {
    // public static Back back = new Back();
    // public static Bitmap background;
    public static Calc calc;
    public static Task task;
    public static SharedPreferences prefs;
    public static SharedPreferences.Editor prefsEditor;
    Intent intent;
    public InterstitialAd fullscreenAd;
    private RewardedVideoAd rewardAd;
    public static boolean adIsShowing;

    // android.support.constraint.ConstraintLayout cl;
    // private AdView adViewBottomGame;

    public CountDownTimer cdt, intro;
    public Intent boardIntent;
    public boolean goMain, isEnd;
    DisplayMetrics metrics;
    int shot, last, width, height;
    Random rand;
    ParticleSystem ps;
    TextView sound_tv;


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

    private SoundPool soundPool;
    private int[] sounds;
    public static final int FIREWORK = 0;
    public static final int BEST_RESULT = 3;
    public static final int TIME_IS_OUT = 4;
    public static final int GET_BONUS = 5;
    public static final int USE_BONUS = 6;
    public static final int RIGHT_ANSWER = 7;
    public static final int WRONG_ANSWER = 8;


    @Override
    protected void onResume() {
        super.onResume();
        MathBase mb = new MathBase(getApplicationContext());
        adIsShowing = false;
        if (!fullscreenAd.isLoaded()) {
            fullscreenAd.loadAd(new AdRequest.Builder().build());
        }
        if (!rewardAd.isLoaded()) {
            rewardAd.loadAd(getString(R.string.AD_MOB_MATH_REWARD),new AdRequest.Builder().build());
        }


        // cl.setBackground(new BitmapDrawable(getResources(), back.getBack()));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        MobileAds.initialize(this, getString(R.string.AD_MOB_APP_ID));
        AdView adViewBottomGame = findViewById(R.id.add_view_bottom_game);
        adViewBottomGame.loadAd(new AdRequest.Builder().build());

        fullscreenAd = new InterstitialAd(this);
        fullscreenAd.setAdUnitId(getString(R.string.AD_MOB_MATH_FULLSCREEN));
        fullscreenAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                adIsShowing = false;
                fullscreenAd.loadAd(new AdRequest.Builder().build());
                goMain = false;
                finish();

            }
        });

        rewardAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardAd.setRewardedVideoAdListener(this);
        if (!rewardAd.isLoaded()) {
            rewardAd.loadAd(getString(R.string.AD_MOB_MATH_REWARD),new AdRequest.Builder().build());
        }




        prefs = this.getPreferences(Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        rand = new Random();
        sound_tv = findViewById(R.id.sound_on_off);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool(15, AudioManager.STREAM_MUSIC, 0);
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
        sounds = new int[]{
                soundPool.load(this, R.raw.firework1, 1),
                soundPool.load(this, R.raw.firework2, 1),
                soundPool.load(this, R.raw.firework3, 1),
                soundPool.load(this, R.raw.new_best, 1),
                soundPool.load(this, R.raw.beep_timer, 1),
                soundPool.load(this, R.raw.get_bonus, 1),
                soundPool.load(this, R.raw.use_bonus, 1),
                soundPool.load(this, R.raw.right_answer, 1),
                soundPool.load(this, R.raw.wrong_answer, 1)};


        //  cl = (android.support.constraint.ConstraintLayout) findViewById(R.id.game_lay);
        //  cl.setBackground(new BitmapDrawable(getResources(), back.getBack()));

    }


    public void goPractice(View v) {
        // background = back.getBack();
        intent = new Intent(this, PracticeSettings.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goArcade(View v) {

        //  background = back.getBack();
        intent = new Intent(this, FastSettings.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goEasy(View v) {
        // background = back.getBack();
        intent = new Intent(this, EasySettings.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goHeavy(View v) {
        //  background = back.getBack();
        intent = new Intent(this, HeavySettings.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public void goFire() {

        play(BEST_RESULT);

        shot = 25;
        last = 0;
        new CountDownTimer(10000, 200) {
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
        }.start();

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
            if (sound == 0) {
                sound = getRnd();
            }
            soundPool.play(sounds[sound], 1, 1, 1, 0, 1f);
        }
    }


    public void goSound(View v) {
        boolean s = prefs.getBoolean("sounds", true);
        startAnimation(findViewById(R.id.sound_on_off), 1);

        if (!s) {
            sound_tv.setText("ON");
        } else {
            sound_tv.setText("OFF");
        }

        prefsEditor
                .putBoolean("sounds", !s)
                .apply();
    }


    public void goAbout(View v) {
        intent = new Intent(this, About.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goSettings(View v) {
        findViewById(R.id.game_settings).setVisibility(View.VISIBLE);
    }

    public void goSettingsOk(View v) {
        findViewById(R.id.game_settings).setVisibility(View.GONE);
    }


    public void showFullscreenAd() {
        // Toast.makeText(this, "showfulscreen", Toast.LENGTH_SHORT).show();
        if (fullscreenAd != null && fullscreenAd.isLoaded()) {
            adIsShowing = true;
            // Toast.makeText(this, "SHOWING", Toast.LENGTH_SHORT).show();
            fullscreenAd.show();
        } else {
            goMain = false;
            finish();
        }

    }

    @Override
    public void onRewardedVideoAdLoaded() {

        Toast.makeText(this, "onRewarded! currency: " + reward.getType() + "  amount: " +
                reward.getAmount(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    ;

}
