package com.adelin.movieapp.utils;

import android.content.Context;

import com.adelin.movieapp.repository.InFileRepository;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Array list of strings that stores the data and reloads it
 */
public class CachedArrayList<T> extends ArrayList<T> {

    private InFileRepository<T> repository;

    public CachedArrayList(Context context, String fileName){
        this.repository = new InFileRepository<>(context, fileName);
        this.clone(repository.loadData());
    }

    private void clone(ArrayList<T> loadData) {
        this.addAll(loadData);
    }

    @Override
    public boolean add(T t) {
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
        repository.saveData(this);
        super.clear();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T s : c) {
            super.add(s);
        }
        repository.saveData(this);
        return true;
    }
}
