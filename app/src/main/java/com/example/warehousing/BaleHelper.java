package com.example.warehousing;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class BaleHelper extends SQLiteOpenHelper {

    public static final String CREATE_BALE ="create table Bale("
            +"number integer PRIMARY KEY AUTOINCREMENT, "
            +"id String, "
            +"rack String, "
            +"location integer, "
            +"count interger)";

    public static final String CREATE_ORDER ="create table newOrder ("
            +"number integer PRIMARY KEY AUTOINCREMENT, "
            +"orderID String, "
            +"clothingID String, "
            +"count interger)";


    private Context mContext;
    public BaleHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
            mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_BALE);
        db.execSQL(CREATE_ORDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
