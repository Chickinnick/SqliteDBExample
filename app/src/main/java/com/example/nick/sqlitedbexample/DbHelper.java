package com.example.nick.sqlitedbexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

public class DbHelper extends SQLiteOpenHelper {

    public static final String TAG = "Tag";



    public DbHelper(Context context) {
        super(context, "MyDb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        Log.d("MyTag", "---Database Created ---");

        db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "email text" + ");");
    }
        //setTextView("database is created");


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public static String setTextView(String text){
        return text;
    }
}
