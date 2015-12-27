package com.tc.dream.books.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dream on 15/12/17.
 */
public class SqliteHelper extends SQLiteOpenHelper{

    private static String INFONAME;
    private static String NAME;
    private static int VERSION = 1;

    static
    {
        NAME = " table_notepad";
        INFONAME = "notepad.db";
    }

    public SqliteHelper(Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt)
    {
        super(paramContext, INFONAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,date TEXT,content TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
