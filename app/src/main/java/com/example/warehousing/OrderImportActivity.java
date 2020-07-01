package com.example.warehousing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.core.SmartTable;

import java.util.ArrayList;
import java.util.List;

public class OrderImportActivity extends AppCompatActivity {

    private Context context;
    private Toolbar toolbar;
    private LinearLayout linearLayoutImport;
    private Button buttonExcel,buttonXml;
    private SmartTable tableOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_import);
        context = getApplicationContext();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        linearLayoutImport = findViewById(R.id.linearLayout_import);
        buttonExcel = findViewById(R.id.button_excel);
        buttonXml = findViewById(R.id.button_xml);
        tableOrder = findViewById(R.id.table_order);

        buttonExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importCSV importCSV =new importCSV();
                initTable(importCSV.importCSV(context));
            }
        });

        buttonXml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importXML importXML = new importXML();
                initTable(importXML.importXML(context));
            }
        });
    }

    private void initTable(List<ModuleOrder> Orderlist) {
        List<UserInfo> Userlist = new ArrayList<>();
        for(int i=0;i<Orderlist.size();i++){
            UserInfo userInfo = new UserInfo();
            userInfo.orderID = Orderlist.get(i).getOrderID();
            userInfo.clothingID = Orderlist.get(i).getClothingID();
            userInfo.count = Orderlist.get(i).getCount();
            Userlist.add(userInfo);
        }
        tableOrder.setData(Userlist);
        tableOrder.getConfig().setShowXSequence(false);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Toolbar返回响应
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @com.bin.david.form.annotation.SmartTable(name="订单")
    public class UserInfo {
        @SmartColumn(id=1,name="订单ID")
        private String orderID;
        @SmartColumn(id=2,name="服装ID")
        private String clothingID;
        @SmartColumn(id=3,name="订购数量")
        private int count;
    }

}