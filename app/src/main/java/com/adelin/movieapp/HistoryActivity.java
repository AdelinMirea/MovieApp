package com.adelin.movieapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adelintmovieapp.R;

import java.util.ArrayList;
import java.util.Collections;

public class HistoryActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> historyArray;
    private ArrayAdapter<String> historyArrayAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        this.setTitle("History");

        Intent intent = getIntent();
        historyArray = intent.getStringArrayListExtra(MainActivity.HISTORY_ARRAY);

        listView = findViewById(R.id.historyList);
        Collections.reverse(historyArray);
        historyArrayAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.txtitem, historyArray);
        listView.setAdapter(historyArrayAdapter);
    }

    @Override
    public void onBackPressed(){
        Intent output = new Intent();
        output.putExtra(MainActivity.HISTORY_ARRAY, historyArray);
        setResult(RESULT_OK, output);
        finish();
    }

    private void onHistoryListItemClocked(View view){
        String selected = ((TextView) view.findViewById(R.id.txtitem)).getText().toString();
        closeActivityAndSendData(selected);
    }

    public void onItemClick(View v){
        AbsoluteLayout linearLayout = (AbsoluteLayout) v.getParent();
        String content = ((TextView) linearLayout.getChildAt(0)).getText().toString();

        closeActivityAndSendData(content);
    }

    private void closeActivityAndSendData(String string){
        Intent output = new Intent();
        output.putExtra(MainActivity.MOVIE_TITLE, string);
        output.putExtra(MainActivity.HISTORY_ARRAY, historyArray);
        setResult(RESULT_OK, output);
        finish();
    }

    public void onItemDeleteButtonClick(View v){

        AbsoluteLayout linearLayout = (AbsoluteLayout) v.getParent();
        String content = ((TextView) linearLayout.getChildAt(0)).getText().toString();

        Context context = MainActivity.getContext();
        Toast toast = Toast.makeText(context, "deleted: " + content, Toast.LENGTH_SHORT);
        toast.show();

        historyArray.remove(content);
        historyArrayAdapter.notifyDataSetChanged();
    }

}
