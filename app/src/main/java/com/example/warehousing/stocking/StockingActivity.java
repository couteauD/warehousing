package com.example.warehousing.stocking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.listener.OnColumnItemClickListener;
import com.example.warehousing.R;
import com.example.warehousing.RecordFragment;
import com.example.warehousing.SQLiteDB;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout linearLayoutSearch;
    private EditText editTextRack;
    private ImageButton imageButtonSearch;
    private Button buttonUpdate,buttonRecord;
    private SmartTable table;
    private TableData tableData;
    private List<Order> list = new ArrayList<>();

    private List<Order> order = new ArrayList<>();
    private ArrayList<String> record = new ArrayList<>();

    private String jobNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocking);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        linearLayoutSearch = findViewById(R.id.linearLayout_search);
        editTextRack = findViewById(R.id.editText_rack);
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
            public void onClick(Column<Integer> column, String value, Integer integer, final int position) {
                order.add(position,list.get(position));
                final EditText real_editText = new EditText(StockingActivity.this);
                real_editText.setFocusable(true);
                real_editText.setHint("输入实际数量（件）");
                new AlertDialog.Builder(StockingActivity.this).setTitle("实际数量")
                        .setView(real_editText)
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //修改表格数量
                                order.get(position).real= Integer.parseInt(real_editText.getText().toString());
                                table.notifyDataChanged();
                            }
                        }).show();
            }
        });


        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rack = editTextRack.getText().toString();
                jobNumber = getIntent().getStringExtra("jobNumber");
                if(jobNumber != null){
                    AbstractPermission abstractPermission = PremissonFactory.getPremission(StockingActivity.this,jobNumber,rack);
                    list = abstractPermission.check(StockingActivity.this,jobNumber,rack);
                }
                else{
                    init(rack);
                }
                tableData = new TableData<Order>("库存详情",list, IDColumn,countColumn,realColumn);
                table.setTableData(tableData);
                table.getConfig().setShowXSequence(false);
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = SQLiteDB.getInstance(StockingActivity.this).getDb();
                ContentValues values = new ContentValues();
                for(int i=0;i<order.size();i++){
                        values.put("count",order.get(i).real);
                        writeRecord(list.get(i).ID,list.get(i).count,order.get(i).real);
                        order.get(i).count = order.get(i).real;
                        table.notifyDataChanged();

                        db.update("Bale",values,"id= ? ",new String[]{list.get(i).ID});
                        values.clear();
                }
            }
        });

        buttonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordFragment recordFragment = RecordFragment.newInstance(record);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                recordFragment.show(fragmentTransaction,"record");
            }
        });

    }

    private void writeRecord(String id,int source,int target) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        record.add(time+" "+id+" source:"+source+" target:"+target);
    }

    private void init(String rack) {
        SQLiteDatabase db = SQLiteDB.getInstance(StockingActivity.this).getDb();
        Cursor cursor = db.rawQuery("select * from Bale where rack ="+"'"+rack+"'",null);
        list.clear();
        if (cursor.moveToFirst()){
            do{
                Order order = new Order();
                order.ID= cursor.getString(cursor.getColumnIndex("id"));
                order.count = cursor.getInt(cursor.getColumnIndex("count"));
                order.real = order.count;
                list.add(order);
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

    static class Order{
        String ID;
        int count;
        int real;
    }

}
