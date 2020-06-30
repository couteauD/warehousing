package com.example.warehousing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.draw.ImageResDrawFormat;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.utils.DensityUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderPickingActivity extends AppCompatActivity {

    private Context context;
    private Toolbar toolbar;
    private LinearLayout linearLayoutCheck;
    private EditText editTextID;
    private ImageButton imageButtonCheck;
    private SmartTable table;
    private TableData tableData;
    private List<Order> list = new ArrayList<>();

    private int count;
    private String clothingID,location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_picking);
        context = getApplicationContext();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        linearLayoutCheck = findViewById(R.id.linearLayout_check);
        editTextID = findViewById(R.id.editText_ID);
        imageButtonCheck = findViewById(R.id.imageButton_check);
        table = findViewById(R.id.query_table);

        //表格列
        final Column<String> IDColumn = new Column<>("订购产品", "ID");
        final Column<Integer> countColumn = new Column<>("数量", "count");
        int size = DensityUtils.dp2px(context,15); //指定图标大小
        final Column<Boolean> stateColumn = new Column<>("状态", "state", new ImageResDrawFormat<Boolean>(size,size) {
            @Override
            protected Context getContext() {
                return OrderPickingActivity.this;
            }

            @Override
            protected int getResourceID(Boolean aBoolean, String value, int position) {
                if(aBoolean) {
                    return R.drawable.check;
                }
                return 0;
            }
        });
        init();
        tableData = new TableData<Order>("订单0001",list, IDColumn,countColumn,stateColumn);
        table.setTableData(tableData);
        table.getConfig().setShowXSequence(false);


        imageButtonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clothingID = editTextID.getText().toString();
                query(clothingID);
                new AlertDialog.Builder(OrderPickingActivity.this).setTitle("产品位置")
                        .setMessage("服装ID："+clothingID+"\n"+"库存数量(件): "+count+"\n"+"库存位置："+location)
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                    for(int i=0;i<list.size();i++){
                                        if(list.get(i).ID.equals(clothingID)){
                                            list.get(i).state = true;
                                            break;
                                        }
                                    }
                                    table.notifyDataChanged();
                            }
                        }).show();
            }
        });
    }

    private void query(String clothingID) {
        SQLiteDatabase db = SQLiteDB.getInstance(OrderPickingActivity.this).getDb();
        Cursor cursor = db.rawQuery("select * from Bale where ID ="+"'"+clothingID+"'",null);
        if (cursor.moveToFirst()){
            do{
                count = cursor.getInt(cursor.getColumnIndex("count"));
                location = cursor.getString(cursor.getColumnIndex("rack")) + cursor.getInt(cursor.getColumnIndex("location"));
            }while (cursor.moveToNext());
        }

    }

    private void init() {
        Order order1 = new Order();
        order1.ID = "0df1a700-2904-9b2d-a888-3535958ff3b3";
        order1.count = 1;
        order1.state = false;
        list.add(order1);

        Order order2 = new Order();
        order2.ID = "fc25a168-bb33-f997-6572-8655f829361c";
        order2.count = 1;
        order2.state = false;
        list.add(order2);

        Order order3 = new Order();
        order3.ID = "3020117f-7d45-32d5-1260-25433e829d49";
        order3.count = 1;
        order3.state = false;
        list.add(order3);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Toolbar返回响应
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class Order{
        private String ID;
        private int count;
        private Boolean state;
    }
}
