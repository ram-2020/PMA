package com.themartinsierra.popularmovieapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.themartinsierra.popularmovieapp.databinding.FragmentDetailBinding;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();

    private String originalTitle;
    private String moviePosterPath;
    private String overview;
    private String voteAverage;
    private String releaseDate;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentDetailBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail, container, false);
        View rootView = binding.getRoot();

        Intent intent = getActivity().getIntent();
        Bundle extras = intent.getExtras();
        if (intent != null && extras != null) {
            originalTitle = extras.getString("ORIGINALTITLE");
            moviePosterPath = extras.getString("POSTERPATH");
            overview = extras.getString("OVERVIEW");
            voteAverage = extras.getString("VOTEAVERAGE");
            releaseDate = extras.getString("RELEASEDATE");

            binding.originaltitleText.setText(originalTitle);
            binding.overviewText.setText(overview);
            binding.voteaverageText.setText(voteAverage);
            binding.releasedateText.setText(releaseDate);
            Picasso.with(getContext()).load(moviePosterPath).into(binding.movieposterImage);
        }
        return rootView;
    }
}
