package com.example.a20200611_2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

//author: 蓝一潇 20714899 电子1701

public class Activity_4 extends AppCompatActivity
{

    private ListView listview;
    SQLiteDatabase db = null;
    int unfinish = 0;
    int outdate = 0;
    int all = 0;
    TextView tv_finish = null;
    TextView tv_outdate = null;
    TextView tv_all = null;
    ProgressBar pb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity4);

        pb = findViewById(R.id.pb);
        tv_finish = findViewById(R.id.numfinish);
        tv_all = findViewById(R.id.numall);
        tv_outdate = findViewById(R.id.numoutdate);

        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"/WORKTASK.DB", null);

        final List<String> listdata = new ArrayList<String>();

        Cursor cursor = db.rawQuery("SELECT * FROM TASK ", null);
//            SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.list_item, cursor, new String[]{"TASK"}, new int[] {R.id.list}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
//            listview.setAdapter(adapter);
        System.out.println("here");
//            insertData(db, "浇花", "HURRY");
//            insertData(db, "喝水", "HURRY");
        while (cursor.moveToNext()){
//                System.out.println(cursor.getString(cursor.getColumnIndex("TASK")));
            listdata.add(cursor.getString(cursor.getColumnIndex("TASK")));
            all += 1;
        }


        cursor = db.rawQuery("SELECT * FROM TASK WHERE COMP='n' ", null);
//            SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.list_item, cursor, new String[]{"TASK"}, new int[] {R.id.list}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
//            listview.setAdapter(adapter);
        System.out.println("here");
//            insertData(db, "浇花", "HURRY");
//            insertData(db, "喝水", "HURRY");
        while (cursor.moveToNext()){
//                System.out.println(cursor.getString(cursor.getColumnIndex("TASK")));
            listdata.add(cursor.getString(cursor.getColumnIndex("TASK")));
            unfinish += 1;
        }

        cursor = db.rawQuery("SELECT * FROM TASK WHERE OUTDATE='y'", null);
//            SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.list_item, cursor, new String[]{"TASK"}, new int[] {R.id.list}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
//            listview.setAdapter(adapter);
        System.out.println("here");
//            insertData(db, "浇花", "HURRY");
//            insertData(db, "喝水", "HURRY");
        while (cursor.moveToNext()){
//                System.out.println(cursor.getString(cursor.getColumnIndex("TASK")));
            listdata.add(cursor.getString(cursor.getColumnIndex("TASK")));
            outdate += 1;
        }

        tv_finish.setText(Integer.valueOf(all-unfinish).toString());
        tv_outdate.setText(Integer.valueOf(outdate).toString());
        tv_all.setText(Integer.valueOf(all).toString());

        pb.setProgress((int)((all-unfinish+0.0)/all*100));




    }
}
