package com.example.warehousing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class RetrunActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textViewOrderID,textViewClothingID,textViewCount;
    private EditText editTextReason;
    private Button buttonRefer;

    private String orderID,clothingID;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrun);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        textViewOrderID = findViewById(R.id.textView_orderID);
        textViewClothingID = findViewById(R.id.textView_clothingID);
        textViewCount = findViewById(R.id.textView_count);
        editTextReason = findViewById(R.id.editText_reason);
        buttonRefer = findViewById(R.id.button_refer);

        initOrder();

        buttonRefer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //库存更新
                SQLiteDatabase db = SQLiteDB.getInstance(RetrunActivity.this).getDb();
                Cursor cursor = db.rawQuery("select * from Bale where id ="+clothingID,null);
                ContentValues values = new ContentValues();
                values.put("count",cursor.getInt(cursor.getColumnIndex("count"))+count);
                db.update("Bale",values,"id= ? ",new String[]{clothingID});
                values.clear();

                //订单列表更新
                SQLiteDatabase db1 = SQLiteDB.getInstance(RetrunActivity.this).getDb();
                db.delete("Bale","orderID= ? and clothingID= ?",new String[]{orderID,clothingID});

                //提醒
                new AlertDialog.Builder(RetrunActivity.this).setTitle("处理结果")
                        .setMessage("处理成功")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });
    }

    private void initOrder() {
        Random rand = new Random();
        int i = rand.nextInt(28);

        SQLiteDatabase db = SQLiteDB.getInstance(RetrunActivity.this).getDb();
        Cursor cursor = db.rawQuery("select * from newOrder where number ="+i,null);
        if (cursor.moveToFirst()){
                orderID = cursor.getString(cursor.getColumnIndex("orderID"));
                clothingID = cursor.getString(cursor.getColumnIndex("clothingID"));
                count = cursor.getInt(cursor.getColumnIndex("count"));

                //显示
                textViewOrderID.setText(orderID);
                textViewClothingID.setText(clothingID);
                textViewCount.setText(count+"");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Toolbar返回响应
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}