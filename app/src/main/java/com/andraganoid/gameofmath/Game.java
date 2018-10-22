package com.andraganoid.gameofmath;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.andraganoid.gameofmath.Arcade.ArcadeSettings;
import com.andraganoid.gameofmath.Easy.EasySettings;
import com.andraganoid.gameofmath.Heavy.HeavySettings;
import com.andraganoid.gameofmath.Operation.Task;
import com.andraganoid.gameofmath.Practice.PracticeSettings;



public class Game extends AppCompatActivity {
   // public static Back back = new Back();
   // public static Bitmap background;
    public static Calc calc;
    public static Task task;
   // public static MathBase mathBase;

    Intent intent;
   // android.support.constraint.ConstraintLayout cl;





    @Override
    protected void onResume() {
        super.onResume();
      //  Toast.makeText(this, "homme resume", Toast.LENGTH_SHORT).show();
        //JUST4TESTING
//        score.setBonus(score.HEAVY_XTRA_TIME,9);
//        score.setBonus(score.HEAVY_HINTS,9);
//        score.setBonus(score.EASY_XTRA_TIME,9);
//        score.setBonus(score.EASY_SKIPS,9);
//        score.setBonus(score.EASY_RESETS,9);
        // cl.setBackground(new BitmapDrawable(getResources(), back.getBack()));
    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Toast.makeText(this, "homme restart", Toast.LENGTH_SHORT).show();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        MathBase mb=new MathBase(getApplicationContext());
      //  Toast.makeText(this, "homme creata", Toast.LENGTH_SHORT).show();
      //  cl = (android.support.constraint.ConstraintLayout) findViewById(R.id.game_lay);
      //  cl.setBackground(new BitmapDrawable(getResources(), back.getBack()));

//        mathBase = new MathBase(this);
//        sNewBest=MediaPlayer.create(this,R.raw.new_best);
//
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//
//        effect = new Effect(metrics.widthPixels, metrics.heightPixels);
    }


    public void goPractice(View v) {
       // background = back.getBack();
        intent = new Intent(this, PracticeSettings.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goArcade(View v) {

      //  background = back.getBack();
        intent = new Intent(this, ArcadeSettings.class);
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


}
