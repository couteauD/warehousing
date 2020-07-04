package com.example.warehousing.importFile;

import android.content.Context;
import android.net.Uri;

import com.example.warehousing.ModuleOrder;

import java.util.List;

interface importFile {
    public List<ModuleOrder> importFile(Context context, String path);
}
