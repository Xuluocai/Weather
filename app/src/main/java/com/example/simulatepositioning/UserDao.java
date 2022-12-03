package com.example.simulatepositioning;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.*;

public class UserDao {
    private DatabaseHelper dbhelper;
    private SQLiteDatabase db;

    UserDao(Context context){
        dbhelper=new DatabaseHelper(context);
    }

    public void insert(String city){
        ContentValues values =new ContentValues();
        values.put("city",city);
        db=dbhelper.getWritableDatabase();
        db.insert("citys",null,values);
        db.close();
    }


    public void update(String city){
        db=dbhelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("city",city);
        db.update("citys",values,"city=?",new String[]{String.valueOf(city)});
        db.close();
    }


    public void delete(String city){
        db=dbhelper.getWritableDatabase();
        db.delete("citys","city=?",new String[]{city});
        db.close();
    }

    public List<String> getAll(){
        List<String> city=new ArrayList<>();
        db=dbhelper.getReadableDatabase();
        Cursor cursor = db.query("citys",new String[]{"id","city"},null,
                null,null,null,null);
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                System.out.println(cursor.getString(1));
                city.add(cursor.getString(1)+"");
            }
        }
        return city;
    }

    public List<String> query(String city){
        List<String> users=new ArrayList<>();
        db=dbhelper.getReadableDatabase();
        Cursor cursor = db.query("citys",new String[]{"city"},"city=?",
                new String[]{city},null,null,null);
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                String c=cursor.getString(0);
                users.add(c);
            }
        }
        return users;
    }
}
