package com.andraganoid.gameofmath;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.andraganoid.gameofmath.DataBase.BonusRepository;
import com.andraganoid.gameofmath.Game.Game;
import com.andraganoid.gameofmath.Misc.Sounds;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startAnimator();

        new Thread(() -> {
            new BonusRepository(getApplicationContext()).initBonuses();
         //   MobileAds.initialize(getApplicationContext(), initializationStatus -> {});
          //  Sounds ms = Sounds.getInstance(getApplicationContext());
        }).start();
    }

    private void goGame() {
        Intent intent = new Intent(this, Game.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void startAnimator() {
        ImageView logo_main = findViewById(R.id.game_logo_main);
        ObjectAnimator animator = ObjectAnimator.ofFloat(logo_main, View.ALPHA, 0f, 1f);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {
            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                goGame();
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {
            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {
            }
        });
        animator.setDuration(5000).start();
    }
}

