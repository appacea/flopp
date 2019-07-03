/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/1/19 12:18 PM
 *
 * Last modified 7/1/19 12:18 PM
 */

package com.flipp.flopp.ui.main.pages.explore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flipp.flopp.R;
import com.flipp.flopp.common.architecture.Status;
import com.flipp.flopp.data.art.local.Art;
import com.flipp.flopp.ui.main.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ExploreFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Art> artworks = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvExplore);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ExploreAdapter(artworks);
        recyclerView.setAdapter(adapter);


        MainViewModel model = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

        model.getAllArt().observe(this, resource -> {
            if(resource.status == Status.LOADING) {
                // displayLoader();

            } else if(!resource.data.isEmpty()) {
                // updateMoviesList(resource.data);
                artworks.clear();
                artworks.addAll(resource.data);
                adapter.notifyDataSetChanged();

            } else {
                //handleErrorResponse();
            }
        });


        return view;
    }




}