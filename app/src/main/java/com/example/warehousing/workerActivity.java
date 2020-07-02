package com.example.warehousing;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class workerActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;

    private Button buttonInventoryQuery,buttonStocking,buttonOrderPicking,buttonEnter,
                    buttonOrderImport,buttonReturn,buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonInventoryQuery = findViewById(R.id.button_inventoryQuery);
        buttonStocking = findViewById(R.id.button_stocking);
        buttonOrderPicking = findViewById(R.id.button_orderPicking);
        buttonEnter = findViewById(R.id.button_enter);
        buttonOrderImport = findViewById(R.id.button_orderImport);
        buttonReturn = findViewById(R.id.button_return);
        buttonLogout = findViewById(R.id.button_logout);

        buttonInventoryQuery.setOnClickListener(this);
        buttonStocking.setOnClickListener(this);
        buttonOrderPicking.setOnClickListener(this);
        buttonEnter.setOnClickListener(this);
        buttonOrderImport.setOnClickListener(this);
        buttonReturn.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.button_inventoryQuery:
                intent = new Intent(workerActivity.this,InventoryQueryActivity.class);
                startActivity(intent);
                break;
            case R.id.button_stocking:
                intent = new Intent(workerActivity.this,StockingActivity.class);
                startActivity(intent);
                break;
            case R.id.button_orderPicking:
                intent = new Intent(workerActivity.this,OrderPickingActivity.class);
                startActivity(intent);
                break;
            case R.id.button_enter:
                intent = new Intent(workerActivity.this,EnterActivity.class);
                startActivity(intent);
                break;
            case R.id.button_orderImport:
                intent = new Intent(workerActivity.this,OrderImportActivity.class);
                startActivity(intent);
                break;
            case R.id.button_return:
                intent = new Intent(workerActivity.this,RetrunActivity.class);
                startActivity(intent);
                break;
            case R.id.button_logout:
                new AlertDialog.Builder(workerActivity.this).setTitle("操作确认")
                        .setMessage("您确定要退出登录吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(workerActivity.this,MainActivity.class);
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