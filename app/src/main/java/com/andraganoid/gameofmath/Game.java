package com.andraganoid.gameofmath;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andraganoid.gameofmath.Easy.EasySettings;
import com.andraganoid.gameofmath.Fast.FastSettings;
import com.andraganoid.gameofmath.Heavy.HeavySettings;
import com.andraganoid.gameofmath.Misc.About;
import com.andraganoid.gameofmath.Misc.MathBase;
import com.andraganoid.gameofmath.Misc.MathSounds;
import com.andraganoid.gameofmath.Operation.Calc;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.plattysoft.leonids.ParticleSystem;

import java.util.Random;

import static com.andraganoid.gameofmath.Misc.MathSounds.BEST_RESULT;
import static com.andraganoid.gameofmath.Misc.MathSounds.FIREWORK;
import static com.andraganoid.gameofmath.Misc.MathSounds.REWARD;


public class Game extends AppCompatActivity implements RewardedVideoAdListener {
    // public static Back back = new Back();
    // public static Bitmap background;
    public static Calc calc;
    public static Task task;

    private GoogleSignInClient mGoogleSignInClient;
    private LeaderboardsClient mLeaderboardsClient;
    Intent intent;
    public InterstitialAd fullscreenAd;
    private RewardedVideoAd rewardAd;
    public AdView bottomAd;
    public static boolean adIsShowing;
    private TextView getBonusClick;
    private boolean goSettings;
    private boolean getReward;

    public SharedPreferences prefs;
    public SharedPreferences.Editor prefsEditor;


    // android.support.constraint.ConstraintLayout cl;
    // private AdView adViewBottomGame;

    public CountDownTimer cdt, intro, fireTimer;
    public Intent boardIntent;
    public boolean goMain, isEnd;
    DisplayMetrics metrics;
    int shot, last, width, height;
    Random rand;
    ParticleSystem ps;
    public MathSounds mathSounds;


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

    private String rc;
    private ConstraintLayout rl;


    @Override
    protected void onPause() {
        super.onPause();
        rewardAd.pause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rewardAd.destroy(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        MathBase mb = new MathBase(getApplicationContext());
        mathSounds = MathSounds.getInstance(getApplicationContext());
        adIsShowing = false;
        goSettings = false;
        getReward = false;

        rewardAd.resume(this);
        if (rewardAd.isLoaded()) {
            getBonusClick.setBackgroundColor(getResources().getColor(R.color.info));
            getBonusClick.setTextColor(getResources().getColor(R.color.checked));
        } else {
            loadRewardAd();
        }
        bottomAd.loadAd(new AdRequest.Builder().build());
        loadFullscreenAd();

        // if (prefs.getBoolean("leaderboardsPermission", false)) {
        //    if(!isSignedIn())  {signInSilently();}
        //  }


        // cl.setBackground(new BitmapDrawable(getResources(), back.getBack()));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefsEditor = prefs.edit();
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        rand = new Random();
        getBonusClick = findViewById(R.id.get_bonus_btn);
        bottomAd = findViewById(R.id.add_view_bottom_game);
        rl = findViewById(R.id.reward_dialog);

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
        rewardAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardAd.setRewardedVideoAdListener(this);

        //  cl = (android.support.constraint.ConstraintLayout) findViewById(R.id.game_lay);
        //  cl.setBackground(new BitmapDrawable(getResources(), back.getBack()));


        mGoogleSignInClient = GoogleSignIn.getClient(this,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build());


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


    public void goSetLeaderboardPermission(View v) {

        if (!prefs.getBoolean("leaderboardsPermission", false)) {

            AlertDialog lad = new AlertDialog.Builder(Game.this).create();
            lad.setMessage(getString(R.string.lboard_connect));
            lad.setButton(Dialog.BUTTON_POSITIVE, getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    startAnimation(findViewById(R.id.lboard_on_off), 1);
                    prefsEditor
                            .putBoolean("leaderboardsPermission", true)
                            .apply();
                    slp();


                }
            });
            lad.setButton(Dialog.BUTTON_NEGATIVE, getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    slp();

                }
            });
            lad.setCancelable(false);
            lad.show();


            Typeface typeface = ResourcesCompat.getFont(this, R.font.luckiestguy);


