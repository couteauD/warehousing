package com.example.warehousing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.core.SmartTable;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class OrderImportActivity extends AppCompatActivity {

    private Context context;
    private Toolbar toolbar;
    private Button buttonImport;
    private SmartTable tableOrder;
    private String path;

    private static final int FILE_SELECT_CODE = 0;
    private static final String TAG = "ChooseFile";


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_import);
        context = getApplicationContext();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        buttonImport = findViewById(R.id.button_import);
        tableOrder = findViewById(R.id.table_order);

        buttonImport.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                verifyStoragePermissions(OrderImportActivity.this);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), 0);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Potentially direct the user to the Market with a Dialog
                }
            }
        });
    }

    private void initTable(List<ModuleOrder> Orderlist) {
        List<UserInfo> Userlist = new ArrayList<>();
        SQLiteDatabase db = SQLiteDB.getInstance(OrderImportActivity.this).getDb();
        ContentValues values = new ContentValues();
        for(int i=0;i<Orderlist.size();i++){
            String orderID = Orderlist.get(i).getOrderID();
            String clothingID = Orderlist.get(i).getClothingID();
            int count =  Orderlist.get(i).getCount();

            values.put("number",i);
            values.put("orderID",orderID);
            values.put("clothingID",clothingID);
            values.put("count",count);
            db.insert("newOrder",null,values);
            values.clear();

            UserInfo userInfo = new UserInfo();
            userInfo.orderID = orderID;
            userInfo.clothingID = clothingID;
            userInfo.count = count;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d(TAG, "File Uri: " + uri.toString());
                    // Get the path
                    try {
                        path = FileUtils.getPath(context, uri);
                        importProxy importProxy = new importProxy();
                        initTable(importProxy.importFile(context,path));
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "File Path: " + path);
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 在对sd卡进行读写操作之前调用这个方法
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to grant permissions
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }
}