package com.themartinsierra.popularmovieapp;

import android.content.Context;


/**
 * Created by Martin on 9/4/2016.
 */
public class PopularMovie {
    String originalTitle;
    String moviePosterPath;
    String overview;
    String voteAverage;
    String releaseDate;

    public PopularMovie(String vOriginalTitle, String vMoviePosterPath, String vOverview, String vVoteAverage, String vReleaseDate, Context context)
    {
        this.originalTitle = vOriginalTitle;
        this.moviePosterPath = context.getString(R.string.image_path) + context.getString(R.string.image_size) +  vMoviePosterPath;
        this.overview = vOverview;
        this.voteAverage =  vVoteAverage;
        this.releaseDate = vReleaseDate;
    }
}
