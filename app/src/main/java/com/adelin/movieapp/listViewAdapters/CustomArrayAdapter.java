package com.adelin.movieapp.listViewAdapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.adelin.movieapp.repository.InFileRepository;

import java.util.ArrayList;
import java.util.List;

public class CustomArrayAdapter<T> extends ArrayAdapter<T> {

    private InFileRepository repository;

    public CustomArrayAdapter(Context context, int resource, int textViewResourceId, List<T> objects) {
        super(context, resource, textViewResourceId, objects);
        repository = new InFileRepository(getContext());
        repository.loadHistory((ArrayList<String>) objects);
        super.notifyDataSetChanged();
    }

    public void save(ArrayList<String> arrayList){
        repository.saveHistory(arrayList);
    }

    public void notifyDataSetChanged(ArrayList<String> arrayList) {
        super.notifyDataSetChanged();
        repository.saveHistory(arrayList);
    }
}

