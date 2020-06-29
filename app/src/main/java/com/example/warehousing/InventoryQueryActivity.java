package com.example.warehousing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.core.SmartTable;

import java.util.ArrayList;
import java.util.List;

public class InventoryQueryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout linearLayoutSearch;
    private EditText editTextID;
    private ImageButton imageButtonSearch;
    private SmartTable table;
    private List<UserInfo> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_query);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        linearLayoutSearch = findViewById(R.id.linearLayout_search);
        editTextID = findViewById(R.id.editText_ID);
        imageButtonSearch = findViewById(R.id.imageButton_search);
        table = findViewById(R.id.query_table);

        initTable();

    }

    private void initTable() {
        UserInfo userInfo = new UserInfo();
        userInfo.ID="4a507db7-7c52-2c22-d6a6-77ade48625bd";
        userInfo.count=1;
        userInfo.location="A1";
        list.add(0,userInfo);
        table.setData(list);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Toolbar返回响应
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @com.bin.david.form.annotation.SmartTable(name="库存查询结果")
    public class UserInfo {
        @SmartColumn(id =1,name = "服装ID")
        private String ID;
        @SmartColumn(id=2,name="库存数量")
        private int count;
        @SmartColumn(id=3,name="库存位置")
        private String location;
    }

}
