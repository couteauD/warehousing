package com.example.warehousing;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class importProxy implements importFile{

    List<ModuleOrder> list = new ArrayList<>();
    importXML importXML = new importXML();
    importCSV importCSV = new importCSV();

    @Override
    public List<ModuleOrder> importFile(Context context,String path) {
        String last = getFileExtension(path);
        if(last.equals("csv")){
            list = importCSV.importFile(context,path);
        }else{
            list = importXML.importFile(context,path);
        }
        return list;
    }

    /**
     * 获取全路径中的文件拓展名
     *
     * @param filePath 文件路径
     * @return 文件拓展名
     */
    public static String getFileExtension(final String filePath) {
        if (filePath == null) return filePath;
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastPoi == -1 || lastSep >= lastPoi) return "";
        return filePath.substring(lastPoi + 1);
    }


}
