package com.andraganoid.gameofmath;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.squareup.leakcanary.LeakCanary;



//ca-app-pub-7832187166369477~6543894378

// TODO
// showscore odbrojavanje rol cifara V2

//background V2 ???

//hiscores  googleplay
//reklame proveri

//help text

//text
//intro
//scores

//SOUNDS: lostlife, gameover, get bonuses, beep za start


public class Main extends AppCompatActivity {

//   private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(getApplication());












//
//        final Fabric fabric = new Fabric.Builder(this)
//                .kits(new Crashlytics())
//                .debuggable(true)  // Enables Crashlytics debugger
//                .build();
//        Fabric.with(fabric);
//
//
//        Button crashButton = new Button(this);
//        crashButton.setText("Crash!");
//        crashButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Crashlytics.getInstance().crash(); // Force a crash
//            }
//        });
//
//        addContentView(crashButton, new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));
//



      // MobileAds.initialize(this, getString(R.string.AD_MOB_APP_ID));

        Intent intent = new Intent(this, Game.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);


    }


}

