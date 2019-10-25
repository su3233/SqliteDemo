package com.sts.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sts.sqlitedemo.db.DbManager;
import com.sts.sqlitedemo.db.MySqliteHelper;

public class MainActivity extends AppCompatActivity {
    private MySqliteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = DbManager.getInstance(this);
    }

    /**
     * 创建和打开数据库
     *
     * @param view
     */
    public void createDb(View view) {
        SQLiteDatabase db = helper.getReadableDatabase();
    }

    public void insertData(View view) {
        SQLiteDatabase db = helper.getReadableDatabase();
        for (int i = 0; i < 10; i++) {
            String sql = "insert into person values(" + i + ",'zhangsan'" + i + ",20)";
            DbManager.exexSQL(db, sql);
        }
        db.close();
    }

    public void updateData(View view) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String updateSql = "update person set name='xiaoming' where _id=2";
        DbManager.exexSQL(db, updateSql);
        db.close();
    }

    public void deleteData(View view) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String deleteSql = "delete from person where _id=3";
        DbManager.exexSQL(db, deleteSql);
        db.close();
    }

    public void clickApi(View view) {
        SQLiteDatabase db;
        switch (view.getId()) {
            case R.id.bt_insert_api:
                db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("_id", 11);
                values.put(Constant.PERSON_NAME, "xiaowu");
                values.put("age", 22);
                long result = db.insert("person", null, values);
                if (result > 0) {
                    Toast.makeText(MainActivity.this, "插入数据成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "插入数据失败", Toast.LENGTH_SHORT).show();
                }
                db.close();
                break;
            case R.id.bt_update_api:
                db = helper.getWritableDatabase();
                ContentValues updateValues = new ContentValues();
                updateValues.put(Constant.PERSON_NAME, "xiaoqi");
                int count = db.update("person", updateValues, "_id=6", null);
//                int count = db.update("person", updateValues, "_id=?", new String[]{"3"});
                if (count > 0) {
                    Toast.makeText(MainActivity.this, "修改数据成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "修改数据失败", Toast.LENGTH_SHORT).show();
                }
                db.close();
                break;
            case R.id.bt_delete_api:
                db = helper.getWritableDatabase();
                int count2 = db.delete("person",  "_id=?", new String[]{"8"});
                if (count2 > 0) {
                    Toast.makeText(MainActivity.this, "删除数据成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "删除数据失败", Toast.LENGTH_SHORT).show();
                }
                db.close();
                break;
        }
    }
}
