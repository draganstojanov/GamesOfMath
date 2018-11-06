package com.andraganoid.gameofmath.Fast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.andraganoid.gameofmath.Game;
import com.andraganoid.gameofmath.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Arrays;

import static com.andraganoid.gameofmath.Game.calc;


public class FastSettings extends AppCompatActivity {

    RecyclerView rv;
    RecyclerView.Adapter fastAdapter;
    RecyclerView.LayoutManager FastLayoutManager;

  //  private AdView adViewBottomFast;

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
        setContentView(R.layout.fast_settings);

        AdView adViewBottomFast = findViewById(R.id.add_view_bottom_fast);
        adViewBottomFast.loadAd(new AdRequest.Builder().build());
        calc = new Fast(Arrays.asList(getResources().getStringArray(R.array.fast_calc_levels)));
        rv = findViewById(R.id.fast_recycler_view);
        rv.setHasFixedSize(true);
        FastLayoutManager = new GridLayoutManager(this, 2);
        rv.setLayoutManager(FastLayoutManager);
        fastAdapter = new FastAdapter(calc.levelNames,Arrays.asList(getResources().getStringArray(R.array.fast_calc_levels_description)));
        rv.setAdapter(fastAdapter);


    }

    public void goHome(View v) {
        Intent intent = new Intent(this, Game.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goHelp(View v) {

        String h1="Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
        String h2="Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                "\n" +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.";

        String h3="It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).";
        String h4="There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.";

        findViewById(R.id.help_layout).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.help_title)).setText("FAST CALC");
        ((TextView)findViewById(R.id.help_text)).setText(h1+"\n\n"+h2+"\n\n"+h3+"\n\n"+h4);
    }

    public void goCloseHelp(View v){ findViewById(R.id.help_layout).setVisibility(View.GONE);}


}
