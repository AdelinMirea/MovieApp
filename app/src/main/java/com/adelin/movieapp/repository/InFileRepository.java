package com.adelin.movieapp.repository;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class InFileRepository<T> implements Serializable {
    private String fileName;
    private transient Context context;

    public InFileRepository(Context context, String fileName){
        this.fileName = fileName;
        this.context = context;
    }

    public ArrayList<T> loadData(){
        try{
            ArrayList<T> collection = new ArrayList<>();
            FileInputStream fin = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fin);
            collection = (ArrayList<T>) ois.readObject();
            ois.close();
            fin.close();
            return collection;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    public void saveData(ArrayList<T> collection){
        try{
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream ous = new ObjectOutputStream(fos);
            ous.writeObject(collection);
            ous.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
