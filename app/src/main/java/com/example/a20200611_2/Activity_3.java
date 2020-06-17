package com.example.a20200611_2;

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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.*;
import android.view.View.OnKeyListener;
import android.view.KeyEvent;

import android.media.MediaPlayer;
import android.content.pm.PackageManager;



import java.io.File;

//author: 蓝一潇 20714899 电子1701

public class Activity_3 extends AppCompatActivity {
    private String tag = "OtherActivity";
    public TextView tv;
    public TextView tv2;
    private Button button;
    private Button button_del;
    private Button play;
    private Button pause;
    private Button stop;

    private String is_hurry;
    private String is_del24;
    private String is_daily;

    private TextView tv_hurry;
    private TextView tv_daily;
    private TextView tv_del24;

    private String music_path;

    private MediaPlayer mediaPlayer = new MediaPlayer();

    SQLiteDatabase db = null;
    String task = null;

    class Play implements CompoundButton.OnClickListener{
        @Override
        public void onClick(View view) {
            if (!mediaPlayer.isPlaying()){
                mediaPlayer.start();
                System.out.println("PLAY");
            }
        }
    }

    class Pause implements CompoundButton.OnClickListener{
        @Override
        public void onClick(View view) {
            if (mediaPlayer.isPlaying()){
                mediaPlayer.pause();
            }
        }
    }

    class Stop implements CompoundButton.OnClickListener{
        @Override
        public void onClick(View view) {
            if (mediaPlayer.isPlaying()){
                mediaPlayer.reset();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    private void initMediaPlayer() {
        try {
//            File file = new File(Environment.getExternalStorageDirectory(), "music.mp3");
//            File file = new File(music_path);
//            mediaPlayer.setDataSource(file.getPath()); //指定音频文件的路径

//            File f = new File(music_path);
//            if (!f.exists()){
//                Toast.makeText(Activity_3.this, "文件根本不存在！"+ music_path, Toast.LENGTH_SHORT).show();
//            }
            mediaPlayer.setDataSource(music_path);
            mediaPlayer.prepare(); //让MediaPlayer进入到准备状态
            System.out.println("INIT HERE");
        } catch (Exception e) {
            System.out.println("HERE ERROR");
            e.printStackTrace();
            Toast.makeText(Activity_3.this, "无法打开音频文件！", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artivity3);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        task = bundle.getString("name");
//        int idx = bundle.getInt("idx");
        Toast.makeText(Activity_3.this, task, Toast.LENGTH_SHORT).show();
        tv = findViewById(R.id.taskname);
        tv.setText(task);
        tv.setTextSize(40);
        tv2 = findViewById(R.id.taskdesc);
        tv2.setText("Loading...");
        tv2.setTextSize(15);
        button = findViewById(R.id.btn_comp);
        button_del = findViewById(R.id.btn_del);
        button.setOnClickListener(new Activity_3.myListener());
        button_del.setOnClickListener(new Activity_3.myListenerdel());

        tv_daily = findViewById(R.id.is_daily);
        tv_del24 = findViewById(R.id.is_del24);
        tv_hurry = findViewById(R.id.is_hurry);

        play = findViewById(R.id.play3);
        pause = findViewById(R.id.pause3);
        stop = findViewById(R.id.stop3);

        play.setOnClickListener(new Play());
        pause.setOnClickListener(new Pause());
        stop.setOnClickListener(new Stop());

        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"/WORKTASK.DB", null);
        getInfo(db, task);

        initMediaPlayer();

    }

    class myListener implements CompoundButton.OnClickListener{
        @Override
        public void onClick(View view) {
            updateData(db, task);
            Toast.makeText(Activity_3.this, "棒！即将返回～", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(Activity_3.this,MainActivity.class);
            startActivity(intent);
        }
    }

    class myListenerdel implements CompoundButton.OnClickListener{
        @Override
        public void onClick(View view) {
            deleteData(db, task);
            Toast.makeText(Activity_3.this, "不要轻易放弃！即将返回～", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(Activity_3.this,MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public File getFilesDir() {
        return super.getFilesDir();
    }

    private void deleteData(SQLiteDatabase db, String task){
        db.execSQL("DELETE FROM TASK WHERE TASK=?", new String[]{task});
        System.out.println("delete a row!");
    }

    private void updateData(SQLiteDatabase db, String task){
        db.execSQL("UPDATE TASK SET COMP=\"y\" WHERE TASK=?", new String[]{task});
        System.out.println("update a row!");
    }

    private void getInfo(SQLiteDatabase db, String task){
        String sql_find = "SELECT * FROM TASK WHERE TASK =\'"+task+"\'";
//        db.execSQL(sql_find);
        Cursor cursor = db.rawQuery(sql_find, null);
        System.out.println("HERE "+sql_find);
        while (cursor.moveToNext()){
            tv2.setText(cursor.getString(cursor.getColumnIndex("DESP")));
            music_path = cursor.getString(cursor.getColumnIndex("MUSIC"));
            is_hurry = cursor.getString(cursor.getColumnIndex("HURRY"));
            is_del24 = cursor.getString(cursor.getColumnIndex("DEL24"));
            is_daily = cursor.getString(cursor.getColumnIndex("REPEAT"));
            if (is_daily.equals("y")){
                tv_daily.setText("🍺每日重复");
                tv_daily.setTextSize(20);
            }
            if (is_hurry.equals("y")){
                tv_hurry.setText("🍺紧急");
                tv_hurry.setTextSize(20);
            }
            if (is_del24.equals("y")){
                tv_del24.setText("🍺24h后删除");
                tv_del24.setTextSize(20);
            }
            System.out.println("HERE2"+music_path);
        }

    }
}
