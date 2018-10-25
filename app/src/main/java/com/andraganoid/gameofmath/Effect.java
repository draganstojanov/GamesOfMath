package com.andraganoid.gameofmath;


import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.plattysoft.leonids.ParticleSystem;

import java.util.Random;

public class Effect extends AppCompatActivity {
    public CountDownTimer cdt, intro;
    public Intent boardIntent;
    public boolean goMain,isEnd;
    public boolean soundIsOn;
    DisplayMetrics metrics;
    int shot, last, width, height;
    Random rand;
    ParticleSystem ps;
   public MediaPlayer sFirework[],sNewBest,sTimeBeep,sGetBonus,sUseBonus, sRight_answer, sWrong_answer;

    int fireDots[] = new int[]{
            R.drawable.fw_01,
            R.drawable.fw_02,
            R.drawable.fw_03,
            R.drawable.fw_04,
            R.drawable.fw_05,
            R.drawable.fw_06,
            R.drawable.fw_07,
            R.drawable.fw_08};

    ObjectAnimator animation;
    Keyframe kf0, kf1, kf2, kf3;
    float[] fx = new float[8];
    float[] fy = new float[8];
    int aCount;
    public boolean colorChange;


    @Override
    protected void onResume() {
        super.onResume();
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        rand = new Random();
        soundIsOn=true;
        sFirework = new MediaPlayer[]{
                MediaPlayer.create(this, R.raw.firework1),
                MediaPlayer.create(this, R.raw.firework2),
                MediaPlayer.create(this, R.raw.firework3)};
        sNewBest = MediaPlayer.create(this, R.raw.new_best);
        sTimeBeep=MediaPlayer.create(this, R.raw.beep_timer);
        sGetBonus=MediaPlayer.create(this, R.raw.get_bonus);
        sUseBonus=MediaPlayer.create(this, R.raw.use_bonus);
        sRight_answer =MediaPlayer.create(this, R.raw.right_answer);
        sWrong_answer =MediaPlayer.create(this, R.raw.wrong_answer);
    }

    public void goFire() {

        //  findViewById(R.id.fireworks_wrapper_e).setVisibility(View.VISIBLE);

        if (soundIsOn) {
            sNewBest.start();
        }
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

        if (soundIsOn) {
            sFirework[getRnd()].start();
        }

        ps = new ParticleSystem(this,
                200, fireDots[rand.nextInt(1000) % 8],
                1000 + 500 * (rand.nextInt(1000) % 9));
        ps.setSpeedRange(0.1f, 0.4f)
                .setRotationSpeed(300)
                .oneShot(findViewById(R.id.fireworks), 50 + rand.nextInt(150));
    }


    public void startAnimation(View view, int duration) {
        int r1 = getRnd();
//        switch (rand.nextInt(1000) % 3) {
        switch (r1) {
            case 0:
                animRotAndShake(view, 15f, 3 * duration);
                break;
            case 1:
                animShake1(view, 16 * duration, 15);
                break;
            case 2:
                String r = "";

                int r2 = getRnd();
//        switch (rand.nextInt(1000) % 3) {
                switch (r2) {
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


}
