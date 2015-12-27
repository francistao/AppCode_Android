package com.tc.dream.lovecalendar.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dream on 15/11/19.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private final static int VERSION = 1;
    private final static String SCHEDULE = "CREATE TABLE IF NOT EXISTS schedule(scheduleID integer primary key autoincrement,scheduleTypeID integer,remindID integer,scheduleContent text,scheduleDate text)";
    private final static String SCHEDULEDATA = "CREATE TABLE IF NOT EXISTS scheduletagdate(tagID integer primary key autoincrement,year integer,month integer,day integer,scheduleID integer)";

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        this(context, name, null, VERSION);
    }

    public DBOpenHelper(Context context, String name) {
        this(context, name, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SCHEDULE);
        db.execSQL(SCHEDULEDATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS schedule");
        db.execSQL("DROP TABLE IF EXISTS scheduletagdate");
        onCreate(db);
    }
}
