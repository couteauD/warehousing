package com.example.warehousing;

import android.content.Context;
import android.net.Uri;

import java.util.List;

interface importFile {
    public List<ModuleOrder> importFile(Context context,String path);
}