            ((TextView) lad.getWindow().findViewById(android.R.id.message)).setTypeface(typeface);
            ((Button) lad.getWindow().findViewById(android.R.id.button1)).setTypeface(typeface);
            ((Button) lad.getWindow().findViewById(android.R.id.button2)).setTypeface(typeface);


        } else {
            startAnimation(findViewById(R.id.lboard_on_off), 1);
            prefsEditor
                    .putBoolean("leaderboardsPermission", false)
                    .apply();
            slp();
        }

    }


    public void slp() {

        if (prefs.getBoolean("leaderboardsPermission", false)) {
            ((TextView) findViewById(R.id.lboard_on_off)).setText(getString(R.string.on));

        } else {
            ((TextView) findViewById(R.id.lboard_on_off)).setText(getString(R.string.off));
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

        } else {
            ((TextView) findViewById(R.id.sound_on_off)).setText(getString(R.string.off));
        }
    }

    @Override
    public void onBackPressed() {
        if (goSettings) {
            goSettings = false;
        } else {
            super.onBackPressed();
        }
    }

    public void goAbout(View v) {

        intent = new Intent(this, About.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goSettings(View v) {

        findViewById(R.id.game_settings).setVisibility(View.VISIBLE);
        goSettings = true;
        soundState();
        slp();
    }

    public void goSettingsOk(View v) {
        findViewById(R.id.game_settings).setVisibility(View.GONE);
    }


    public void goGetBonus(View v) {
        if (rewardAd != null && rewardAd.isLoaded()) {
            showRewardAd();
        }
    }

    public void goLogoEffect(View v) {
        startAnimation(findViewById(R.id.logo), 1);

    }


    public void loadFullscreenAd() {
        if (!fullscreenAd.isLoaded()) {
            fullscreenAd.loadAd(new AdRequest.Builder().build());
        }
    }

    public void showFullscreenAd() {
        if (fullscreenAd != null && fullscreenAd.isLoaded()) {
            if (fireTimer != null) {
                fireTimer.cancel();
            }
            adIsShowing = true;
            fullscreenAd.show();
        } else {
            goMain = false;
            finish();
        }

    }


    private void loadRewardAd() {

        if (rewardAd != null && !rewardAd.isLoaded()) {
            if (fireTimer != null) {
                fireTimer.cancel();
            }
            //    getBonusClick.setBackground(getResources().getDrawable(R.drawable.bar_stroke));
            //    getBonusClick.setTextColor(getResources().getColor(R.color.base));
            rewardAd.loadAd(getString(R.string.AD_MOB_MATH_REWARD), new AdRequest.Builder().build());
        }
    }

    private void showRewardAd() {

        if (rewardAd != null && rewardAd.isLoaded()) {
            rewardAd.show();
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        getBonusClick.setBackgroundColor(getResources().getColor(R.color.info));
        getBonusClick.setTextColor(getResources().getColor(R.color.checked));
    }

    @Override
    public void onRewardedVideoAdOpened() {
    }

    @Override
    public void onRewardedVideoStarted() {
    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardAd();
        if (getReward) {
            getReward = false;
            play(REWARD);
            openRewardDialog();

        }


    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

        //  Toast.makeText(this, "onRewarded! currency: " + rewardItem.getType() + "  amount: " +
        //         rewardItem.getAmount(), Toast.LENGTH_LONG).show();

        //NACRTAJ NAGRADU


        getReward = true;


        // Reward the user.
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

    public void openRewardDialog() {
        rc = "";
        (findViewById(R.id.reward_dialog)).setVisibility(View.VISIBLE);


    }

    public void chooseReward(View v) {

        switch (v.getId()) {
            case R.id.reward_e_skip:
                rc = Calc.EASY_SKIPS;
                break;
            case R.id.reward_e_xtra:
                rc = Calc.EASY_XTRA_TIME;
                break;
            case R.id.reward_e_reset:
                rc = Calc.EASY_RESETS;
                break;
            case R.id.reward_h_hint:
                rc = Calc.HEAVY_HINTS;
                break;
            case R.id.reward_h_xtra:
                rc = Calc.HEAVY_XTRA_TIME;
                break;
            case R.id.reward_h_live:
                rc = Calc.HEAVY_XTRA_LIVES;
                break;
            case R.id.reward_submit:
                (findViewById(R.id.reward_dialog)).setVisibility(View.GONE);
                new Calc().addRewards(rc);
                break;
        }

        for (int i = 1; i < rl.getChildCount() - 1; i++) {
            rl.getChildAt(i).setBackground(getResources().getDrawable(R.drawable.tv_stroke));
        }
        if (v.getId() != R.id.reward_submit) {
            v.setBackgroundColor(getResources().getColor(R.color.checked));
        }


    }


    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx


    private void signInSilently() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        signInClient.silentSignIn().addOnCompleteListener(this,
                new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<GoogleSignInAccount> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Game.this, "SILENT OK", Toast.LENGTH_SHORT).show();
                            // The signed in account is stored in the task's result.
                            GoogleSignInAccount signedInAccount = task.getResult();
                        } else {
                            startSignInIntent();
                            // Player will need to sign-in explicitly using via UI
                        }
                    }
                });
    }


    private void startSignInIntent() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent, 9001);


    }

    private boolean isSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9001) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {

                Toast.makeText(Game.this, "SIGNED IN", Toast.LENGTH_SHORT).show();
                // The signed in account is stored in the result.
                GoogleSignInAccount signedInAccount = result.getSignInAccount();
            } else {
                String message = result.getStatus().getStatusMessage();
                if (message == null || message.isEmpty()) {
                    message = getString(R.string.signin_other_error);
                }
                new AlertDialog.Builder(this).setMessage(message)
                        .setNeutralButton(android.R.string.ok, null).show();
            }
        }
    }


    private void signOut() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        signInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {

                    }

                });
    }


    public void submitScore() {
//        Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
////////                .submitScore(getString(R.string.leaderboard_id), 1337);

        Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .submitScore("CgkIo5LlzL0eEAIQEA", 1313);
    }


    private void showLeaderboard() {
        Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
//                .getLeaderboardIntent(getString(R.string.leaderboard_id))
                .getLeaderboardIntent("CgkIo5LlzL0eEAIQEA")
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, 9004);
                    }
                });
    }

//        if(isSignedIn()){
//            Toast.makeText(this, "SIGNED IN", Toast.LENGTH_SHORT).show();
//
//        }else{ signInSilently();}


}
