package com.andraganoid.gameofmath;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.andraganoid.gameofmath .DataBase.BonusRepository;
import com.andraganoid.gameofmath.Game.Game;
import com.andraganoid.gameofmath.Misc.Sounds;
import com.google.android.gms.ads.MobileAds;


public class Main extends AppCompatActivity {
    private ImageView logo_main;
    private ObjectAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startAnimator();

        new Thread(new Runnable() {
            @Override
            public void run() {
                new BonusRepository(getApplicationContext()).initBonuses();
                MobileAds.initialize(getApplicationContext(), getString(R.string.AD_MOB_APP_ID));
                Sounds ms = Sounds.getInstance(getApplicationContext());
            }
        }).start();
    }

    private void goGame() {
        Intent intent = new Intent(this, Game.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void startAnimator() {
        logo_main = findViewById(R.id.game_logo_main);
        animator = ObjectAnimator.ofFloat(logo_main, View.ALPHA, 0f, 1f);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                goGame();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.setDuration(5000).start();
    }
}

