package com.andraganoid.gameofmath.Practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andraganoid.gameofmath.Game;
import com.andraganoid.gameofmath.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static com.andraganoid.gameofmath.Game.calc;

public class PracticeSettings extends AppCompatActivity {

    TextView pAdd, pSub, pMul, pDiv;
    ConstraintLayout clAdd, clSub, clMul, clDiv;
    TextView pAdd_1_1, pAdd_12_1, pAdd_123_1, pAdd_12_12;
    TextView pSub_1_1, pSub_12_1, pSub_123_1, pSub_12_12;
    TextView pMul_1_1, pMul_12_1, pMul_123_1;
    TextView pDiv_12_1, pDiv_123_1;
    TextView pMchoice, pCalc;


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_settings);

        AdView adViewBottomHeavy = findViewById(R.id.add_view_bottom_practice);
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewBottomHeavy.loadAd(adRequest);

        //  findViewById(R.id.practice_set_lay).setBackground(new BitmapDrawable(getResources(), background));
        calc = new Practice();

        pAdd = findViewById(R.id.practice_op_add);
        pSub = findViewById(R.id.practice_op_sub);
        pMul = findViewById(R.id.practice_op_mul);
        pDiv = findViewById(R.id.practice_op_div);

        clAdd = findViewById(R.id.practice_range_add);
        clSub = findViewById(R.id.practice_range_sub);
        clMul = findViewById(R.id.practice_range_mul);
        clDiv = findViewById(R.id.practice_range_div);

        pAdd_1_1 = findViewById(R.id.practice_range_add_1_1);
        pAdd_12_1 = findViewById(R.id.practice_range_add_12_1);
        pAdd_123_1 = findViewById(R.id.practice_range_add_123_1);
        pAdd_12_12 = findViewById(R.id.practice_range_add_12_12);

        pSub_1_1 = findViewById(R.id.practice_range_sub_1_1);
        pSub_12_1 = findViewById(R.id.practice_range_sub_12_1);
        pSub_123_1 = findViewById(R.id.practice_range_sub_123_1);
        pSub_12_12 = findViewById(R.id.practice_range_sub_12_12);

        pMul_1_1 = findViewById(R.id.practice_range_mul_1_1);
        pMul_12_1 = findViewById(R.id.practice_range_mul_12_1);
        pMul_123_1 = findViewById(R.id.practice_range_mul_123_1);

        pDiv_12_1 = findViewById(R.id.practice_range_div_12_1);
        pDiv_123_1 = findViewById(R.id.practice_range_div_123_1);

        pMchoice = findViewById(R.id.practice_multi);
        pCalc = findViewById(R.id.practice_calc);
    }


    public void pOperation(View v) {
        pAdd.setBackgroundColor(getResources().getColor(R.color.base));
        pSub.setBackgroundColor(getResources().getColor(R.color.base));
        pMul.setBackgroundColor(getResources().getColor(R.color.base));
        pDiv.setBackgroundColor(getResources().getColor(R.color.base));

        clAdd.setVisibility(View.GONE);
        clSub.setVisibility(View.GONE);
        clMul.setVisibility(View.GONE);
        clDiv.setVisibility(View.GONE);

        switch (v.getId()) {
            case R.id.practice_op_add:
                clAdd.setVisibility(View.VISIBLE);
                pAdd.setBackgroundColor(getResources().getColor(R.color.checked));
                rangeSetChecked(pAdd_1_1);
                calc.setOperandMinVal(1, 1);
                calc.setOperandMaxVal(10, 10);
                calc.setOperationType(0);
                break;

            case R.id.practice_op_sub:
                clSub.setVisibility(View.VISIBLE);
                pSub.setBackgroundColor(getResources().getColor(R.color.checked));
                rangeSetChecked(pSub_1_1);
                calc.setOperandMinVal(2, 1);
                calc.setOperandMaxVal(10, 9);
                calc.setOperationType(1);
                break;

            case R.id.practice_op_mul:
                clMul.setVisibility(View.VISIBLE);
                pMul.setBackgroundColor(getResources().getColor(R.color.checked));
                rangeSetChecked(pMul_1_1);
                calc.setOperandMinVal(1, 1);
                calc.setOperandMaxVal(10, 10);
                calc.setOperationType(2);
                break;

            case R.id.practice_op_div:
                clDiv.setVisibility(View.VISIBLE);
                pDiv.setBackgroundColor(getResources().getColor(R.color.checked));
                rangeSetChecked(pDiv_12_1);
                calc.setOperandMinVal(10, 2);
                calc.setOperandMaxVal(100, 10);
                calc.setOperationType(3);
                break;
        }


    }

    public void pRange(View v) {


        switch (v.getId()) {

            case R.id.practice_range_add_1_1:
                rangeSetChecked(pAdd_1_1);
                calc.setOperandMinVal(1, 1);
                calc.setOperandMaxVal(10, 10);
                break;

            case R.id.practice_range_add_12_1:
                rangeSetChecked(pAdd_12_1);
                calc.setOperandMinVal(10, 1);
                calc.setOperandMaxVal(100, 10);
                break;

            case R.id.practice_range_add_123_1:
                rangeSetChecked(pAdd_123_1);
                calc.setOperandMinVal(100, 1);
                calc.setOperandMaxVal(1000, 10);
                break;

            case R.id.practice_range_add_12_12:
                rangeSetChecked(pAdd_12_12);
                calc.setOperandMinVal(10, 10);
                calc.setOperandMaxVal(100, 100);
                break;

            case R.id.practice_range_sub_1_1:
                rangeSetChecked(pSub_1_1);
                calc.setOperandMinVal(2, 1);
                calc.setOperandMaxVal(10, 9);
                break;

            case R.id.practice_range_sub_12_1:
                rangeSetChecked(pSub_12_1);
                calc.setOperandMinVal(10, 1);
                calc.setOperandMaxVal(100, 9);
                break;

            case R.id.practice_range_sub_123_1:
                rangeSetChecked(pSub_123_1);
                calc.setOperandMinVal(100, 1);
                calc.setOperandMaxVal(1000, 10);
                break;

            case R.id.practice_range_sub_12_12:
                rangeSetChecked(pSub_12_12);
                calc.setOperandMinVal(11, 10);
                calc.setOperandMaxVal(100, 99);
                break;

            case R.id.practice_range_mul_1_1:
                rangeSetChecked(pMul_1_1);
                calc.setOperandMinVal(1, 1);
                calc.setOperandMaxVal(10, 10);
                break;

            case R.id.practice_range_mul_12_1:
                rangeSetChecked(pMul_12_1);
                calc.setOperandMinVal(10, 1);
                calc.setOperandMaxVal(100, 10);
                break;

            case R.id.practice_range_mul_123_1:
                rangeSetChecked(pMul_123_1);
                calc.setOperandMinVal(100, 1);
                calc.setOperandMaxVal(1000, 10);
                break;

            case R.id.practice_range_div_12_1:
                rangeSetChecked(pDiv_12_1);
                calc.setOperandMinVal(10, 2);
                calc.setOperandMaxVal(100, 10);
                break;

            case R.id.practice_range_div_123_1:
                rangeSetChecked(pDiv_123_1);
                calc.setOperandMinVal(100, 2);
                calc.setOperandMaxVal(1000, 10);
                break;

        }
    }

    void rangeSetChecked(View v) {
        pAdd_1_1.setBackgroundColor(getResources().getColor(R.color.base));
        pAdd_12_1.setBackgroundColor(getResources().getColor(R.color.base));
        pAdd_123_1.setBackgroundColor(getResources().getColor(R.color.base));
        pAdd_12_12.setBackgroundColor(getResources().getColor(R.color.base));

        pSub_1_1.setBackgroundColor(getResources().getColor(R.color.base));
        pSub_12_1.setBackgroundColor(getResources().getColor(R.color.base));
        pSub_123_1.setBackgroundColor(getResources().getColor(R.color.base));
        pSub_12_12.setBackgroundColor(getResources().getColor(R.color.base));

        pMul_1_1.setBackgroundColor(getResources().getColor(R.color.base));
        pMul_12_1.setBackgroundColor(getResources().getColor(R.color.base));
        pMul_123_1.setBackgroundColor(getResources().getColor(R.color.base));

        pDiv_12_1.setBackgroundColor(getResources().getColor(R.color.base));
        pDiv_123_1.setBackgroundColor(getResources().getColor(R.color.base));

        v.setBackgroundColor(getResources().getColor(R.color.checked));
    }


    public void pAnswer(View v) {
        switch (v.getId()) {
            case R.id.practice_multi:
                calc.setAnswerType("multichoice");
                pCalc.setBackgroundColor(getResources().getColor(R.color.base));
                pMchoice.setBackgroundColor(getResources().getColor(R.color.checked));
                break;

            case R.id.practice_calc:
                calc.setAnswerType("calculate");
                pMchoice.setBackgroundColor(getResources().getColor(R.color.base));
                pCalc.setBackgroundColor(getResources().getColor(R.color.checked));
                break;
        }
    }


    public void startPractice(View v) {
        startActivity(new Intent(this, PracticeBoard.class));
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

        //  Toast.makeText(this, "PRACTICE SETTINGS HELP", Toast.LENGTH_LONG).show();
        findViewById(R.id.help_layout).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.help_title)).setText("PRACTICE");
        ((TextView)findViewById(R.id.help_text)).setText(h1+"\n\n"+h2+"\n\n"+h3+"\n\n"+h4);
        findViewById(R.id.practice_set_lay).setClickable(false);
    }

    public void goCloseHelp(View v){ findViewById(R.id.help_layout).setVisibility(View.GONE);}
}