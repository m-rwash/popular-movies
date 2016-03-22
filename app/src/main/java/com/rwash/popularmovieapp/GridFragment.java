package com.rwash.popularmovieapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class GridFragment extends Fragment {

    Context context = getActivity();

    private String[] labels = {"dog2", "dog3",
            "dog4", "dog5",
            "dog6", "dog7",
            "dog0", "dog1",
            "dog2", "dog3",
            "dog4", "dog5",
            "dog6", "dog7",
            "dog0", "dog1",
            "dog2", "dog3",
            "dog4", "dog5",
            "dog6", "dog7"};

    private Integer[] images = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };
    public GridFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_grid, container, false);

        RecyclerView recyclerViewMovies = (RecyclerView) rootView.findViewById(R.id.rVmovies);
        recyclerViewMovies.setAdapter(new MoviesAdapter(images, labels));
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerViewMovies.setHasFixedSize(true);

        //recyclerViewMovies.addItemDecoration(new MoviesAdapter.GridSpacingItemDecoration(2, 10, true));


        return rootView;
    }
}
