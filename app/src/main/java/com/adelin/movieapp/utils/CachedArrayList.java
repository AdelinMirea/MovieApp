package com.adelin.movieapp.utils;

import android.content.Context;

import com.adelin.movieapp.repository.InFileRepository;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Array list of strings that stores the data and reloads it
 */
public class CachedArrayList extends ArrayList<String> {

    private InFileRepository repository;

    public CachedArrayList(Context context){
        this.repository = new InFileRepository(context);
        repository.loadData(this);
    }

    @Override
    public boolean add(String t) {
        boolean rspv =  super.add(t);
        repository.saveData(this);
        return rspv;
    }

    @Override
    public boolean remove(Object obj){
        boolean rspv = super.remove(obj);
        repository.saveData(this);
        return rspv;
    }

    @Override
    public void clear(){
        super.clear();
        repository.saveData(this);
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        for (String s : c) {
            this.add(s);
        }
        return true;
    }
}
