package com.example.warehousing;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class OrderHelper extends SQLiteOpenHelper {

    public static final String CREATE_ORDER ="create table newOrder ("
            +"number integer PRIMARY KEY AUTOINCREMENT, "
            +"id String, "
            +"count interger)";

    private Context mContext;

    public OrderHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ORDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
