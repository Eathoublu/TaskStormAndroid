package com.example.a20200611_2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.ActionMenuView;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;

//author: 蓝一潇 20714899 电子1701

public class MainActivity extends AppCompatActivity {

//    private String[] data = {"暹罗猫", "布偶猫", "折耳猫", "短毛猫", "波斯猫", "蓝猫", "森林猫", "孟买猫","缅因猫","埃及猫","伯曼猫","缅甸猫","新加坡猫","美国短尾猫","巴厘猫"};
    private ListView listview;
//    private sqlhandler sh = new sqlhandler(this, "TASK.DB", null, 1);
    SQLiteDatabase db = null;
    FloatingActionButton fab2;



    @Override
    public File getFilesDir() {
        return super.getFilesDir();
    }
    //    private SQLiteDatabase db = sh.getWritableDatabase();
//    SQLiteDatabase db = sh.getWritableDatabase()
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.profile_author) {
            Toast.makeText(MainActivity.this, "作者：蓝一潇\n20174899\n电子1701", Toast.LENGTH_SHORT).show();
            return true;
        } else if(id == R.id.finish){
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,Activity_5.class);
//                MainActivity.this.startActivity(intent);
            startActivity(intent);
        }else if(id == R.id.outdate){
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,Activity_6.class);
//                MainActivity.this.startActivity(intent);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    //上面两个方法用于生成右上角菜单

    private void insertData(SQLiteDatabase db, String task, String desp){
        db.execSQL("INSERT INTO TASK (TASK, DESP, HURRY, REPEAT, DEL24, MUSIC, ADDRECORD, COMP, OUTDATE) VALUES (?, ?)", new String[]{task, desp});
        System.out.println("insert a row!");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(R.layout.content_main);

        listview = (ListView)findViewById(R.id.list);

//        String[] str = {"上海   ","北京","天津","江苏","河南","西藏","新疆","湖南","湖北"};
        final List<String> listdata = new ArrayList<String>();
////        listdata.add("上海");
////        listdata.add("北京");
////        listdata.add("天津");
////        listdata.add("江苏");
//        for (int i=0;i<str.length;i++){
//            listdata.add(str[i]);
//        }


        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"/WORKTASK.DB", null);

        try{
            Cursor cursor = db.rawQuery("SELECT * FROM TASK WHERE COMP='n' AND OUTDATE='n' ", null);
//            SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.list_item, cursor, new String[]{"TASK"}, new int[] {R.id.list}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
//            listview.setAdapter(adapter);
            System.out.println("here");
//            insertData(db, "浇花", "HURRY");
//            insertData(db, "喝水", "HURRY");
            while (cursor.moveToNext()){
//                System.out.println(cursor.getString(cursor.getColumnIndex("TASK")));
                listdata.add(cursor.getString(cursor.getColumnIndex("TASK")));
            }
        }catch (Exception e){
            System.out.println("ERROR WHEN CREATE"+e);
            String sql_create = "CREATE TABLE TASK(ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK VARCHAR(255), DESP VARCHAR(255), HURRY VARCHAR(10), REPEAT VARCHAR(10), DEL24 VARCHAR(10), MUSIC VARCHAR(255), ADDRECORD VARCHAR(10), COMP VARCHAR(10), OUTDATE VARCHAR(10))";
            db.execSQL(sql_create);
            Cursor cursor = db.rawQuery("SELECT * FROM TASK", null);
//            SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.list_item, cursor, new String[]{"TASK"}, new int[] {R.id.list}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
//            listview.setAdapter(adapter);
            System.out.println("here");
            while (cursor.moveToNext()){
                System.out.println(cursor.getString(cursor.getColumnIndex("TASK")));
                listdata.add(cursor.getString(cursor.getColumnIndex("TASK")));
            }
        }
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item,listdata);//listdata和str均可 //layout应该是每一个item的布局，而list则是在主页上，list的布局。
        listview.setAdapter(arrayAdapter);







//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                MainActivity.this, android.R.layout.simple_list_item_1, data);
//        ((ListView) findViewById(R.id.list)).setAdapter(adapter);

//
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String sqlInsertData = "INSERT INTO TASK (TASK, DESP) VALUES ('浇花', '不要再忘记了')";
//                db.execSQL(sqlInsertData);
//                System.out.println("添加成功！");
                Snackbar.make(view, "添加新的任务～", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,Activity_2.class);
//                MainActivity.this.startActivity(intent);
                startActivity(intent);

    }});
        fab2 = findViewById(R.id.fab_2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String sqlInsertData = "INSERT INTO TASK (TASK, DESP) VALUES ('浇花', '不要再忘记了')";
//                db.execSQL(sqlInsertData);
//                System.out.println("添加成功！");
                Snackbar.make(view, "任务完成统计～", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,Activity_4.class);
//                MainActivity.this.startActivity(intent);
                startActivity(intent);

            }});



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Cat cat = cats.get(position);
                //Toast.makeText(MainActivity.this, cat.getName(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "进入", Toast.LENGTH_SHORT).show();
//                arrayAdapter.add("告知");

                Intent intent = new Intent();
                intent.setClass(MainActivity.this,Activity_3.class);
                intent.putExtra("name", listdata.get(position));
//                MainActivity.this.startActivity(intent);
                startActivity(intent);
            }
        });





}
}