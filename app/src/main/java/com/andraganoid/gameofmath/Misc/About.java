package com.andraganoid.gameofmath.Misc;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.andraganoid.gameofmath.Game.Game;
import com.andraganoid.gameofmath.R;

public class About extends AppCompatActivity {

    final private String MAIL_TO_AUTHOR = "mailto:andraganoid@gmail.com";
    final private String MAIL_TO_DESIGNER = "mailto:milenanikolicc@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        findViewById(R.id.about_lay).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.about_version)).setText(" v " + appVersionName());
    }

    public String appVersionName() {
        String vn = "";
        try {
            vn = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return vn;
    }

    public void mailToMe(View v) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(MAIL_TO_AUTHOR));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_feedback));
        startActivity(Intent.createChooser(emailIntent, getString(R.string.mail_chooser)));
    }

    public void mailToDesigner(View v) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(MAIL_TO_DESIGNER));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_feedback));
        startActivity(Intent.createChooser(emailIntent, getString(R.string.mail_chooser)));
    }

    public void goPolicy(View v) {
        Intent intent = new Intent(this, PrivacyPolicy.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goMain(View v) {
        startActivity(new Intent(this, Game.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
