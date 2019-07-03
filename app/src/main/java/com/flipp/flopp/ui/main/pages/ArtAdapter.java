/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/2/19 10:28 PM
 *
 * Last modified 7/2/19 10:27 PM
 */

package com.flipp.flopp.ui.main.pages;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.flipp.flopp.R;
import com.flipp.flopp.data.art.local.Art;
import com.flipp.flopp.tools.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ArtAdapter extends RecyclerView.Adapter<ArtAdapter.ExploreViewHolder> {
    private List<Art> artworks;

    public static class ExploreViewHolder extends RecyclerView.ViewHolder {
        public FrameLayout flCard;
        public ImageView ivMain;
        public ImageView ivOwner;
        public ExploreViewHolder(FrameLayout v) {
            super(v);
            flCard = v;
            ivMain = v.findViewById(R.id.ivMain);
            ivOwner = v.findViewById(R.id.ivOwner);
        }
    }

    public ArtAdapter(List<Art> artworks) {
        this.artworks = artworks;
    }

    @Override
    public ArtAdapter.ExploreViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        FrameLayout v = (FrameLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_artcard, parent, false);

        ExploreViewHolder vh = new ExploreViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ExploreViewHolder holder, int position) {

        Art art = this.artworks.get(position);
        //holder.textView.setText(art.getCategory());
        Picasso.get().load(art.getLargeImageUrl()).into(holder.ivMain);
        Picasso.get().load(art.getOwner().getThumbnail()).transform(new CircleTransform()).into(holder.ivOwner);
    }

    @Override
    public int getItemCount() {
        return this.artworks.size();
    }
}