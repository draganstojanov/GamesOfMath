package com.andraganoid.gameofmath.Misc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.andraganoid.gameofmath.Game;
import com.andraganoid.gameofmath.R;


public class PrivacyPolicy extends AppCompatActivity {

    final private String POLICY_URL="file:///android_asset/privacy_policy.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_policy);

        WebView webview = findViewById(R.id.policy);
        webview.loadUrl(POLICY_URL);
    }

    public void goMain(View v) {
        Intent intent = new Intent(this, Game.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

