package com.sts.sqlitedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.camera2.DngCreator;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.sts.sqlitedemo.adapter.PageOffsetAdapter;
import com.sts.sqlitedemo.bean.Person;
import com.sts.sqlitedemo.db.DbManager;
import com.sts.sqlitedemo.db.MySqliteHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SuTongsheng
 * @create 2019/10/29
 * @Describe 分页加载数据库
 */
public class OffsetPageActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL = 0x002;
    private ListView lvOffsetPage;
    private MySqliteHelper sqliteHelper;
    private int pageNum;
    private int pageSize = 20;
    private int totalNum;
    private int currentPage = 1;
    private List<Person> personList;
    private boolean isDivPage;//是否分页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offset_page);
        getPermissons();
        sqliteHelper = DbManager.getInstance(OffsetPageActivity.this);
        initView();
    }

    private void initView() {
        lvOffsetPage = findViewById(R.id.lv_offset_page);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "info.db";
        final SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        totalNum = DbManager.getDataCount(database, Constant.TABLE_NAME);
        pageNum = (int) Math.ceil(totalNum / (double) pageSize);
        if (currentPage == 1) {
            personList = DbManager.getListByCurrentPage(database, Constant.TABLE_NAME, currentPage, pageSize);
        }
        final PageOffsetAdapter pageOffsetAdapter = new PageOffsetAdapter(OffsetPageActivity.this, personList);
        lvOffsetPage.setAdapter(pageOffsetAdapter);

        lvOffsetPage.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (isDivPage && AbsListView.OnScrollListener.SCROLL_STATE_IDLE == scrollState) {
                    if (currentPage < pageNum) {
                        currentPage++;
                        personList.addAll(DbManager.getListByCurrentPage(database, Constant.TABLE_NAME, currentPage, pageSize));
                        pageOffsetAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisiableItem, int visibleItemCount, int totalItemCount) {
                isDivPage = ((firstVisiableItem + visibleItemCount) == totalItemCount);
            }
        });
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
}
