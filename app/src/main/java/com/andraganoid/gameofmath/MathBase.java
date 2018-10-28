package com.andraganoid.gameofmath;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

public class MathBase extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private ContentValues cv = new ContentValues();
    Cursor cursor;
    //    Gson gson = new Gson();
//    Type type = new TypeToken<List<Long>>() {}.getType();
//    ArrayList<Long> list = new ArrayList<>();
    HashMap<String, Long> hm = new HashMap<String, Long>();
    //  String query;
    String[] t = new String[1];

    private static MathBase mathBase;
    private static Context mContext;


    public MathBase(Context context) {
        this();
        mContext = context;
    }

    //  private MathBase(Context context) {
    //     super(context, "math_base", null, 1);
    //  }

    private MathBase() {
        super(mContext, "math_base", null, 1);
    }


    public static MathBase getInstance() {

        if (mathBase == null) {
            mathBase = new MathBase();
        }

        return mathBase;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE high_scores (high_scores_level TEXT UNIQUE,high_scores_result INTEGER)");
        db.execSQL("CREATE TABLE bonus (bonus_type TEXT UNIQUE,bonus_val INTEGER)");

//        db.execSQL("CREATE TABLE arcade (arcade_level TEXT UNIQUE,arcade_result INTEGER)");
//        db.execSQL("CREATE TABLE easy (easy_level TEXT UNIQUE,easy_result INTEGER)");
//        db.execSQL("CREATE TABLE heavy (heavy_level TEXT UNIQUE,heavy_result INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS high_scores");
        db.execSQL("DROP TABLE IF EXISTS bonus");

//        db.execSQL("DROP TABLE IF EXISTS arcade");
//        db.execSQL("DROP TABLE IF EXISTS easy");
//        db.execSQL("DROP TABLE IF EXISTS heavy");

        onCreate(db);
    }


    public HashMap<String, Long> getHighScores(List<String > ln) {

        hm.clear();

        for (int i = 0; i < ln.size(); i++) {
            hm.put(ln.get(i), 0L);
        }

        db = this.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM high_scores", null);
        while (cursor.moveToNext()) {
            hm.put(cursor.getString(cursor.getColumnIndexOrThrow("high_scores_level")),
                    cursor.getLong(cursor.getColumnIndexOrThrow("high_scores_result")));
        }
        db.close();
        return hm;

    }


    public void saveHighScore(String lvl, Long res) {
        cv.clear();
        cv.put("high_scores_level", lvl);
        cv.put("high_scores_result", res);
        db = this.getWritableDatabase();
        db.insertWithOnConflict("high_scores", null, cv, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }


/*    public HashMap<String, Long> getFastHiScores() {

        hm.clear();

        db = this.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM arcade", null);
        while (cursor.moveToNext()) {
            hm.put(cursor.getString(cursor.getColumnIndexOrThrow("arcade_level")), cursor.getLong(cursor.getColumnIndexOrThrow("arcade_result")));
        }
        db.close();
        return hm;

    }*/


/*    public void saveFastResult(String lvl, Long res) {
        cv.clear();
        cv.put("arcade_level", lvl);
        cv.put("arcade_result", res);
        db = this.getWritableDatabase();
        long ins = db.insertWithOnConflict("arcade", null, cv, SQLiteDatabase.CONFLICT_REPLACE);
        Log.i("score-sav-arc", String.valueOf(ins));
        db.close();
    }*/


/*    public HashMap<String, Long> getEasyHiScores() {

        hm.clear();
        db = this.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM easy", null);

        while (cursor.moveToNext()) {
            hm.put(cursor.getString(cursor.getColumnIndexOrThrow("easy_level")), cursor.getLong(cursor.getColumnIndexOrThrow("easy_result")));
        }
        db.close();

        return hm;

    }*/


/*    public void saveEasyResult(String lvl, Long res) {

        cv.clear();
        cv.put("easy_level", lvl);
        cv.put("easy_result", res);
        db = this.getWritableDatabase();
        db.insertWithOnConflict("easy", null, cv, SQLiteDatabase.CONFLICT_REPLACE);
        //  Log.i("score-sav-arc", String.valueOf(ins));
        db.close();
    }*/


/*    public HashMap<String, Long> getHeavyHiScores() {

        hm.clear();

        db = this.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM heavy", null);
        while (cursor.moveToNext()) {
            hm.put(cursor.getString(cursor.getColumnIndexOrThrow("heavy_level")), cursor.getLong(cursor.getColumnIndexOrThrow("heavy_result")));
        }
        db.close();
        return hm;

    }*/


/*    public void saveHeavyResult(String lvl, Long res) {
        cv.clear();
        cv.put("heavy_level", lvl);
        cv.put("heavy_result", res);
        db = this.getWritableDatabase();
        db.insertWithOnConflict("heavy", null, cv, SQLiteDatabase.CONFLICT_REPLACE);

        db.close();
    }*/


    public int getBonusValue(String typ) {
        int r = -1;
        t[0] = typ;
        db = this.getReadableDatabase();
        cursor = db.rawQuery("SELECT bonus_val FROM bonus WHERE bonus_type=? ", t);

        if (cursor.moveToFirst()) {

            r = cursor.getInt(cursor.getColumnIndexOrThrow("bonus_val"));
        }
        db.close();
        return r;

    }


    public void saveBonus(String typ, int val) {
        cv.clear();
        cv.put("bonus_type", typ);
        cv.put("bonus_val", val);
        db = this.getWritableDatabase();
        //   db.update("bonus", cv, "bonus_type=" + typ, null);
        db.insertWithOnConflict("bonus", null, cv, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }


}
