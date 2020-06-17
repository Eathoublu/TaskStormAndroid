package com.example.a20200611_2;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class sqlhandler extends SQLiteOpenHelper {
    public sqlhandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE TASK(ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK CHAR(255), DESP CHAR(255))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        System.out.println("upgraded!");
    }
}
