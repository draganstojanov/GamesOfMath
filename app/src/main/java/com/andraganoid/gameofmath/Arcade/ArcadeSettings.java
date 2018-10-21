package com.andraganoid.gameofmath.Arcade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.andraganoid.gameofmath.Game;
import com.andraganoid.gameofmath.MathBase;
import com.andraganoid.gameofmath.R;

import java.util.Arrays;

import static com.andraganoid.gameofmath.Game.calc;


public class ArcadeSettings extends AppCompatActivity {

    RecyclerView rv;
    RecyclerView.Adapter arcadeAdapter;
    RecyclerView.LayoutManager ArcadeLayoutManager;


    @Override
    protected void onResume() {
        super.onResume();
        arcadeAdapter.notifyDataSetChanged();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arcade_settings);

        calc = new Arcade(Arrays.asList(getResources().getStringArray(R.array.arcade_levels)));
        Game.mathBase = new MathBase(this);

        rv = findViewById(R.id.arcade_recycler_view);
        rv.setHasFixedSize(true);
        ArcadeLayoutManager = new GridLayoutManager(this, 2);
        rv.setLayoutManager(ArcadeLayoutManager);
        arcadeAdapter = new ArcadeAdapter(calc.levelNames);
        rv.setAdapter(arcadeAdapter);


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
