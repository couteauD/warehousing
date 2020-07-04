package com.example.warehousing;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.warehousing.stocking.StockingActivity;

public class ManagerActivity extends AppCompatActivity implements View.OnClickListener{


    private Toolbar toolbar;

    private Button buttonInventoryQuery,buttonStocking,buttonOrderQuery,buttonUsers,buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonInventoryQuery = findViewById(R.id.button_inventoryQuery);
        buttonStocking = findViewById(R.id.button_stocking);
        buttonOrderQuery = findViewById(R.id.button_orderQuery);
        buttonUsers = findViewById(R.id.button_users);
        buttonLogout = findViewById(R.id.button_logout);

        buttonInventoryQuery.setOnClickListener(this);
        buttonStocking.setOnClickListener(this);
        buttonOrderQuery.setOnClickListener(this);
        buttonUsers.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.button_inventoryQuery:
                intent = new Intent(ManagerActivity.this,InventoryQueryActivity.class);
                startActivity(intent);
                break;
            case R.id.button_stocking:
                intent = new Intent(ManagerActivity.this, StockingActivity.class);
                startActivity(intent);
                break;
            case R.id.button_orderQuery:
                intent = new Intent(ManagerActivity.this,OrderImportActivity.class);
                startActivity(intent);
                break;
            case R.id.button_users:
                intent = new Intent(ManagerActivity.this,UsersActivity.class);
                startActivity(intent);
                break;
            case R.id.button_logout:
                new AlertDialog.Builder(ManagerActivity.this).setTitle("操作确认")
                        .setMessage("您确定要退出登录吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ManagerActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
        }
    }
}