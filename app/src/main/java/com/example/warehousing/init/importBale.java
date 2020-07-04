package com.example.warehousing.init;

import android.content.Context;

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

class importBale {

    public List<Bale> importFile(Context context){
        List<Bale> list = new ArrayList<>();
        InputStream inputStream;
        Scanner scanner;
        try{
            inputStream = context.getResources().getAssets().open("Bale.csv");
            scanner = new Scanner(inputStream,"UTF-8");
            scanner.nextLine();
            while(scanner.hasNextLine()){
                String sourceString = scanner.nextLine();
                Pattern pattern = Pattern.compile("[^,]*,");
                Matcher matcher = pattern.matcher(sourceString);
                String[] lines = new String[4];
                int i=0;
                while (matcher.find()){
                    String find = matcher.group().replace(",","");
                    lines[i] = find.trim();
                    i++;
                }
                Bale bale = new Bale(lines[0],Integer.parseInt(lines[1]),lines[2],Integer.parseInt(lines[3]));
                list.add(bale);
                i=0;
            }
        } catch (NumberFormatException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
