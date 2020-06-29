package com.example.warehousing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
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
    private LinearLayout linearLayoutSearch;
    private EditText editTextID;
    private ImageButton imageButtonSearch;
    private SmartTable table;
    private TableData tableData;
    private List<Order> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_picking);
        context = getApplicationContext();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        linearLayoutSearch = findViewById(R.id.linearLayout_search);
        editTextID = findViewById(R.id.editText_ID);
        imageButtonSearch = findViewById(R.id.imageButton_search);
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
        tableData = new TableData<Order>("订单",list, IDColumn,countColumn,stateColumn);
        table.setTableData(tableData);
        table.getConfig().setShowXSequence(false);
    }

    private void init() {
        Order order = new Order();
        order.ID = "4a507db7-7c52-2c22-d6a6-77ade48625bd";
        order.count = 3;
        order.state = true;
        list.add(order);
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
