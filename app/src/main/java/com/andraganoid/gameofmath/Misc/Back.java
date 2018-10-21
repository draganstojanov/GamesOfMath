package com.andraganoid.gameofmath.Misc;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

import java.util.Random;

public class Back {

    private Bitmap back;
    private Canvas canvas = new Canvas();
    private Paint paint = new Paint();
    Random r = new Random();
    private int rows, textSize, c;
    private String TXT[] = {"0", "9", "+", "8", "1", "=", "2", "7", "\u00F7", "6", "3", "\u00D7", "2", "1", "-"};
    private final int BWIDTH = 900;
    private final int BHEIGHT = 1600;

    public Bitmap getBack() {

        rows = r.nextInt(45 - 5 + 1) + 5;

        back = Bitmap.createBitmap(BWIDTH, 1608, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(back);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));


        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < rows + 1; j++) {
                Log.i("math_BACK RND: ", String.valueOf(rows));

                //   Log.i("math_BACK RND: ", String.valueOf(1600 / ((r.nextInt(45 - rows + 1) + rows))));
                textSize = BHEIGHT / ((r.nextInt(45 - rows + 1) + rows));
                paint.setTextSize(textSize);
                c = r.nextInt(32);
                paint.setColor(Color.argb(128, c, c, c));
                canvas.drawText(TXT[r.nextInt(15000) % 15], (BWIDTH / rows) * (i + 1), (BHEIGHT / rows) * (j + 1) - (BHEIGHT / rows - textSize) / 2, paint);


            }
        }
        return back;

    }


}



