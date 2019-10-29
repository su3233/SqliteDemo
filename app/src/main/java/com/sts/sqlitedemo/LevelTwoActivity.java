package com.sts.sqlitedemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.sts.sqlitedemo.bean.Person;
import com.sts.sqlitedemo.db.DbManager;
import com.sts.sqlitedemo.db.MySqliteHelper;

import java.io.File;
import java.util.List;

public class LevelTwoActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LevelTwoActivity";
    private MySqliteHelper sqliteHelper;
    private ListView lvDbData;
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL = 0x001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_two);
        getPermissons();
        sqliteHelper = DbManager.getInstance(LevelTwoActivity.this);
        initView();
    }

    private void getPermissons() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE_EXTERNAL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_WRITE_EXTERNAL) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "请求通过", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "请求未通过", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {
        findViewById(R.id.bt_insert).setOnClickListener(this);
        findViewById(R.id.bt_quary).setOnClickListener(this);
        findViewById(R.id.bt_quary_api).setOnClickListener(this);
        findViewById(R.id.bt_insert_trans).setOnClickListener(this);

        lvDbData = findViewById(R.id.lv_all_db);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "info.db";
        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = database.rawQuery("select * from person", null);
        //Context context,int layout,Cursor c,String[] from,int[] to,int flags
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.item_cursor_lv, cursor,
                new String[]{Constant.PERSON_ID, Constant.PERSON_NAME, Constant.PERSON_AGE}, new int[]{R.id.tv_id, R.id.tv_name, R.id.tv_age},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lvDbData.setAdapter(cursorAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_insert:
                createDb();
                break;

            case R.id.bt_quary:
                quarySql();
                break;

            case R.id.bt_quary_api:
                quarySqlAPI();
                break;

            case R.id.bt_insert_trans:
                insertTrans();
                break;
        }
    }

    private void insertTrans() {
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        //开启事务
        db.beginTransaction();
        for (int i = 70; i < 80; i++) {
            String sql = "insert into person values(" + i + ",'无极',20)";
            db.execSQL(sql);
        }
        //提交事务
        db.setTransactionSuccessful();
        db.endTransaction();
        //关闭事务
        db.close();
    }

    private void quarySqlAPI() {
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        //boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
        // String orderBy, String limit
        Cursor cursor = db.query(Constant.TABLE_NAME, null, Constant.PERSON_ID + ">?", new String[]{"10"}, null,
                null, Constant.PERSON_ID + " desc");
        List<Person> personList = DbManager.cursorToList(cursor);
        for (Person person : personList) {
            Log.e(TAG, "quarySql: " + person.toString());
        }
        db.close();
    }

    private void quarySql() {
        SQLiteDatabase writableDatabase = sqliteHelper.getWritableDatabase();
        String sql = "select * from " + Constant.TABLE_NAME;
        Cursor cursor = DbManager.quaryRawQuary(writableDatabase, sql, null);
        List<Person> personList = DbManager.cursorToList(cursor);
        for (Person person : personList) {
            Log.e(TAG, "quarySql: " + person.toString());
        }
        writableDatabase.close();
    }

    /**
     * 插入数据
     */
    private void createDb() {
        SQLiteDatabase writableDatabase = sqliteHelper.getWritableDatabase();
        for (int i = 1; i < 70; i++) {
            String sql = "insert into person values(" + i + ",'张三" + i + "',20)";
            writableDatabase.execSQL(sql);
        }
        writableDatabase.close();
    }
}
