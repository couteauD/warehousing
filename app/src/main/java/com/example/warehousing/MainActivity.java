package com.example.warehousing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BaleHelper baleHelper;
    private Button button_inventory_query;
    private Button button_order_picking;
    private Button button_stocking;
    private Button button_enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button_inventory_query = findViewById(R.id.button_inventory_query);
        button_order_picking = findViewById(R.id.button_order_picking);
        button_stocking = findViewById(R.id.button_stocking);
        button_enter = findViewById(R.id.button_enter);

        button_inventory_query.setOnClickListener(this);
        button_order_picking.setOnClickListener(this);
        button_stocking.setOnClickListener(this);
        button_enter.setOnClickListener(this);

        initDB();
    }

    private void initDB() {
        SQLiteDatabase db = SQLiteDB.getInstance(MainActivity.this).getDb();
//        ContentValues values = new ContentValues();
//        values.put("number","001");
//        values.put("id","4a507db7-7c52-2c22-d6a6-77ade48625bd");
//        values.put("rack","A");
//        values.put("location","1");
//        values.put("count","8");
//        db.insert("Bale",null,values);
//        values.clear();
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.button_inventory_query:
                intent = new Intent(MainActivity.this, InventoryQueryActivity.class);
                startActivity(intent);
                break;
            case R.id.button_order_picking:
                intent = new Intent(MainActivity.this, OrderPickingActivity.class);
                startActivity(intent);
                break;
            case R.id.button_stocking:
                intent = new Intent(MainActivity.this, StockingActivity.class);
                startActivity(intent);
                break;
            case R.id.button_enter:
                intent = new Intent(MainActivity.this, EnterActivity.class);
                startActivity(intent);
                break;
        }

    }
}