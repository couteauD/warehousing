package com.example.warehousing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity{

    private EditText editTextJobNumber,editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextJobNumber = findViewById(R.id.editText_jobNumber);
        editTextPassword = findViewById(R.id.editText_password);
        buttonLogin = findViewById(R.id.button_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                String number = editTextJobNumber.getText().toString();

                SQLiteDatabase db = SQLiteDB.getInstance(MainActivity.this).getDb();
                Cursor cursor = db.rawQuery("select identity from User where jobNumber="+number ,null);
                if(cursor.moveToFirst()){
                    if(cursor.getString(cursor.getColumnIndex("identity")).equals("manager")){
                        intent = new Intent(MainActivity.this, ManagerActivity.class);
                    }else{
                        intent = new Intent(MainActivity.this, workerActivity.class);
                    }
                    startActivity(intent);
                }

                if(number.equals("0001")){
                    intent = new Intent(MainActivity.this, ManagerActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        });
    }


}