package com.andraganoid.gameofmath.Misc;



import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.andraganoid.gameofmath.R;


public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);







        ((TextView) findViewById(R.id.app_name_ver)).setText(getString(R.string.app_name) + "  v " + appVersionName());

        ((TextView) findViewById(R.id.about_content)).setText(R.string.aboutText);

        ((TextView) findViewById(R.id.about_feedback)).setText(R.string.aboutFeedback);
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
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:andraganoid@gmail.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mailToMe));
        startActivity(Intent.createChooser(emailIntent, getString(R.string.mailchooser)));
    }

    public void mailToDesigner(View v) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:comicomike@outlook.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mailToMe));
        startActivity(Intent.createChooser(emailIntent, getString(R.string.mailchooser)));
    }

    public void goPolicy(View v) {
        Intent intent = new Intent(this, PrivacyPolicy.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
