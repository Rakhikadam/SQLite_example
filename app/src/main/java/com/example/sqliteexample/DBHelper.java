package com.example.sqliteexample;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static String DB_Name = "contactDB";
    public static int DB_Version = 1;

    public DBHelper(@Nullable Context context) {

        super(context, DB_Name,null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE contacttable(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,number TEXT)");

    }
    public void addContact(String name , String number){
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("number",number);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("contacttable",null,values);
        db.close();
    }
    @SuppressLint("Range")
    public List<Contact> getallcontacts(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM contacttable";
        Cursor cursor = db.rawQuery(query , new String[]{});
        List<Contact>list = new ArrayList<>();

        if (cursor.moveToFirst()){
            do {
                 String id = cursor.getString(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String number = cursor.getString(cursor.getColumnIndex("number"));
                Contact contact = new Contact(name,number,id);
                list.add(contact);

            }
            while (cursor.moveToNext());

        }

        return list;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
