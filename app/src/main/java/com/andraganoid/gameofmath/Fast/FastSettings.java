package com.andraganoid.gameofmath.Fast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.andraganoid.gameofmath.Game;
import com.andraganoid.gameofmath.R;

import java.util.Arrays;

import static com.andraganoid.gameofmath.Game.calc;


public class FastSettings extends AppCompatActivity {

    RecyclerView rv;
    RecyclerView.Adapter fastAdapter;
    RecyclerView.LayoutManager FastLayoutManager;


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fastAdapter.notifyDataSetChanged();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arcade_settings);

        calc = new Fast(Arrays.asList(getResources().getStringArray(R.array.fast_calc_levels)));
       // Game.mathBase = new MathBase(this);

        rv = findViewById(R.id.arcade_recycler_view);
        rv.setHasFixedSize(true);
        FastLayoutManager = new GridLayoutManager(this, 2);
        rv.setLayoutManager(FastLayoutManager);
        fastAdapter = new FastAdapter(calc.levelNames,this);
        rv.setAdapter(fastAdapter);


    }

    public void goHome(View v) {
        Intent intent = new Intent(this, Game.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goHelp(View v) {
        Toast.makeText(this, "ARCADE SETTINGS HELP", Toast.LENGTH_LONG).show();
    }
}
