package com.adelin.movieapp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetData {

    private static ArrayList<MediaInfo> mediaInfos = new ArrayList<>();

    /**
     * method used to get info about movies searching by title
     * @param title movie title
     * @return string consisting of movie info such as title, year, IMDBID, posterlink
     * @throws IOException
     * @throws JSONException
     */
    public static String accesData(String title) throws IOException, JSONException {

        URL url = new URL("https://www.omdbapi.com/?apikey=ea34e38a&s="+title);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int status = connection.getResponseCode();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String inputLine;
        StringBuilder stringBuffer = new StringBuilder();
        while ((inputLine = bufferedReader.readLine())!=null){
            stringBuffer.append(inputLine);
        }
        bufferedReader.close();
        connection.disconnect();

        return parseJson(stringBuffer.toString());
    }

    /**
     * parsing a json string to get the necessary info
     * @param jsonString json string got from the request
     * @return string consisting of movie infos
     * @throws JSONException
     */
    private static String parseJson(String jsonString) throws JSONException {
        mediaInfos.clear();
        JSONObject object = new JSONObject(jsonString);
        JSONArray array = object.getJSONArray("Search");
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<array.length(); i++){
            mediaInfos.add(new MediaInfo(array.getJSONObject(i).getString("Title"),
                            array.getJSONObject(i).getString("Year"),
                            array.getJSONObject(i).getString("imdbID"),
                            array.getJSONObject(i).getString("Poster")));
            stringBuilder.append(mediaInfos.get(i).toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     *
     * @return array list with all the movies got from searching by title
     */
    public static ArrayList<MediaInfo> getMediaInfos() {
        return mediaInfos;
    }

    /**
     *
     * @param movieCode IMDB code
     * @return mediaInfo object with filled with data about specified movie
     * @throws IOException
     */
    public static MediaInfo getMediaInfo(String movieCode) throws IOException {
        URL url = new URL("https://www.omdbapi.com/?apikey=ea34e38a&i=" + movieCode);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");

        urlConnection.setRequestProperty("Content-Type", "application/json");

        urlConnection.setReadTimeout(5000);
        urlConnection.setConnectTimeout(5000);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        String input;
        StringBuilder stringBuilder = new StringBuilder();
        while ((input = bufferedReader.readLine())!= null){
            stringBuilder.append(input);
        }
        bufferedReader.close();
        urlConnection.disconnect();

        return createMediaInfoFromJson(stringBuilder.toString());
    }

    /**
     *
     * @param json json string og one single movie
     * @return mediaInfo object with details set
     */
    private static MediaInfo createMediaInfoFromJson(String json){
        try {
            JSONObject object = new JSONObject(json);
            String title, image, year, rating, plot, actors, genre;
            title = object.getString("Title");
            year = object.getString("Year");
            image = object.getString("Poster");
            rating = object.getString("imdbRating");
            plot = object.getString("Plot");
            actors = object.getString("Actors");
            genre = object.getString("Genre");
            return new MediaInfo(title, year, object.getString("imdbID"), image, rating, plot, actors, genre);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new MediaInfo("", "", "", "");
    }
}
