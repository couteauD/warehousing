package com.example.warehousing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_inventory_query;
    private Button button_order_picking;
    private Button button_stocking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button_inventory_query = findViewById(R.id.button_inventory_query);
        button_order_picking = findViewById(R.id.button_order_picking);
        button_stocking = findViewById(R.id.button_stocking);

        button_inventory_query.setOnClickListener(this);
        button_order_picking.setOnClickListener(this);
        button_stocking.setOnClickListener(this);
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
        }

    }
}