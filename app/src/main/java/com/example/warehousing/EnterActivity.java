package com.example.warehousing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.core.SmartTable;

import java.util.ArrayList;
import java.util.List;

public class EnterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editTextID;
    private EditText editTextCount;
    private Button buttonCheckEnter;
    private SmartTable tableEnter;
    private List<UserInfo> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        editTextID = findViewById(R.id.editText_ID);
        editTextCount = findViewById(R.id.editText_count);
        buttonCheckEnter = findViewById(R.id.button_checkEnter);
        tableEnter = findViewById(R.id.enter_table);

        buttonCheckEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase db = SQLiteDB.getInstance(EnterActivity.this).getDb();
                        Cursor cursor = db.rawQuery("select number from Bale order by number" ,null);
                        int index = 0;
                        Boolean isEntered = false;
                        if (cursor.moveToFirst()){
                            do{
                                int i=cursor.getInt(cursor.getColumnIndex("number"));
                                if( i == index){
                                    index++;
                                } else{    //空货位
                                    enter(index);
                                    isEntered = true;
                                    break;
                                }
                            }while (cursor.moveToNext());
                        }
                        if(!isEntered){
                            enter(index);
                        }
                    }
                }).start();

                new AlertDialog.Builder(EnterActivity.this).setTitle("消息提醒")
                        .setMessage("入库成功")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tableEnter.setData(list);
                                tableEnter.getConfig().setShowXSequence(false);
                            }
                        }).show();
            }
        });
    }

    private void enter(int index){
        SQLiteDatabase db = SQLiteDB.getInstance(EnterActivity.this).getDb();
        ContentValues values = new ContentValues();
        values.put("number",index);

        String ID = editTextID.getText().toString();
        values.put("id",ID);

        String rack="A";
        if(index/12 == 1){
            rack = "B";
        }else if(index/12 == 2){
            rack = "C";
        }else if(index/12 == 3){
            rack = "D";
        }
        values.put("rack",rack);

        values.put("location",index % 12 + 1);

        int count = Integer.parseInt(editTextCount.getText().toString());
        values.put("count",count);

        db.insert("Bale",null,values);
        values.clear();

        UserInfo userInfo = new UserInfo();
        userInfo.ID = ID;
        userInfo.count = count;
        userInfo.location = rack + (index%12+1);
        list.add(userInfo);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Toolbar返回响应
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @com.bin.david.form.annotation.SmartTable(name="入库信息")
    public class UserInfo {
        @SmartColumn(id =1,name = "服装ID")
        private String ID;
        @SmartColumn(id=2,name="库存数量")
        private int count;
        @SmartColumn(id=3,name="库存位置")
        private String location;
    }
}