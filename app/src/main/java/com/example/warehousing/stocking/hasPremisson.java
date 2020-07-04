package com.example.warehousing.stocking;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.warehousing.SQLiteDB;
import com.example.warehousing.stocking.AbstractPermission;
import com.example.warehousing.stocking.StockingActivity;

import java.util.ArrayList;
import java.util.List;

class hasPremisson implements AbstractPermission {

    private List list = new ArrayList();
    @Override
    public List check(Context context, String jobNumber, String rack) {
        SQLiteDatabase db = SQLiteDB.getInstance(context).getDb();
        Cursor cursor = db.rawQuery("select * from Bale where rack ="+"'"+rack+"'",null);
        if (cursor.moveToFirst()){
            do{
                StockingActivity.Order order = new StockingActivity.Order();
                order.ID= cursor.getString(cursor.getColumnIndex("id"));
                order.count = cursor.getInt(cursor.getColumnIndex("count"));
                order.real = order.count;
                list.add(order);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
