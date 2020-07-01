package com.example.warehousing;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

class importCSV {

    public List<ModuleOrder> importCSV(Context context){
        List<ModuleOrder> list = new ArrayList<>();
        InputStream inputStream;
        Scanner scanner;
        try{
            inputStream = context.getResources().getAssets().open("orderForm.csv");
            scanner = new Scanner(inputStream,"UTF-8");
            scanner.nextLine();
            while(scanner.hasNextLine()){
                String sourceString = scanner.nextLine();
                Pattern pattern = Pattern.compile("[^,]*,");
                Matcher matcher = pattern.matcher(sourceString);
                String[] lines = new String[3];
                int i=0;
                while (matcher.find()){
                    String find = matcher.group().replace(",","");
                    lines[i] = find.trim();
                    Log.e(TAG,"find="+find+",i="+i+",lines="+lines[i]);
                    i++;
                }
                ModuleOrder order = new ModuleOrder(lines[0],lines[1],lines[2]);
                list.add(order);
                i=0;
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


}
