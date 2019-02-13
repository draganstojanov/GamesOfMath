package com.andraganoid.gameofmath.MathLeaderboards;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andraganoid.gameofmath.Game.Game;
import com.andraganoid.gameofmath.R;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;


public abstract class MathLeaderboards extends AppCompatActivity {


  //  public GoogleSignInClient mGoogleSignInClient;
 //   public LeaderboardsClient mLeaderboardsClient;

    public Typeface alertTypeface;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;

    public static final int REQUEST_CODE_MAIN = 9001;
    public static final int REQUEST_CODE_SETTINGS = 9002;
    public static final int REQUEST_CODE_SHOW = 9003;
    public static final int REQUEST_CODE_GAME = 9004;
    public static final int REQUEST_CODE_LIST = 9005;

    @Override
    protected void onResume() {
        super.onResume();

       // mGoogleSignInClient = GoogleSignIn.getClient(this,
             //   new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build());

        alertTypeface = ResourcesCompat.getFont(this, R.font.luckiestguy);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefsEditor = prefs.edit();

    }

    public boolean isSignedInLeaderboard() {
        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }

    public void signInSilentlyLeaderboard(final int requestCode) {

        findViewById(R.id.full_screen_transparent).setVisibility(View.VISIBLE);
        prefsEditor.putBoolean("leaderboardsPermissionGranted", true).apply();
        Toast.makeText(this, getString(R.string.connecting), Toast.LENGTH_LONG).show();
        GoogleSignInClient signInClient = GoogleSignIn.getClient(getApplication(), GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        System.out.println("signInClient"+signInClient);
        signInClient.silentSignIn().addOnCompleteListener(this,
                new OnCompleteListener <GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task <GoogleSignInAccount> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "SILENT OK", Toast.LENGTH_SHORT).show();
                            // The signed in account is stored in the task's result.
                            GoogleSignInAccount signedInAccount = task.getResult();
                            onConnection(getString(R.string.succes_conn_lb), requestCode);
                        } else {
                            startSignInIntentLeaderboard(requestCode);
                            // Player will need to sign-in explicitly using via UI
                        }
                    }
                });
    }


    public void startSignInIntentLeaderboard(int requestCode) {

//        findViewById(R.id.full_screen_transparent).setVisibility(View.VISIBLE);
//        prefsEditor.putBoolean("leaderboardsPermissionGranted", true).apply();
        // Toast.makeText(this, getString(R.string.connecting), Toast.LENGTH_LONG).show();
        Toast.makeText(this, "ONACTiVITY00", Toast.LENGTH_SHORT).show();
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent, requestCode);
        Toast.makeText(this, "ONACTiVITY000", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("RequestCODE: "+requestCode);
        System.out.println("ResultCODES: "+resultCode);
        Toast.makeText(this, "ONACTiVITY1", Toast.LENGTH_SHORT).show();
        if (requestCode == REQUEST_CODE_MAIN
                || requestCode == REQUEST_CODE_SETTINGS
                || requestCode == REQUEST_CODE_SHOW
                || requestCode == REQUEST_CODE_GAME
                || requestCode == REQUEST_CODE_LIST) {
            Toast.makeText(this, "ONACTiVITY_2", Toast.LENGTH_SHORT).show();
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount signedInAccount = result.getSignInAccount();
                onConnection(getString(R.string.succes_conn_lb), requestCode);

            } else {
                String message = result.getStatus().getStatusMessage();
                if (message == null || message.isEmpty()) {
                    message = getString(R.string.signin_other_error);

                    onConnection(message, requestCode);

                }
            }
        }
    }

    protected void onConnection(final String msg, final int requestCode) {
        findViewById(R.id.full_screen_transparent).setVisibility(View.GONE);
        AlertDialog ad = new AlertDialog.Builder(this).create();
        ad.setMessage(msg);
         ad.setCancelable(true);
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

                switch (requestCode) {

                    case REQUEST_CODE_MAIN:
                        Intent intent = new Intent(getApplicationContext(), Game.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;

                    case REQUEST_CODE_SETTINGS:
                        if (msg.equals(getString(R.string.succes_conn_lb))) {
                            slp(true);
                        }

                        break;

                    case REQUEST_CODE_SHOW:

                        break;

                    case REQUEST_CODE_GAME:

                        break;

                    case REQUEST_CODE_LIST:

                        break;
                }

            }
        });
        ad.show();

        ((TextView) ad.getWindow().findViewById(android.R.id.message)).setTypeface(alertTypeface);


    }

    public void slp(boolean onoff) {
    }


    public void signOutLeaderboard() {
        findViewById(R.id.full_screen_transparent).setVisibility(View.VISIBLE);
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        signInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener <Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task <Void> task) {
                        // ((TextView) findViewById(R.id.lboard_on_off)).setText(getString(R.string.off));
                        findViewById(R.id.full_screen_transparent).setVisibility(View.GONE);
                        slp(false);
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
        Toast.makeText(this, "SHOW LEADERBOARDS", Toast.LENGTH_SHORT).show();
        System.out.println("BOARD NAME: " +boardName);
        System.out.println("BOARD: "+getBoardId("leaderboard_" + boardName));
        if (isSignedInLeaderboard()) {
            int boardId = getBoardId("leaderboard_" + boardName);
            Log.d("exp", getString(boardId));

            if (GoogleSignIn.getLastSignedInAccount(this) != null) {//TRY/CATCH
                Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .getLeaderboardIntent(getString(boardId))
                        .addOnSuccessListener(new OnSuccessListener <Intent>() {
                            @Override
                            public void onSuccess(Intent intent) {
                                startActivityForResult(intent, 9004);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "FAILED" + String.valueOf(e), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {

            final AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setMessage(String.format("%s!", getString(R.string.lboard_connect)));

            ad.setButton(Dialog.BUTTON_POSITIVE, getString(R.string.connect), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ad.cancel();
                    //  signInSilentlyLeaderboard(REQUEST_CODE_SHOW);
                   // startSignInIntentLeaderboard(REQUEST_CODE_LIST);
                    signInSilentlyLeaderboard(REQUEST_CODE_LIST);
                }
            });

            ad.setButton(Dialog.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    findViewById(R.id.full_screen_transparent).setVisibility(View.GONE);
                    ad.cancel();
                }
            });


            ad.setCancelable(true);
            ad.show();

          ((TextView) ad.getWindow().findViewById(android.R.id.message)).setTypeface(alertTypeface);


        }
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx


//    public void goLeaderboards(View v) {//koja tabela? ONCLICK POSLE ZAVRSENE IGRE
//
//
//    }

    public void showLeaderboardsList(View v) {

        final ConstraintLayout exp = findViewById(R.id.lboards_exp_list_wrapper);

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

//                Toast.makeText(getApplicationContext(), levelList.get(groupPosition).getLevel(childPosition), Toast.LENGTH_SHORT).show();

                showLeaderboard(levelList.get(groupPosition).getLevel(childPosition));
                System.out.println("showLeaderboard: "+levelList.get(groupPosition).getLevel(childPosition));
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

