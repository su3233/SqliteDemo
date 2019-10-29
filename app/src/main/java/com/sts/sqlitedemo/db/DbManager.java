package com.sts.sqlitedemo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.sts.sqlitedemo.Constant;
import com.sts.sqlitedemo.bean.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SuTs
 * @create 2019/10/21
 * @Describe
 */
public class DbManager {
    private static MySqliteHelper helper;
    private static final String TAG = "DbManager";

    public static MySqliteHelper getInstance(Context context) {
        if (helper == null) {
            synchronized (MySqliteHelper.class) {
                if (helper == null) {
                    helper = new MySqliteHelper(context);
                }
            }
        }
        return helper;
    }

    /**
     * 执行sql语句
     *
     * @param db
     * @param sql
     */
    public static void exexSQL(SQLiteDatabase db, String sql) {
        if (db != null) {
            if (!TextUtils.isEmpty(sql)) {
                db.execSQL(sql);
            }
        }
    }

    /**
     * 通过查询
     *
     * @param db
     * @param sql
     * @return
     */
    public static Cursor quaryRawQuary(SQLiteDatabase db, String sql, String[] selectionArgs) {
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(sql, selectionArgs);
        }
        return cursor;
    }

    public static List<Person> cursorToList(Cursor cursor) {
        List<Person> personList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex(Constant.PERSON_ID));
            String name = cursor.getString(cursor.getColumnIndex(Constant.PERSON_NAME));
            int age = cursor.getInt(cursor.getColumnIndex(Constant.PERSON_AGE));
            Person person = new Person(_id, name, age);
            personList.add(person);
        }
        return personList;
    }

    /**
     * 查询数据库中的数据个数
     *
     * @param db
     * @param tableName
     * @return
     */
    public static int getDataCount(SQLiteDatabase db, String tableName) {
        int count = 0;
        if (db != null) {
            Cursor cursor = db.rawQuery("select * from " + tableName, null);
            count = cursor.getCount();
        }
        Log.e(TAG, "quaryRawQuary: " + count);
        return count;
    }

    /**
     * 根据当前页码获取当前页数据
     *
     * @param db
     * @param tableName
     * @param currentPage
     * @return
     */
    public static List<Person> getListByCurrentPage(SQLiteDatabase db, String tableName, int currentPage, int pageSize) {
        Cursor cursor = null;
        int index = (currentPage - 1) * pageSize;
        if (db != null) {
            String sql = "select * from " + tableName + " limit ?,?";
            cursor = db.rawQuery(sql, new String[]{index + "", pageSize + ""});
        }
        return DbManager.cursorToList(cursor);
    }

}
