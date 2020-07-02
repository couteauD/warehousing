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
import android.widget.Button;
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
    private Button buttonFinish;
    private SmartTable table;
    private TableData tableData;
    private List<Order> list = new ArrayList<>();

    private int count;
    private String ID,location;
    private int index=0;

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
        buttonFinish = findViewById(R.id.button_finish);
        table = findViewById(R.id.query_table);

        //表格列
        final Column<String> orderIDColumn = new Column<>("订单ID","orderID");
        final Column<String> clothingIDColumn = new Column<>("产品ID", "clothingID");
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
        tableData = new TableData<Order>("拣货任务",list, orderIDColumn,clothingIDColumn,countColumn,stateColumn);
        table.setTableData(tableData);
        table.getConfig().setShowXSequence(false);


        imageButtonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID = editTextID.getText().toString();
                query(ID);
                new AlertDialog.Builder(OrderPickingActivity.this).setTitle("产品位置")
                        .setMessage("服装ID："+ID+"\n"+"库存数量(件): "+count+"\n"+"库存位置："+location)
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                    for(int i=0;i<list.size();i++){
                                        if(list.get(i).clothingID.equals(ID)){
                                            list.get(i).state = true;
                                            break;
                                        }
                                    }
                                    table.notifyDataChanged();
                            }
                        }).show();
            }
        });

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                index++;
                init();
                table.notifyDataChanged();
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
        SQLiteDatabase db = SQLiteDB.getInstance(OrderPickingActivity.this).getDb();
        Cursor cursor = db.rawQuery("select * from newOrder where number >="+index,null);
        int sum = 0;
        if (cursor.moveToFirst()){
            do{
                Order order = new Order();
                order.orderID = cursor.getString(cursor.getColumnIndex("orderID"));
                order.clothingID = cursor.getString(cursor.getColumnIndex("clothingID"));
                order.count = cursor.getInt(cursor.getColumnIndex("count"));
                sum+=order.count;
                order.state = false;
                if(sum <= 5){
                    index++;
                    list.add(order);
                }else{
                    break;
                }
            }while (cursor.moveToNext());
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

    private class Order{
        private String orderID;
        private String clothingID;
        private int count;
        private Boolean state;
    }
}
