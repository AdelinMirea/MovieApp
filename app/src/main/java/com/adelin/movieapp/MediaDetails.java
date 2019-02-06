package com.adelin.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.adelin.movieapp.utils.MediaInfo;
import com.adelin.movieapp.utils.NetData;
import com.adelintmovieapp.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MediaDetails extends AppCompatActivity {

    private ImageView poster;
    private TextView title;
    private TextView year;
    private TextView rating;
    private TextView cast;
    private TextView plot;
    private Button addToFavButton;
    private MediaInfo mediaInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_details);

        setValues();

        Intent intent = getIntent();
        String IMDBcode = intent.getStringExtra(MainActivity.MOVIE_CODE);

        showMovieDetails(IMDBcode);
    }

    private void setValues() {
        this.poster = findViewById(R.id.cover);
        this.rating = findViewById(R.id.ratingText);
        this.title = findViewById(R.id.titleText);
        this.year = findViewById(R.id.yearText);
        this.plot = findViewById(R.id.plotText);
        this.cast = findViewById(R.id.castText);
        this.addToFavButton = findViewById(R.id.addToFaved);
    }

    private void showMovieDetails(String movieCode) {
        Thread thread = new Thread(() -> {
            try {
                mediaInfo = NetData.getMediaInfo(movieCode);
                if (!mediaInfo.getTitle().equals("")) {
                    runOnUiThread(() -> {
                        Picasso.get().load(mediaInfo.getPosterLink()).into(poster);
                        title.setText(mediaInfo.getTitle());
                        year.setText("Year: " + mediaInfo.getYear());
                        rating.setText("Rating: " + mediaInfo.getRating());
                        plot.setText(mediaInfo.getPlot());
                        cast.setText(mediaInfo.getActors());
                        setTitle(mediaInfo.getTitle());
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

}
