package com.example.warehousing;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

class NotPremisson implements AbstractPermission {
    private List list = new ArrayList();

    @Override
    public List check(Context context, String jobNumber, String rack) {
        new AlertDialog.Builder(context).setTitle("消息提醒")
                .setMessage("对不起，您没有盘点该货架的权限！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
        return list;
    }
}
