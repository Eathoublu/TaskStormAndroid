package com.example.a20200611_2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.media.MediaPlayer;
import android.content.pm.PackageManager;
import android.Manifest;

//import android.support.v4.content.ContextCompat;

import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.content.ContentUris;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.Toast;
//import android.widget.*;
import android.view.View.OnKeyListener;
import android.view.KeyEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

//author: 蓝一潇 20714899 电子1701


public class Activity_2 extends AppCompatActivity {

    //    private EditText et_phone;// 文本输入框中的手机号码
//    private EditText et_content;// 文本输入框中的短信内容
    private String tag = "OtherActivity";
    EditText et;
    EditText etd;
    Button button;
    Button button2;
    SQLiteDatabase db = null;
    CheckBox cba = null;
    CheckBox cbb = null;
    CheckBox cbc = null;
    Button play = null;
    Button pause = null;
    Button stop = null;



    private MediaPlayer mediaPlayer = new MediaPlayer();

    boolean add_music = false;
    String music_path = "";


    int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        cba = findViewById(R.id.aa);
        cbb = findViewById(R.id.bb);
        cbc = findViewById(R.id.cc);

        play = findViewById(R.id.play);
        stop = findViewById(R.id.stop);
        pause = findViewById(R.id.pause);





        et = (EditText)super.findViewById(R.id.edit_text);
        et.setOnKeyListener(new a());

        button = findViewById(R.id.btn_sav);
        //button点击跳转
        button.setOnClickListener(new myListener());

        button2 = findViewById(R.id.btn_add);
        button2.setOnClickListener(new addFile());

        play.setOnClickListener(new Play());
        pause.setOnClickListener(new Pause());
        stop.setOnClickListener(new Stop());


        etd = (EditText)super.findViewById(R.id.edit_text_des);

        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"/WORKTASK.DB", null);






    }

    private class a implements OnKeyListener{
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            //输入银行账号，用大字回显出来字符,每4个字符用横线隔开
//            switch (event.getAction()) {
//                case KeyEvent.ACTION_UP:             //键盘松开
//                    String sAccout=et.getText().toString();
//                    Toast.makeText(Activity_2.this, sAccout, Toast.LENGTH_SHORT).show();
//                case KeyEvent.ACTION_DOWN:          //键盘按下
//                    break;
//                case KeyEvent.KEYCODE_ENTER:
//                    Toast.makeText(Activity_2.this, et.getText(), Toast.LENGTH_SHORT).show();
//                case KeyEvent.ACTION_DOWN:
//                    break;
//            }
            if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)){
                Toast.makeText(Activity_2.this, et.getText(), Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    }



    class myListener implements CompoundButton.OnClickListener{
        @Override
        public void onClick(View view) {
            System.out.println("here");
//            Toast.makeText(Activity_2.this, "HERERE2222", Toast.LENGTH_SHORT).show();
            String add_record = "n";
            if (add_music){
                add_record = "y";
            }
            String hurry = "n";
            String repeat = "n";
            String del24 = "n";
            if (cba.isChecked()){
                hurry = "y";
                }
            if (cbb.isChecked()){
                repeat = "y";
            }
            if (cbc.isChecked()){
                del24 = "y";
            }
//            Toast.makeText(Activity_2.this, "HERERE", Toast.LENGTH_SHORT).show();
//            System.out.println("HERERE@@@@");
            insertData(db, et.getText().toString(), etd.getText().toString(), hurry, repeat, del24, music_path, add_record);
            Toast.makeText(Activity_2.this, "保存成功！即将返回～", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(Activity_2.this,MainActivity.class);
            Activity_2.this.startActivity(intent);
        }
    }

    class addFile implements CompoundButton.OnClickListener{
        @Override
        public void onClick(View view) {
            pickFile();
        }
    }

    public static boolean CopyFile2(String fromFile, String toFile) {

        try {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return true;

        } catch (Exception ex) {
            System.out.println("不成功！！！！！！"+ex);
            return false;
        }
    }

    public void _copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原bai文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }

    public boolean copyFile(String oldPath$Name, String newPath$Name) {
        try {
            File oldFile = new File(oldPath$Name);
            if (!oldFile.exists()) {
                System.out.println("--Method-- copyFile:  oldFile not exist.");
                return false;
            } else if (!oldFile.isFile()) {
                System.out.println("--Method-- copyFile:  oldFile not file.");
                return false;
            } else if (!oldFile.canRead()) {
                System.out.println("--Method-- copyFile:  oldFile cannot read.");
                return false;
            }

        /* 如果不需要打log，可以使用下面的语句
        if (!oldFile.exists() || !oldFile.isFile() || !oldFile.canRead()) {
            return false;
        }
        */

            FileInputStream fileInputStream = new FileInputStream(oldPath$Name);    //读入原文件
            FileOutputStream fileOutputStream = new FileOutputStream(newPath$Name);
            byte[] buffer = new byte[1024];
            int byteRead;
            while ((byteRead = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void insertData(SQLiteDatabase db, String task, String desp, String hurry, String repeat, String del24, String music, String add_record){
        db.execSQL("INSERT INTO TASK (TASK, DESP, HURRY, REPEAT, DEL24, MUSIC, COMP, OUTDATE, ADDRECORD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", new String[]{task, desp, hurry, repeat, del24, music, "n", "n", add_record});
        System.out.println("insert a row!");
    }

    // 打开系统的文件选择器
    public void pickFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        this.startActivityForResult(intent, REQUEST_CODE); //REQUEST_CODE为自定义，随便给的
    }

    // 获取文件的真实路径
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            // 用户未选择任何文件，直接返回
            return;
        }
        Uri uri = data.getData(); // 获取用户选择文件的URI
        // 通过ContentProvider查询文件路径
        music_path = uri.toString();
        Toast.makeText(Activity_2.this, music_path, Toast.LENGTH_SHORT).show();
        initMediaPlayer();
//        System.out.println("HERE INIT OK");
//        if(ContextCompat.checkSelfPermission(Activity_2.this, Manifest.permission.
//                WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(Activity_2.this, new String[] {
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//            }, 1);
//        } else {
//            initMediaPlayer(); //初始化MediaPlayer
//        }



    }

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
            mediaPlayer.setDataSource(music_path);
            mediaPlayer.prepare(); //让MediaPlayer进入到准备状态
            add_music = true;
            System.out.println("INIT HERE");
        } catch (Exception e) {
            System.out.println("HERE ERROR");
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initMediaPlayer();
                } else {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

}