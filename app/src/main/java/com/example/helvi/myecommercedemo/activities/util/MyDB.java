package com.example.helvi.myecommercedemo.activities.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
* DataBase class to create table and all
* other database operation
* @author:Helvi
* */
public class MyDB extends SQLiteOpenHelper {

    public static final String DATABASE="productinfo";
    public static final String TABLE="cart";
    public static final String PID="id";
    public static final String PNAME="name";
    public static final String PQUANTITY="quantity";
    public static final String PRIZE="prize";
    public static final String IMAGE="image";
    public static final int VERSION=1;


    public MyDB(Context context) {
        super(context, DATABASE, null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE + "("
            + PID + " TEXT PRIMARY KEY," + PNAME + " TEXT,"
            + PQUANTITY + " TEXT," + PRIZE + " TEXT, " + IMAGE + " TEXT"+ ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
       db.execSQL("DROP TABLE IF EXITS" + TABLE);
        onCreate(db);
    }



}
