package com.example.a20200611_2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

//author: 蓝一潇 20714899 电子1701

public class Activity_5 extends AppCompatActivity {

    private ListView listview;
    SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity5);
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"/WORKTASK.DB", null);

        final List<String> listdata = new ArrayList<String>();

        Cursor cursor = db.rawQuery("SELECT * FROM TASK WHERE COMP='y' AND OUTDATE='n' ", null);
//            SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.list_item, cursor, new String[]{"TASK"}, new int[] {R.id.list}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
//            listview.setAdapter(adapter);
        System.out.println("here");
//            insertData(db, "浇花", "HURRY");
//            insertData(db, "喝水", "HURRY");
        while (cursor.moveToNext()){
//                System.out.println(cursor.getString(cursor.getColumnIndex("TASK")));
            listdata.add(cursor.getString(cursor.getColumnIndex("TASK")));
        }

        listview = (ListView)findViewById(R.id.list_finish);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item,listdata);//listdata和str均可 //layout应该是每一个item的布局，而list则是在主页上，list的布局。
        listview.setAdapter(arrayAdapter);


    }


}
