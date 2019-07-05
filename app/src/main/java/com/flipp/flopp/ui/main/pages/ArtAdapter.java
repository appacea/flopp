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
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.flipp.flopp.R;
import com.flipp.flopp.data.art.local.Art;
import com.flipp.flopp.tools.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ArtAdapter extends RecyclerView.Adapter<ArtAdapter.ExploreViewHolder> {


    private List<Art> artworks;




    private final OnArtClickedListener listener;

    public static class ExploreViewHolder extends RecyclerView.ViewHolder {
        public FrameLayout flCard;
        public ImageView ivMain;
        public ImageView ivOwner;
        public TextView tvPrice;
        public TextView tvTitle;
        public CheckBox cbFavorite;
        public ExploreViewHolder(FrameLayout v) {
            super(v);
            flCard = v;
            ivMain = v.findViewById(R.id.ivMain);
            ivOwner = v.findViewById(R.id.ivOwner);
            tvPrice = v.findViewById(R.id.tvPrice);
            tvTitle = v.findViewById(R.id.tvTitle);
            cbFavorite = v.findViewById(R.id.cbFavorite);
        }

        public void bind(final Art art, final OnArtClickedListener listener) {
            Picasso.get().load(art.getLargeImageUrl()).into(this.ivMain);
            Picasso.get().load(art.getOwner().getThumbnail()).transform(new CircleTransform()).into(this.ivOwner);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onArtClicked(art);
                }
            });
            this.tvPrice.setText(art.getReadablePrice());
            this.tvTitle.setText(art.getTitle());
            this.cbFavorite.setChecked(art.isFavorite());
            this.cbFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    listener.onArtFavorite(art,isChecked);

                }
            });
        }
    }

    public ArtAdapter(List<Art> artworks, OnArtClickedListener listener) {
        this.artworks = artworks;
        this.listener = listener;
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
            holder.bind(artworks.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return this.artworks.size();
    }


    public interface OnArtClickedListener {
        void onArtClicked(Art art);
        void onArtFavorite(Art art, boolean isFavorite);
    }
}