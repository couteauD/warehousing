package com.example.warehousing.stocking;

import android.content.Context;


import com.example.warehousing.stocking.AbstractPermission;

import java.util.HashMap;
import java.util.Map;

class PremissonFactory {
    static Map<Integer,String> premisson = new HashMap<>();

    static {
        premisson.put(1,"A");
        premisson.put(2,"B");
        premisson.put(3,"C");
        premisson.put(4,"D");
    }

    public static AbstractPermission getPremission(Context context, String jobNumber, String rack){
        AbstractPermission abstractPermission = null;
        int number = Integer.parseInt(jobNumber);
        System.out.println(number);
        if(premisson.get(number % 10).equals(rack)){
            abstractPermission = new hasPremisson();
        }else{
            abstractPermission = new NotPremisson();
        }
        return abstractPermission;
    }
}
