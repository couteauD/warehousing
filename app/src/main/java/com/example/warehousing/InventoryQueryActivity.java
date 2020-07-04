package com.example.warehousing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.core.SmartTable;
import com.example.warehousing.init.Bale;
import com.example.warehousing.init.importBale;

import java.util.ArrayList;
import java.util.List;

public class InventoryQueryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout linearLayoutSearch;
    private EditText editTextID;
    private ImageButton imageButtonSearch;
    private SmartTable table;
    private Button buttonShowAll;
    private List<UserInfo> list = new ArrayList<>();
    private List<UserInfo> search = new ArrayList<>();

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
        buttonShowAll = findViewById(R.id.button_showall);

        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clothingID = editTextID.getText().toString();
                search(clothingID);
            }
        });

        buttonShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });

        importBale importBale = new importBale();
        initTable(importBale.importFile(InventoryQueryActivity.this));
        init();
    }

    private void init() {
        SQLiteDatabase db = SQLiteDB.getInstance(InventoryQueryActivity.this).getDb();
        Cursor cursor = db.rawQuery("select * from Bale",null);
        list.clear();
        if (cursor.moveToFirst()){
            do{
                UserInfo userInfo = new UserInfo();
                userInfo.ID= cursor.getString(cursor.getColumnIndex("id")) ;
                userInfo.count = cursor.getInt(cursor.getColumnIndex("count"));
                userInfo.location = cursor.getString(cursor.getColumnIndex("rack")) + cursor.getInt(cursor.getColumnIndex("location"));
                list.add(userInfo);
            }while (cursor.moveToNext());
        }
        table.setData(list);
        table.getConfig().setShowXSequence(false);

    }


    private void search(String clothingID) {
        SQLiteDatabase db = SQLiteDB.getInstance(InventoryQueryActivity.this).getDb();
        Cursor cursor = db.rawQuery("select * from Bale where ID ="+"'"+clothingID+"'",null);
        list.clear();
        if (cursor.moveToFirst()){
            do{
                UserInfo userInfo = new UserInfo();
                userInfo.ID= clothingID ;
                userInfo.count = cursor.getInt(cursor.getColumnIndex("count"));
                userInfo.location = cursor.getString(cursor.getColumnIndex("rack")) + cursor.getInt(cursor.getColumnIndex("location"));
                search.add(userInfo);
            }while (cursor.moveToNext());
        }
        table.setData(search);
        table.getConfig().setShowXSequence(false);
        search.clear();
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

    private void initTable(List<Bale> Balelist) {
        List<UserInfo> Userlist = new ArrayList<>();
        SQLiteDatabase db = SQLiteDB.getInstance(InventoryQueryActivity.this).getDb();
        ContentValues values = new ContentValues();
        for(int i=0;i<Balelist.size();i++){
            String ID = Balelist.get(i).getID();
            int count = Balelist.get(i).getCount();
            String rack = Balelist.get(i).getRack() ;
            int location = Balelist.get(i).getLocation();

            if(rack.equals("A")){
                values.put("number",i);
            }else if(rack.equals("B")){
                values.put("number",i+12);
            }else if(rack.equals("C")){
                values.put("number",i+24);
            }else if(rack.equals("D")){
                values.put("number",i+36);
            }

            values.put("id",ID);
            values.put("rack",rack);
            values.put("location",location);
            values.put("count",count);
            db.insert("Bale",null,values);
            values.clear();

            UserInfo userInfo = new UserInfo();
            userInfo.ID = ID;
            userInfo.location= rack+location;
            userInfo.count = count;
            Userlist.add(userInfo);
        }
        table.setData(Userlist);
        table.getConfig().setShowXSequence(false);
    }

}
