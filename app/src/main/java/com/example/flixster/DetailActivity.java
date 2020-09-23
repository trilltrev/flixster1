package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.youtube.player.YoutubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import models.Movies;
import okhttp3.Headers;

public class DetailActivity<YoutubeBaseActivity> extends YoutubeBaseActivity {

    private static final String YOUTUBE_API_KEY = "AIzaSyAHpKACyrPHDZZ1CXmAhjnJLapTc_z8B8";
    public static final String VIDOES_URL = "https://api.themoviedb.org/3/movie/%d/vidoes?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    YoutubePlayerView youtubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        ratingBar = findViewById(R.id.ratingBar);
        youtubePlayerView = findViewById(R.id.player);

        Movies movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        ratingBar.setRating((float) movie.getRating());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDOES_URL, movie.getMoviId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if (results.length() == 0){
                        return;
                    }
                    String youtubeKey = results.getJSONObject(0).getString("key");
                    Log.d("DetailActivity", "youtubeKey");
                    initializeYoutube(youtubeKey);
                } catch (JSONException e) {
                    Log.e("DetailActivity", "Failed to parse JSON", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });



    private void initializeYoutube(String Object youtubeKey;
        youtubeKey) {
            youtubePlayerView.initialize(YOUTUBE_API_KEY, new TextToSpeech.OnInitializedListener()) {
                @Override
                public void onInitializationSuccess (YoutubePlayer.Provider provider, YoutubePlayer
                youTubePlayer,boolean b)
                Log.d("DetailActivity", "onInitializationSuccess");
                youTubePlayer.cueVideo(youtubeKey);

            }

            @Override
            public void onInitializationFailure (YoutubePlayer.Provider
            provider, YoutubeInitializationResult youTubeInitializationResult){

            }
        }

    });
}