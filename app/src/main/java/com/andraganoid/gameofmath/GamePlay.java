package com.andraganoid.gameofmath;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andraganoid.gameofmath.Leaderboards.Level;
import com.andraganoid.gameofmath.Misc.MathBase;
import com.andraganoid.gameofmath.Misc.MathSounds;
import com.andraganoid.gameofmath.Operation.Calc;
import com.andraganoid.gameofmath.Operation.Task;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.leaderboard.ScoreSubmissionData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.plattysoft.leonids.ParticleSystem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.andraganoid.gameofmath.Misc.MathSounds.BEST_RESULT;
import static com.andraganoid.gameofmath.Misc.MathSounds.FIREWORK;

public class GamePlay extends AppCompatActivity {

    public static Calc calc;
    public static Task task;

    public GoogleSignInClient mGoogleSignInClient;
    public LeaderboardsClient mLeaderboardsClient;


    public InterstitialAd fullscreenAd;
    public static boolean adIsShowing, fullscreenIsShowed;
    public SharedPreferences prefs;
    ;

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

        Toast.makeText(this, "RESUME GAME PLAY", Toast.LENGTH_SHORT).show();
        MathBase mb = new MathBase(getApplicationContext());

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


        mGoogleSignInClient = GoogleSignIn.getClient(this,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build());


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

        } else {
            ((TextView) findViewById(R.id.sound_on_off)).setText(getString(R.string.off));
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
        } else {
            goMain = false;
            finish();
        }

    }


    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    public void signInSilentlyLeaderboard() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        signInClient.silentSignIn().addOnCompleteListener(this,
                new OnCompleteListener <GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task <GoogleSignInAccount> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(GamePlay.this, "SILENT OK", Toast.LENGTH_SHORT).show();
                            // The signed in account is stored in the task's result.
                            GoogleSignInAccount signedInAccount = task.getResult();
                        } else {
                            startSignInIntentLeaderboard();
                            // Player will need to sign-in explicitly using via UI
                        }
                    }
                });
    }


    public void startSignInIntentLeaderboard() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent, 9013);


    }

    public boolean isSignedInLeaderboard() {
        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9013) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {

                Toast.makeText(GamePlay.this, "SIGNED IN", Toast.LENGTH_SHORT).show();
                GoogleSignInAccount signedInAccount = result.getSignInAccount();
                (findViewById(R.id.first_logo)).setVisibility(View.GONE);
            } else {
                String message = result.getStatus().getStatusMessage();
                if (message == null || message.isEmpty()) {
                    message = getString(R.string.signin_other_error);

                    prefsEditor.putBoolean("leaderboardsPermission", false).apply();
                    //  slp(false);

                }
                (findViewById(R.id.first_logo)).setVisibility(View.GONE);


                AlertDialog ad = new AlertDialog.Builder(GamePlay.this).create();
                ad.setMessage(message);
                ad.setCancelable(true);
                ad.show();


                ((TextView) ad.getWindow().findViewById(android.R.id.message)).setTypeface(alertTypeface);

//                new AlertDialog.Builder(this).setMessage(message)
//                        .setCancelable(true).show();
//

            }
        }
    }


    public void signOutLeaderboard() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        signInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener <Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task <Void> task) {
                        ((TextView) findViewById(R.id.lboard_on_off)).setText(getString(R.string.off));
                    }

                });
    }


    public void submitScoreLeaderboard(String boardID, long result) {

        // Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this)).submitScoreLeaderboard(getString(R.string.leaderboard_id), 1337);
        // Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this)).submitScore(getString(R.string.leaderboard_id), 1337);
        //  int e = (rand.nextInt(1000));

//        if (isSignedInLeaderboard()) {xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .submitScoreImmediate(boardID, result)
                .addOnSuccessListener(new OnSuccessListener <ScoreSubmissionData>() {
                    @Override
                    public void onSuccess(ScoreSubmissionData scoreSubmissionData) {

                        Log.d("SUBMIT SUCCESS", String.valueOf(scoreSubmissionData.toString()));


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.d("SUBMIT FAILED", String.valueOf(e.toString()));

            }
        });

        // }
        //   .submitScore("CgkItryh-O0OEAIQFQ",e );callback
    }


    public void showLeaderboard(String boardName) {

        if (isSignedInLeaderboard()) {//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            int boardId = getBoardId("leaderboard_" + boardName);
            Log.d("exp", getString(boardId));

            if (GoogleSignIn.getLastSignedInAccount(this) != null) {//TRY/CATCH
                Games.getLeaderboardsClient(this,
                        GoogleSignIn.getLastSignedInAccount(this))
                        .getLeaderboardIntent(getString(boardId))
                        .addOnSuccessListener(new OnSuccessListener <Intent>() {
                            @Override
                            public void onSuccess(Intent intent) {
                                startActivityForResult(intent, 9004);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(GamePlay.this, "FAILED" + String.valueOf(e), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {


            //connectAlert();

          final  AlertDialog ad = new AlertDialog.Builder(GamePlay.this).create();
            ad.setMessage(String.format("%s!", getString(R.string.lboard_connect)));

            ad.setButton(Dialog.BUTTON_POSITIVE, getString(R.string.connect), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ad.cancel();
                    signInSilentlyLeaderboard();

                }
            });

            ad.setButton(Dialog.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

//cancel
                    ad.cancel();
                }
            });


            ad.setCancelable(true);
            ad.show();

            ((TextView) ad.getWindow().findViewById(android.R.id.message)).setTypeface(alertTypeface);



        }
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    public void turnTheScreenOff() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }


    public void goLeaderboards(View v) {//koja tabela? ONCLICK POSLE ZAVRSENE IGRE


    }

    public void showLeaderboardsList(View v) {

        final ConstraintLayout exp = findViewById(R.id.lboards_exp_list_wrapper);

     //   if (isSignedInLeaderboard()) {//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            //    if (true) {

            final ArrayList <Level> levelList = new ArrayList <Level>();


            levelList.add(new Level(getString(R.string.fast_calc),
                    Arrays.asList(getResources().getStringArray(R.array.fast_calc_levels)),
                    Arrays.asList(getResources().getStringArray(R.array.fast_calc_levels_description))));

            levelList.add(new Level(getString(R.string.easy_calc),
                    Arrays.asList(getResources().getStringArray(R.array.easy_calc_levels)),
                    Arrays.asList(getResources().getStringArray(R.array.easy_calc_levels_description))));

            levelList.add(new Level(getString(R.string.heavy_calc),
                    Arrays.asList(getResources().getStringArray(R.array.heavy_calc_levels)),
                    Arrays.asList(getResources().getStringArray(R.array.heavy_calc_levels_description))));

            levelList.add(new Level(getString(R.string.cancel), null, null));

            ExpandableListView expListView = (ExpandableListView) findViewById(R.id.lboards_exp_list);


            exp.setVisibility(View.VISIBLE);
            ExpandableListAdapter elAdapter = new com.andraganoid.gameofmath.LeaderboardAdapter(this, levelList);

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

                    Toast.makeText(GamePlay.this, levelList.get(groupPosition).getLevel(childPosition), Toast.LENGTH_SHORT).show();

                    showLeaderboard(levelList.get(groupPosition).getLevel(childPosition));
                    return false;
                }
            });

      //  } else {

            //  exp.setVisibility(View.GONE);
      //      connectAlert();
      //  }
    }


    private void connectAlert() {
//        AlertDialog ad = new AlertDialog.Builder(GamePlay.this).create();
//        ad.setMessage(String.format("%s!", getString(R.string.lboard_connect)));
//        ad.setCancelable(true);
//        ad.show();
//
//        ((TextView) ad.getWindow().findViewById(android.R.id.message)).setTypeface(alertTypeface);


//            new AlertDialog.Builder(this).setMessage(getString(R.string.lboard_connect) + "!")
//                    .setCancelable(true).show();

    }


    public static int getBoardId(String resName) {

        try {
            Field idField = R.string.class.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


}

