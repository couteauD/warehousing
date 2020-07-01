package com.example.warehousing;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

class SQLiteDB {

    public static final String DB_NAME = "Bale_repertory.db";
    public static final int VERSION = 1;
    private static SQLiteDB sqLiteDB;

    public SQLiteDatabase getDb() {
        return db;
    }

    private SQLiteDatabase db;

    private SQLiteDB (Context context){
        BaleHelper baleHelper = new BaleHelper(context,DB_NAME,null,VERSION);
        db = baleHelper.getWritableDatabase();
    }

    public synchronized static SQLiteDB getInstance(Context context){
        if(sqLiteDB == null){
            sqLiteDB = new SQLiteDB(context);
        }
        return sqLiteDB;
    }
}
