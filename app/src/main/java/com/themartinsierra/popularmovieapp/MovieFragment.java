/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
    public MovieFragment() {
}

    @Override
 */
package com.themartinsierra.popularmovieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


import com.themartinsierra.popularmovieapp.databinding.FragmentMovieBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Encapsulates fetching the movie and displaying it as a {@link GridView} layout.
 */
public class MovieFragment extends Fragment {

    private PopularMovieAdapter popularMovieAdapter;
    private List<PopularMovie> popularMovies;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //Uncomment this out to reveal the refresh option
        //inflater.inflate(R.menu.moviefragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        popularMovies = new ArrayList<PopularMovie>();
        popularMovieAdapter =  new PopularMovieAdapter(getActivity(), popularMovies);


        FragmentMovieBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie,container, false);
        View rootView = binding.getRoot();

        binding.moviesGrid.setAdapter(popularMovieAdapter);
        binding.moviesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                PopularMovie movie = popularMovieAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra("ORIGINALTITLE", movie.originalTitle)
                        .putExtra("POSTERPATH", movie.moviePosterPath)
                        .putExtra("OVERVIEW", movie.overview)
                        .putExtra("VOTEAVERAGE", movie.voteAverage)
                        .putExtra("RELEASEDATE", movie.releaseDate);
                startActivity(intent);
            }
        });
        return rootView;
    }

    private void updateWeather() {
        FetchMovieTask movieTask = new FetchMovieTask();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String listtype = prefs.getString(getString(R.string.pref_gridtype_key),getString(R.string.pref_value_most_popular));

        movieTask.execute(listtype);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }

    public class FetchMovieTask extends AsyncTask<String, Void, PopularMovie[]> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();
        /**
         * Take the String representing the complete Movie Information in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private PopularMovie[] getPopularMovieDataFromJson(String movieJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String TMDB_RESULTS = "results";
            final String TMDB_ORIGINAL_TITLE = "original_title";
            final String TMDB_POSTER_PATH = "poster_path";
            final String TMDB_OVERVIEW = "overview";
            final String TMDB_VOTE_AVERAGE = "vote_average";
            final String TMDB_RELEASE_DATE = "release_date";

            JSONObject popularMovieJson = new JSONObject(movieJsonStr);
            JSONArray popularMoviesArray = popularMovieJson.getJSONArray(TMDB_RESULTS);
            List<PopularMovie> popularMovies = new ArrayList<PopularMovie>();

            for(int i = 0; i < popularMoviesArray.length(); i++) {

                String originalTitle;
                String moviePosterPath;
                String overview;
                String voteAverage;
                String releaseDate;
                String length;
                // Get the JSON object representing the movie
                JSONObject movie = popularMoviesArray.getJSONObject(i);

                originalTitle = movie.getString(TMDB_ORIGINAL_TITLE);
                moviePosterPath = movie.getString(TMDB_POSTER_PATH);
                overview = movie.getString(TMDB_OVERVIEW);
                voteAverage = movie.getString(TMDB_VOTE_AVERAGE);
                releaseDate = movie.getString(TMDB_RELEASE_DATE);
                popularMovies.add(new PopularMovie( originalTitle,moviePosterPath,overview,voteAverage,releaseDate, getContext()));
            }
            return popularMovies.toArray(new PopularMovie[popularMovies.size()]);
        }
        @Override
        protected PopularMovie[] doInBackground(String... params) {

             // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String popularMoviesJsonStr = null;

            try {
                // Construct the URL for the The Movie DB
                final String POPULARMOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";
                final String APPID_PARAM = "api_key";

                Uri builtUri = Uri.parse(POPULARMOVIE_BASE_URL).buildUpon()
                        .appendPath( params[0])
                        .appendQueryParameter(APPID_PARAM, BuildConfig.MOVIEAPIKEY)
                        .build();
                URL url = new URL(builtUri.toString());

                // Create the request to The Movie Database, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                popularMoviesJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the Movie data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getPopularMovieDataFromJson(popularMoviesJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }

        @Override
        protected void onPostExecute(PopularMovie[] result) {
            if (result != null) {
                popularMovies =  Arrays.asList(result);
                if (popularMovieAdapter != null)
                        popularMovieAdapter.clear();
                else
                    popularMovieAdapter = new PopularMovieAdapter(getActivity(), popularMovies);
                for(PopularMovie pm : result) {
                    popularMovieAdapter.add(pm);
                }
                // New data is back from the server.  Hooray!
            }
        }
    }
}
