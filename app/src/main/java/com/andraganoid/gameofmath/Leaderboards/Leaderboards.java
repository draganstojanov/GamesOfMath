//package com.andraganoid.gameofmath.Leaderboards;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Typeface;
//import android.preference.PreferenceManager;
//import android.support.annotation.NonNull;
//import android.support.constraint.ConstraintLayout;
//import android.support.v4.content.res.ResourcesCompat;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.ExpandableListAdapter;
//import android.widget.ExpandableListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.andraganoid.gameofmath.GamePlay;
//import com.andraganoid.gameofmath.LeaderboardAdapter;
//import com.andraganoid.gameofmath.Leaderboards.Level;
//import com.andraganoid.gameofmath.R;
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.google.android.gms.games.Games;
//import com.google.android.gms.games.leaderboard.ScoreSubmissionData;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//
//public class Leaderboards extends AppCompatActivity {
//
//    public Typeface alertTypeface = ResourcesCompat.getFont(this, R.font.luckiestguy);
//
//
//    public SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//    public SharedPreferences.Editor prefsEditor = prefs.edit();
//    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
//
//    public void signInSilentlyLeaderboard() {
//        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
//                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
//        signInClient.silentSignIn().addOnCompleteListener(this,
//                new OnCompleteListener <GoogleSignInAccount>() {
//                    @Override
//                    public void onComplete(@NonNull com.google.android.gms.tasks.Task <GoogleSignInAccount> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), "SILENT OK", Toast.LENGTH_SHORT).show();
//                            // The signed in account is stored in the task's result.
//                            GoogleSignInAccount signedInAccount = task.getResult();
//                        } else {
//                            startSignInIntentLeaderboard();
//                            // Player will need to sign-in explicitly using via UI
//                        }
//                    }
//                });
//    }
//
//
//    public void startSignInIntentLeaderboard() {
//        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
//                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
//        Intent intent = signInClient.getSignInIntent();
//        startActivityForResult(intent, 9013);
//
//
//    }
//
//    public boolean isSignedInLeaderboard() {
//        return GoogleSignIn.getLastSignedInAccount(this) != null;
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 9013) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//
//            if (result.isSuccess()) {
//
//                Toast.makeText(this, "SIGNED IN", Toast.LENGTH_SHORT).show();
//                GoogleSignInAccount signedInAccount = result.getSignInAccount();
//                (findViewById(R.id.first_logo)).setVisibility(View.GONE);
//            } else {
//                String message = result.getStatus().getStatusMessage();
//                if (message == null || message.isEmpty()) {
//                    message = getString(R.string.signin_other_error);
//
//                    prefsEditor.putBoolean("leaderboardsPermission", false).apply();
//                    //  slp(false);
//
//                }
//                (findViewById(R.id.first_logo)).setVisibility(View.GONE);
//
//
//                AlertDialog ad = new AlertDialog.Builder(this).create();
//                ad.setMessage(message);
//                ad.setCancelable(true);
//                ad.show();
//
//
//                ((TextView) ad.getWindow().findViewById(android.R.id.message)).setTypeface(alertTypeface);
//
////                new AlertDialog.Builder(this).setMessage(message)
////                        .setCancelable(true).show();
////
//
//            }
//        }
//    }
//
//
//    public void signOutLeaderboard() {
//        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
//                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
//        signInClient.signOut().addOnCompleteListener(this,
//                new OnCompleteListener <Void>() {
//                    @Override
//                    public void onComplete(@NonNull com.google.android.gms.tasks.Task <Void> task) {
//                        ((TextView) findViewById(R.id.lboard_on_off)).setText(getString(R.string.off));
//                    }
//
//                });
//    }
//
//
//    public void submitScoreLeaderboard(String boardID, long result) {
//
//        // Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this)).submitScoreLeaderboard(getString(R.string.leaderboard_id), 1337);
//        // Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this)).submitScore(getString(R.string.leaderboard_id), 1337);
//        //  int e = (rand.nextInt(1000));
//
////        if (isSignedInLeaderboard()) {xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
//        Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
//                .submitScoreImmediate(boardID, result)
//                .addOnSuccessListener(new OnSuccessListener <ScoreSubmissionData>() {
//                    @Override
//                    public void onSuccess(ScoreSubmissionData scoreSubmissionData) {
//
//                        Log.d("SUBMIT SUCCESS", String.valueOf(scoreSubmissionData.toString()));
//
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//                Log.d("SUBMIT FAILED", String.valueOf(e.toString()));
//
//            }
//        });
//
//        // }
//        //   .submitScore("CgkItryh-O0OEAIQFQ",e );callback
//    }
//
//
//    public void showLeaderboard(String boardName) {
//
//        if (isSignedInLeaderboard()) {//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
//            int boardId = getBoardId("leaderboard_" + boardName);
//            Log.d("exp", getString(boardId));
//
//            if (GoogleSignIn.getLastSignedInAccount(this) != null) {//TRY/CATCH
//                Games.getLeaderboardsClient(this,
//                        GoogleSignIn.getLastSignedInAccount(this))
//                        .getLeaderboardIntent(getString(boardId))
//                        .addOnSuccessListener(new OnSuccessListener <Intent>() {
//                            @Override
//                            public void onSuccess(Intent intent) {
//                                startActivityForResult(intent, 9004);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getApplicationContext(), "FAILED" + String.valueOf(e), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        } else {
//
//
//            //connectAlert();
//
//            AlertDialog ad = new AlertDialog.Builder(this).create();
//            ad.setMessage(String.format("%s!", getString(R.string.lboard_connect)));
//            ad.setCancelable(true);
//            ad.show();
//
//            ((TextView) ad.getWindow().findViewById(android.R.id.message)).setTypeface(alertTypeface);
//
//
//        }
//    }
//
//    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
//
//
//    public void goLeaderboards(View v) {//koja tabela? ONCLICK POSLE ZAVRSENE IGRE
//
//
//    }
//
//    public void showLeaderboardsList(View v) {
//
//        final ConstraintLayout exp = findViewById(R.id.lboards_exp_list_wrapper);
//
//        //   if (isSignedInLeaderboard()) {//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
//        //    if (true) {
//
//        final ArrayList <Level> levelList = new ArrayList <Level>();
//
//
//        levelList.add(new Level(getString(R.string.fast_calc),
//                Arrays.asList(getResources().getStringArray(R.array.fast_calc_levels)),
//                Arrays.asList(getResources().getStringArray(R.array.fast_calc_levels_description))));
//
//        levelList.add(new Level(getString(R.string.easy_calc),
//                Arrays.asList(getResources().getStringArray(R.array.easy_calc_levels)),
//                Arrays.asList(getResources().getStringArray(R.array.easy_calc_levels_description))));
//
//        levelList.add(new Level(getString(R.string.heavy_calc),
//                Arrays.asList(getResources().getStringArray(R.array.heavy_calc_levels)),
//                Arrays.asList(getResources().getStringArray(R.array.heavy_calc_levels_description))));
//
//        levelList.add(new Level(getString(R.string.cancel), null, null));
//
//        ExpandableListView expListView = (ExpandableListView) findViewById(R.id.lboards_exp_list);
//
//
//        exp.setVisibility(View.VISIBLE);
//        ExpandableListAdapter elAdapter = new LeaderboardAdapter(this, levelList);
//
//        // setting list adapter
//        expListView.setAdapter(elAdapter);
//
//        // Listview Group click listener
//        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v,
//                                        int groupPosition, long id) {
//                if (groupPosition + 1 == levelList.size()) {
//                    exp.setVisibility(View.GONE);
//                }
//                return false;
//            }
//        });
//
//        // Listview Group expanded listener
////        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
////
////            @Override
////            public void onGroupExpand(int groupPosition) { Toast.makeText(GamePlay.this, levelList.get(groupPosition).getGameName(), Toast.LENGTH_SHORT).show();
//////                Toast.makeText(getApplicationContext(),
//////                        listDataHeader.get(groupPosition) + " Expanded",
//////                        Toast.LENGTH_SHORT).show();
////            }
////        });
//
//        // Listview Group collasped listener
////        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
////
////            @Override
////            public void onGroupCollapse(int groupPosition) { Toast.makeText(GamePlay.this, levelList.get(groupPosition).getGameName(), Toast.LENGTH_SHORT).show();
//////                Toast.makeText(getApplicationContext(),
//////                        listDataHeader.get(groupPosition) + " Collapsed",
//////                        Toast.LENGTH_SHORT).show();
////
////            }
////        });
//
//        // Listview on child click listener
//        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
//
//                Toast.makeText(getApplicationContext(), levelList.get(groupPosition).getLevel(childPosition), Toast.LENGTH_SHORT).show();
//
//                showLeaderboard(levelList.get(groupPosition).getLevel(childPosition));
//                return false;
//            }
//        });
//
//        //  } else {
//
//        //  exp.setVisibility(View.GONE);
//        //      connectAlert();
//        //  }
//    }
//
//
//    private void connectAlert() {
////        AlertDialog ad = new AlertDialog.Builder(GamePlay.this).create();
////        ad.setMessage(String.format("%s!", getString(R.string.lboard_connect)));
////        ad.setCancelable(true);
////        ad.show();
////
////        ((TextView) ad.getWindow().findViewById(android.R.id.message)).setTypeface(alertTypeface);
//
//
////            new AlertDialog.Builder(this).setMessage(getString(R.string.lboard_connect) + "!")
////                    .setCancelable(true).show();
//
//    }
//
//
//    public static int getBoardId(String resName) {
//
//        try {
//            Field idField = R.string.class.getDeclaredField(resName);
//            return idField.getInt(idField);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return -1;
//        }
//    }
//
//
//}
