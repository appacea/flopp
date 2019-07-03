/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/1/19 12:11 PM
 *
 * Last modified 7/1/19 12:10 PM
 */

package com.flipp.flopp.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import dagger.android.AndroidInjection;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.flipp.flopp.R;
import com.flipp.flopp.common.architecture.Resource;
import com.flipp.flopp.common.architecture.Status;
import com.flipp.flopp.di.ViewModelFactory;
import com.flipp.flopp.ui.main.pages.PagerAdapter;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private static int SCROLLABLE_TABS_POSITION = 1;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tlMain;
    private TabLayout tlScroll;

    @Inject
    ViewModelFactory viewModelFactory;

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AndroidInjection.inject(this);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);


        tlMain = (TabLayout) findViewById(R.id.tlMain);
        viewPager = (ViewPager) findViewById(R.id.viewPager);



        TabLayout.Tab tab = tlMain.getTabAt(SCROLLABLE_TABS_POSITION);
        View v = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab.setCustomView(v);



        View root = tlMain.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.colorAccent));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlMain));

        tlMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition()==0){
                    TabLayout.Tab tab1 = tlScroll.getTabAt(0);
                    tab1.select();
                    tlScroll.getTabSelectedIndicator().setVisible(false,true);
                  //  tlScroll.setSelectedTabIndicatorHeight(0);
                    tlScroll.setSelectedTabIndicatorColor(Color.TRANSPARENT);

                }
                tab.select();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        LinearLayout layout = ((LinearLayout) ((LinearLayout) tlMain.getChildAt(0)).getChildAt(0));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
        layoutParams.weight = 0f;
        layout.setMinimumWidth(200);
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layout.setLayoutParams(layoutParams);



        tlScroll = v.findViewById(R.id.tlScroll);
        tlScroll.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tlScroll.setSelectedTabIndicatorColor(Color.WHITE);
                viewPager.setCurrentItem(SCROLLABLE_TABS_POSITION);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);

        mainViewModel.getAllArt().observe(this, resource -> {
             if(resource.status == Status.LOADING) {
               // displayLoader();

            } else if(!resource.data.isEmpty()) {
               // updateMoviesList(resource.data);

            } else {
                 //handleErrorResponse();
             }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }
}
