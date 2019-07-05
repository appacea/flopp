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
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import dagger.android.AndroidInjection;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.flipp.flopp.R;
import com.flipp.flopp.common.architecture.Status;
import com.flipp.flopp.di.ViewModelFactory;
import com.flipp.flopp.ui.main.pages.favorites.FavoritesFragment;
import com.flipp.flopp.ui.main.pages.PagerAdapter;
import com.flipp.flopp.ui.main.pages.ViewPagerFragment;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

/**
 * This is the main activity for the app.
 *
 * This activity controls all fragments in the app.
 */
public class MainActivity extends AppCompatActivity {

    //View Pager used in main activity, allows for embedded viewpager
    private ParentViewPager viewPager;
    //Pager adapter used in viewpager
    private PagerAdapter pagerAdapter;
    //Tablayout that is linked to the embedded viewpager in a fragment
    private TabLayout tlMain;
    //Indicator to link favorites and tablayout
    private View vIndicator;
    //Imageview used to display favorites
    private ImageView ivFavorite;
    //Fragment that embeds a viewpager
    private ViewPagerFragment viewPagerFragment;
    //Progress dialog displayed during loading
    private ProgressDialog progressDialog;

    @Inject
    ViewModelFactory viewModelFactory;

    //The viemodel for this view
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));

        tlMain = (TabLayout) findViewById(R.id.tlMain);
        //remove highlight on tabs since we don't want to have a highlight when favorites is selected
        tlMain.setTabTextColors(ContextCompat.getColor(MainActivity.this,R.color.colorAccent),ContextCompat.getColor(MainActivity.this,R.color.colorAccent));

        viewPager = (ParentViewPager) findViewById(R.id.viewPager);
        ivFavorite = (ImageView) findViewById(R.id.ivFavorite);


        //If we click favorites, hide the tab indicator
        //Ideally we would animate the indicator to the favorites position but I had some problems making it seamless.
        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24px);
                tlMain.getTabAt(0).select();
                viewPager.setCurrentItem(0); //move to first page
                tlMain.setSelectedTabIndicatorColor(Color.TRANSPARENT);
            }
        });

        //Setup parent viewpager with two fragments 1) Favorites 2) Embedded View Pager
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new FavoritesFragment(),"");
        viewPagerFragment = new ViewPagerFragment();
        pagerAdapter.addFragment(viewPagerFragment, "");
        viewPager.setAdapter(pagerAdapter);
        tlMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(viewPager.getCurrentItem()==0){
                    viewPager.setCurrentItem(1);
                    tlMain.setSelectedTabIndicatorColor(Color.WHITE);
                    ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24px);
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if(viewPager.getCurrentItem()==0){
                    viewPager.setCurrentItem(1);
                    tlMain.setSelectedTabIndicatorColor(Color.WHITE);
                    ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24px);
                }

            }
        });


        //Get main viewmodel
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        mainViewModel.getAllArt().observe(this, resource -> {
            if(resource.status == Status.LOADING) {
                progressDialog.show();
            }
            else  {
                progressDialog.hide();
            }
        });


        //Trying to replicate tablayout on Flipp
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if(position==1){
                    tlMain.setSelectedTabIndicatorColor(Color.WHITE);
                    ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24px);
                }
                else{
                    tlMain.setSelectedTabIndicatorColor(Color.TRANSPARENT);
                    ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24px);
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //Hide the custom indicator for now, will try to fix it later so that animations are smooth from favorites to tablayout
        vIndicator = findViewById(R.id.vIndicator);
        vIndicator.setVisibility(View.GONE);

        mainViewModel.city.setValue(mainViewModel.getCity());

    }



    /********************************************************************************
     *
     * Menu overrides
     *
     *******************************************************************************/

    /***
     * Create menu and set account icon color
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);
        Drawable drawable = menu.findItem(R.id.account).getIcon();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(this,R.color.colorAccent));
        menu.findItem(R.id.account).setIcon(drawable);

        return true;
    }

    /**
     * Handle options for cities
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miMontreal:
                mainViewModel.setCity("Montreal");
                return true;
            case R.id.miToronto:
                mainViewModel.setCity("Toronto");
                return true;
            case R.id.miOttawa:
                mainViewModel.setCity("Ottawa");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Dismiss progressdialog on destroy to avoid memory leak
     */
    @Override
    protected void onDestroy() {
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        super.onDestroy();
    }
}
