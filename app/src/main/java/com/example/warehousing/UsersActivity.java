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
import android.os.ConditionVariable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.core.SmartTable;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private EditText editTextJobNumber;
    private Button buttonManager,buttonWorker;
    private SmartTable table;
    private List<UserInfo> list = new ArrayList<>();
    private int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        editTextJobNumber = findViewById(R.id.editText_jobNumber);
        buttonManager = findViewById(R.id.button_manager);
        buttonWorker = findViewById(R.id.button_worker);
        table = findViewById(R.id.table_users);

        buttonManager.setOnClickListener(this);
        buttonWorker.setOnClickListener(this);

        init();
    }

    private void init() {
        SQLiteDatabase db = SQLiteDB.getInstance(UsersActivity.this).getDb();
        Cursor cursor = db.rawQuery("select * from User " ,null);
        if (cursor.moveToFirst()){
            do{
                String jobnumber = cursor.getString(cursor.getColumnIndex("jobNumber"));
                String identity = cursor.getString(cursor.getColumnIndex("identity"));

                UserInfo userInfo = new UserInfo();
                userInfo.jobNumber = jobnumber;
                userInfo.identity = identity;
                list.add(userInfo);
            }while (cursor.moveToNext());
        }

        if(cursor.moveToLast()){
            number = cursor.getInt(cursor.getColumnIndex("number"));
        }
        table.setData(list);
        table.getConfig().setShowXSequence(false);

    }

    @Override
    public void onClick(View v) {
        final String number = editTextJobNumber.getText().toString();
        SQLiteDatabase db = SQLiteDB.getInstance(UsersActivity.this).getDb();
        ContentValues values = new ContentValues();
        switch (v.getId()){
            case R.id.button_manager:
                values.put("number",this.number+1);
                values.put("jobNumber",number);
                values.put("password","123456");
                values.put("identity","manager");
                db.insert("User",null,values);
                values.clear();

                new AlertDialog.Builder(UsersActivity.this).setTitle("消息提醒")
                        .setMessage("新增管理员成功")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserInfo userInfo = new UserInfo();
                                userInfo.jobNumber = number;
                                userInfo.identity = "manager";
                                list.add(userInfo);
                                table.notifyDataChanged();
                            }
                        }).show();
                break;
            case R.id.button_worker:
                values.put("number",this.number+1);
                values.put("jobNumber",number);
                values.put("password","123456");
                values.put("identity","worker");
                db.insert("User",null,values);
                values.clear();

                new AlertDialog.Builder(UsersActivity.this).setTitle("消息提醒")
                        .setMessage("新增工作人员成功")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserInfo userInfo = new UserInfo();
                                userInfo.jobNumber = number;
                                userInfo.identity = "worker";
                                list.add(userInfo);
                                table.notifyDataChanged();
                            }
                        }).show();
                break;
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

    @com.bin.david.form.annotation.SmartTable(name="用户信息")
    public class UserInfo {
        @SmartColumn(id =1,name = "工号")
        private String jobNumber;
        @SmartColumn(id=2,name="身份")
        private String identity;
    }
}