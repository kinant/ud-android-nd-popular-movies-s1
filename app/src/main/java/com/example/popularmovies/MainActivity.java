package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.utilities.MovieDBJsonUtils;
import com.example.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this, GridLayoutManager.DEFAULT_SPAN_COUNT);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        new FetchMovieTask().execute();
    }

    public class FetchMovieTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {

            URL moviesRequestUrl = NetworkUtils
                    .buildURL(NetworkUtils.Endpoint.POPULAR, getString(R.string.api_key));

            try {

                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(moviesRequestUrl);

                Log.d("JSON RESPONSE:", jsonMovieResponse);

                // Parse JSON into a list of movies...
                List<Movie> movies = MovieDBJsonUtils
                        .getMoviesFromJson(jsonMovieResponse);

            } catch (Exception e){
                e.printStackTrace();
                return null;
            }

            return null;
        }
    }
}
