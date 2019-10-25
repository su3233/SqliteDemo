package com.sts.sqlitedemo.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sts.sqlitedemo.Constant;

/**
 * @author SuTs
 * @create 2019/10/21
 * @Describe
 */
public class MySqliteHelper extends SQLiteOpenHelper {
    private static final String TAG = "MySqliteHelper";

    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public MySqliteHelper(Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e(TAG, "onCreate: ");
        String sql = "create table person(_id Integer primary key,name varcahr(10),age Integer)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.e(TAG, "onUpgrade: ");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.e(TAG, "onOpen: ");
        super.onOpen(db);
    }
}
