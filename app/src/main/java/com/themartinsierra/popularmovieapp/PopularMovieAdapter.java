package com.themartinsierra.popularmovieapp;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.themartinsierra.popularmovieapp.databinding.MovieitemBinding;

import java.util.List;
/**
 * Created by Martin on 9/4/2016.
 * Used https://github.com/nomanr/android-databinding-example/blob/master/app/src/main/java/com/databinding/example/databindingexample/adapters/SimpleAdapter.java
 * as reference.
 */
public class PopularMovieAdapter extends ArrayAdapter<PopularMovie> {
    private Context context;
    private List<PopularMovie> movies;

    public PopularMovieAdapter(Activity context, List<PopularMovie> popularMovies)  {

        super(context, 0, popularMovies);
        this.movies = popularMovies;
        this.context = context;
    }
    @Override
    public PopularMovie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //TODO: Implement ViewHolder in next segment of project.
        LayoutInflater inflater = LayoutInflater.from(context);
        MovieitemBinding binding = DataBindingUtil.inflate(inflater, R.layout.movieitem, null, false);

        PopularMovie popularMovie = getItem(position);
        binding.setMovieitem(popularMovie);


        Picasso.with(getContext()).load(popularMovie.moviePosterPath).into(binding.movieImage);
        return binding.getRoot();
    }
}
