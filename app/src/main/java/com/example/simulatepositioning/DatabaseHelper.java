package com.example.simulatepositioning;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {//构造方法
        super(context, "citydb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table citys(id INTEGER primary key autoincrement,city varchar(30) not null)";
        try {
            db.execSQL("drop table if exists citys");//执行数据库命令
            db.execSQL(sql);//执行数据库命令
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion ) {

    }
}
