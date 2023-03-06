package com.andraganoid.gameofmath.Practice;

import android.content.Intent;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.andraganoid.gameofmath.Game.Game;
import com.andraganoid.gameofmath.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static com.andraganoid.gameofmath.Game.Game.calc;

public class PracticeSettings extends AppCompatActivity {

    private TextView pAdd, pSub, pMul, pDiv;
    private ConstraintLayout clAdd, clSub, clMul, clDiv;
    private TextView pAdd_1_1, pAdd_12_1, pAdd_123_1, pAdd_12_12;
    private TextView pSub_1_1, pSub_12_1, pSub_123_1, pSub_12_12;
    private TextView pMul_1_1, pMul_12_1, pMul_123_1;
    private TextView pDiv_12_1, pDiv_123_1;
    private TextView pMchoice, pCalc;
    private AdView adViewBottomPractice;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adViewBottomPractice.destroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_settings);
        adViewBottomPractice = findViewById(R.id.add_view_bottom_practice);
        adViewBottomPractice.loadAd(new AdRequest.Builder().build());
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
        findViewById(R.id.help_layout).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.help_title)).setText(getString(R.string.practice));
        ((TextView) findViewById(R.id.help_text)).setText(getString(R.string.help_practice));
        findViewById(R.id.practice_set_lay).setClickable(false);
    }

    public void goCloseHelp(View v) {
        findViewById(R.id.help_layout).setVisibility(View.GONE);
    }
}