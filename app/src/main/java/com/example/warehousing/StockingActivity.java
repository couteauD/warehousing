package com.example.warehousing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.draw.ImageResDrawFormat;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.listener.OnColumnItemClickListener;
import com.bin.david.form.utils.DensityUtils;

import org.litepal.tablemanager.Connector;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StockingActivity extends AppCompatActivity {

    private Context context;
    private Toolbar toolbar;
    private LinearLayout linearLayoutSearch;
    private EditText editTextID;
    private ImageButton imageButtonSearch;
    private Button buttonUpdate,buttonRecord;
    private SmartTable table;
    private TableData tableData;
    private List<Order> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocking);
        context = getApplicationContext();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Connector.getDatabase();

        linearLayoutSearch = findViewById(R.id.linearLayout_search);
        editTextID = findViewById(R.id.editText_ID);
        imageButtonSearch = findViewById(R.id.imageButton_search);
        table = findViewById(R.id.query_table);
        buttonUpdate = findViewById(R.id.button_update);
        buttonRecord = findViewById(R.id.button_record);

        //表格列
        final Column<String> IDColumn = new Column<>("服装ID", "ID");
        final Column<Integer> countColumn = new Column<>("库存数量", "count");
        final Column<Integer> realColumn = new Column<>("实际数量","real");

        realColumn.setOnColumnItemClickListener(new OnColumnItemClickListener<Integer>() {
            @Override
            public void onClick(Column<Integer> column, String value, Integer integer, int position) {
                final EditText real_editText = new EditText(StockingActivity.this);
                real_editText.setFocusable(true);
                real_editText.setHint("输入实际数量（件）");
                new AlertDialog.Builder(StockingActivity.this).setTitle("实际数量")
                        .setView(real_editText)
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //修改数据库数量
                            }
                        }).show();
            }
        });
        init();
        tableData = new TableData<Order>("库存详情",list, IDColumn,countColumn,realColumn);
        table.setTableData(tableData);
        table.getConfig().setShowXSequence(false);

    }

    private void init() {
        Order order = new Order();
        order.ID = "4a507db7-7c52-2c22-d6a6-77ade48625bd";
        order.count = 3;
        order.real = 3;
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
        private int real;
    }

}
