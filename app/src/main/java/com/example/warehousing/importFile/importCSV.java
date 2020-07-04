package com.example.warehousing.importFile;

import android.content.Context;

import com.example.warehousing.ModuleOrder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class importCSV implements importFile{

    public List<ModuleOrder> importFile(Context context, String path){
        List<ModuleOrder> list = new ArrayList<>();
        InputStream inputStream;
        Scanner scanner;
        try{
            File file = new File(path);
            inputStream = new FileInputStream(file);
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
                    i++;
                }
                ModuleOrder order = new ModuleOrder(lines[0],lines[1],lines[2]);
                list.add(order);
                i=0;
            }
        } catch (NumberFormatException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }


}
