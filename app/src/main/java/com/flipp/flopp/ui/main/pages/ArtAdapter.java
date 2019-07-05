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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import androidx.recyclerview.widget.RecyclerView;

/***
 * Adapter that displays a piece of Art
 */
public class ArtAdapter extends RecyclerView.Adapter<ArtAdapter.ExploreViewHolder> {

    private List<Art> artworks;
    private final OnArtClickedListener listener;

    /**
     * ViewHolder for Art Card
     */
    public static class ExploreViewHolder extends RecyclerView.ViewHolder {
        public FrameLayout flCard;
        public ImageView ivMain;
        public ImageView ivOwner;
        public TextView tvPrice;
        public TextView tvTitle;
        public CheckBox cbFavorite;
        public TextView tvUntil;

        public ExploreViewHolder(FrameLayout v) {
            super(v);
            flCard = v;
            ivMain = v.findViewById(R.id.ivMain);
            ivOwner = v.findViewById(R.id.ivOwner);
            tvPrice = v.findViewById(R.id.tvPrice);
            tvTitle = v.findViewById(R.id.tvTitle);
            cbFavorite = v.findViewById(R.id.cbFavorite);
            tvUntil = v.findViewById(R.id.tvUntil);
        }

        //Bind data to viewholder
        public void bind(final Art art, final OnArtClickedListener listener) {
            //Load thumbnail then large image
            if(!art.getThumbnailUrl().isEmpty()){
                Picasso.get()
                        .load(art.getThumbnailUrl()) // thumbnail url goes here
                        .into(this.ivMain, new Callback() {
                            @Override
                            public void onSuccess() {
                                Picasso.get().load(art.getLargeImageUrl()).into(ExploreViewHolder.this.ivMain);
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });

            }
            Picasso.get().load(art.getOwner().getThumbnail()).transform(new CircleTransform()).into(this.ivOwner);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onArtClicked(art);
                }
            });
            this.tvPrice.setText(art.getReadablePrice());
            this.tvTitle.setText(art.getTitle());
            this.cbFavorite.setChecked(art.isFavorite());
            this.cbFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onArtFavorite(art,((CompoundButton) view).isChecked());
                }
            });
            this.tvUntil.setText("until "+art.getReadableUntil());
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