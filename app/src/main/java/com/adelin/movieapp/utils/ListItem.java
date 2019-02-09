package com.adelin.movieapp.utils;


import android.content.Context;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adelin.movieapp.MainActivity;
import com.adelin.movieapp.listViewAdapters.CustomArrayAdapter;

import java.util.ArrayList;

public class ListItem {

    private EditText movieTitle;
    private ArrayList<String> stringArrayList;
    private CustomArrayAdapter<String> arrayAdapter;

    public ListItem(ArrayList<String> stringArrayList, CustomArrayAdapter<String> arrayAdapter, EditText movieTitle){
        this.movieTitle = movieTitle;
        this.arrayAdapter = arrayAdapter;
        this.stringArrayList = stringArrayList;
    }

    public void onItemClick(View v){
        AbsoluteLayout linearLayout = (AbsoluteLayout) v.getParent();
        String content = ((TextView) linearLayout.getChildAt(0)).getText().toString();

        movieTitle.setText(content);
    }

    public void onItemDeleteButtonClick(View v){

        AbsoluteLayout linearLayout = (AbsoluteLayout) v.getParent();
        String content = ((TextView) linearLayout.getChildAt(0)).getText().toString();

        Context context = MainActivity.getContext();
        Toast toast = Toast.makeText(context, "deleted: " + content, Toast.LENGTH_SHORT);
        toast.show();

        stringArrayList.remove(content);
        arrayAdapter.notifyDataSetChanged(stringArrayList);
    }
}
