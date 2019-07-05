/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/4/19 11:36 AM
 *
 * Last modified 7/4/19 11:36 AM
 */

package com.flipp.flopp.ui.main;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.flipp.flopp.R;
import com.flipp.flopp.data.art.local.Art;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import dagger.android.AndroidInjection;

public class DetailActivity extends AppCompatActivity {

    private Art art;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AndroidInjection.inject(this);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ivArt = findViewById(R.id.ivArt);
        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            art = extras.getParcelable("Art");
            Picasso.get().load(art.getLargeImageUrl()).into(ivArt);

            TextView tvMedium = (TextView) findViewById(R.id.tvMedium);
            TextView tvCategory = (TextView) findViewById(R.id.tvCategory);
            TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
            TextView tvPrice = (TextView) findViewById(R.id.tvPrice);

            tvMedium.setText(art.getMedium());
            tvTitle.setText(art.getTitle());
            tvCategory.setText(art.getCategory());
            tvPrice.setText(art.getReadablePrice());
        }


    }
}
