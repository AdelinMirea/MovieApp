package com.adelin.movieapp.repository;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class InFileRepository {
    private String fileName;
    private Context context;
    //TODO set file name as a parameter
    public InFileRepository(Context context){
        this.fileName = "history.txt";
        this.context = context;
    }

    public void loadData(ArrayList<String> stringArrayList){
        try{
            FileInputStream fin = context.openFileInput(fileName);
            int c;
            StringBuilder temp= new StringBuilder();
            while( (c = fin.read()) != -1){
                temp.append(Character.toString((char) c));
            }
            String[] arr = temp.toString().split("`");
            stringArrayList.addAll(Arrays.asList(arr));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveData(ArrayList<String> stringArrayList){
        try{
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            for (String string : stringArrayList) {
                fos.write(string.getBytes());
                fos.write("`".getBytes());
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
