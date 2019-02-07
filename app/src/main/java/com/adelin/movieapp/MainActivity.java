package com.adelin.movieapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.adelin.movieapp.listViewAdapters.MediaInfoArrayAdapter;
import com.adelin.movieapp.utils.CachedArrayList;
import com.adelin.movieapp.utils.MediaInfo;
import com.adelin.movieapp.utils.NetData;
import com.adelintmovieapp.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String HISTORY_ARRAY = "app.com.adelin.testproject.HISTORY";
    public static final String MOVIE_TITLE = "app.com.adelin.testproject.MOVIE_TITLE";
    public static final String MOVIE_CODE = "app.com.adelin.testproject.MOVIE_CODE";

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private Button searchButton;
    private EditText movieTitle;
    private ListView list;
    private CachedArrayList historyStringArrayList;
    private ArrayList<MediaInfo> mediaInfoArrayList;
    private MediaInfoArrayAdapter mediaInfoArrayAdapter;

    private static Context context;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainActivity.context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historyStringArrayList = new CachedArrayList(this);

        mediaInfoArrayList = new ArrayList<>();
        mediaInfoArrayAdapter = new MediaInfoArrayAdapter(this, R.layout.media_list_item, mediaInfoArrayList);

        searchButton = findViewById(R.id.searchButton);
        movieTitle = findViewById(R.id.movieInput);
        list = findViewById(R.id.list);

        list.setAdapter(mediaInfoArrayAdapter);

        searchButton.setOnClickListener((v) -> searchMovie());
        list.setOnItemClickListener(this::onMovieClickedHandler);
        list.setOnItemLongClickListener(this::onLongMovieClickedHandler);
        createSlidingMenu();
    }


    private void createSlidingMenu(){
        drawerLayout = findViewById(R.id.drawer);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()){
            case R.id.historyButton:
                historyButtonHandler();
                return true;
            case R.id.closeApp:
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                return true;
            case R.id.fav:
                //TODO Faved list
                //TODO Time watched
                //TODO Watched List/Button
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Toast.makeText(MainActivity.this, " was selected", Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK ){
            movieTitle.setText(data.getStringExtra(MOVIE_TITLE));
            historyStringArrayList.clear();
            historyStringArrayList.addAll(data.getStringArrayListExtra(HISTORY_ARRAY));
        }
    }

    private void onMovieClickedHandler(View adaptiveView, View view, int position, long id){
        Toast toast = Toast.makeText(this, mediaInfoArrayList.get(position).getTitle(), Toast.LENGTH_LONG);
        toast.show();
        Intent intent = new Intent(this, MediaDetails.class);
        intent.putExtra(MOVIE_CODE, mediaInfoArrayList.get(position).getIMDBCode());
        startActivityForResult(intent, 2);
    }

    private boolean onLongMovieClickedHandler(View adaptiveView, View view, int position, long id){
        Toast toast = Toast.makeText(this, mediaInfoArrayList.get(position).getTitle(), Toast.LENGTH_LONG);
        toast.show();
        return true;
    }

    private void historyButtonHandler(){
        Intent historyIntent = new Intent(this, HistoryActivity.class);
        historyIntent.putExtra(HISTORY_ARRAY, historyStringArrayList);
        startActivityForResult(historyIntent, 1);
    }

    public static Context getContext(){
        return context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void searchMovie(){
        movieTitle.setEnabled(false);
        movieTitle.setEnabled(true);
        StringBuilder stringBuilder = new StringBuilder();
        Thread thread = new Thread(()->{
            try {
                stringBuilder.append(NetData.accesData(movieTitle.getText().toString()));
                runOnUiThread(()->{
                    if(historyStringArrayList.stream().noneMatch((elem) -> elem.toLowerCase().equals(movieTitle.getText().toString().toLowerCase()))){
                        historyStringArrayList.add(movieTitle.getText().toString());
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                runOnUiThread(()->{
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(e.getMessage()).setTitle("Alerta!");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                });
                e.printStackTrace();
            }
            runOnUiThread(()->{
                mediaInfoArrayList.clear();
                NetData.getMediaInfos().forEach(elem-> mediaInfoArrayList.add(new MediaInfo(elem.getTitle(), elem.getYear(), elem.getIMDBCode(), elem.getPosterLink())));
                mediaInfoArrayAdapter.notifyDataSetChanged();
            });
        });
        thread.start();
    }

}
