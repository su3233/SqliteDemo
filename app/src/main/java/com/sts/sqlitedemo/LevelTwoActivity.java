package com.sts.sqlitedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sts.sqlitedemo.bean.Person;
import com.sts.sqlitedemo.db.DbManager;
import com.sts.sqlitedemo.db.MySqliteHelper;

import java.util.List;

public class LevelTwoActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LevelTwoActivity";
    private MySqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_two);
        sqliteHelper = DbManager.getInstance(LevelTwoActivity.this);
        initView();
    }

    private void initView() {
        findViewById(R.id.bt_insert).setOnClickListener(this);
        findViewById(R.id.bt_quary).setOnClickListener(this);
        findViewById(R.id.bt_quary_api).setOnClickListener(this);
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
        }
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
        for (int i = 1; i < 30; i++) {
            String sql = "insert into person values(" + i + ",'张三" + i + "',20)";
            writableDatabase.execSQL(sql);
        }
        writableDatabase.close();
    }
}
