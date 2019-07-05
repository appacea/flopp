/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/3/19 12:34 AM
 *
 * Last modified 7/3/19 12:11 AM
 */

package com.flipp.flopp.ui.main.pages.designs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flipp.flopp.R;
import com.flipp.flopp.data.art.local.Art;
import com.flipp.flopp.ui.main.DetailActivity;
import com.flipp.flopp.ui.main.MainViewModel;
import com.flipp.flopp.ui.main.pages.ArtAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DesignsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Art> artworks = new ArrayList<>();
    private MainViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_designs, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvDesigns);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ArtAdapter(artworks, new ArtAdapter.OnArtClickedListener() {
            @Override
            public void onArtClicked(Art art) {
                Intent detailActivity = new Intent(getActivity(), DetailActivity.class);
                detailActivity.putExtra("Art", art);
                getActivity().startActivity(detailActivity);
            }

            @Override
            public void onArtFavorite(Art art, boolean isFavorite) {
                model.setFavorite(art.getId(),isFavorite);
            }
        });
        recyclerView.setAdapter(adapter);


        model = ViewModelProviders.of(getActivity()).get(MainViewModel.class);



        model.getDesigns().observe(this.getViewLifecycleOwner(), new Observer<List<Art>>() {
            @Override
            public void onChanged(List<Art> arts) {
                artworks.clear();
                artworks.addAll(arts);
                adapter.notifyDataSetChanged();
            }
        });



        return view;
    }




}