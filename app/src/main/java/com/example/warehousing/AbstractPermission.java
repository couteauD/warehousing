package com.example.warehousing;

import android.content.Context;

import java.util.List;

public interface AbstractPermission {

    public List check(Context context, String jobNumber,String rack);

}
