package com.andraganoid.gameofmath.util;

import android.app.Activity;
import android.view.View;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Static alternative — use this if an activity can't extend BaseActivity
 * (e.g. it already extends something else). Call from onCreate() after
 * setContentView().
 *
 *   EdgeToEdgeUtils.apply(this, findViewById(R.id.root));
 */
public final class EdgeToEdgeUtils {

    private EdgeToEdgeUtils() {}

    public static void apply(Activity activity, View rootView) {
        if (activity == null || rootView == null) return;
        WindowCompat.setDecorFitsSystemWindows(activity.getWindow(), false);
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
    }
}