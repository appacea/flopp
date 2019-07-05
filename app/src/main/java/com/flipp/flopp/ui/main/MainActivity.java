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

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.flipp.flopp.R;
import com.flipp.flopp.common.architecture.Status;
import com.flipp.flopp.di.ViewModelFactory;
import com.flipp.flopp.ui.main.pages.favorites.FavoritesFragment;
import com.flipp.flopp.ui.main.pages.PagerAdapter;
import com.flipp.flopp.ui.main.pages.ViewPagerFragment;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private static int SCROLLABLE_TABS_POSITION = 1;
    private ParentViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tlMain;
    private TabLayout tlScroll;
    private View vIndicator;
    private View vIndicatorAnimate;
        private int indicatorPosition;
    private ImageView ivFavorite;
    private boolean automatedMove;
    private int position;
    private boolean blockMove;
    private ViewPagerFragment viewPagerFragment;

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
        viewPager = (ParentViewPager) findViewById(R.id.viewPager);
        ivFavorite = (ImageView) findViewById(R.id.ivFavorite);

    //viewPager.setChildId(R.id.vpChild);
        automatedMove = false;
        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(position>=1){
                    vIndicator.setVisibility(View.GONE);
                    vIndicatorAnimate.setVisibility(View.GONE);
                    automatedMove = true;
                    int[] location = new int[2];
                    LinearLayout layout = ((LinearLayout) ((LinearLayout) tlMain.getChildAt(0)).getChildAt(tlMain.getSelectedTabPosition()));
                    layout.getLocationOnScreen(location);
                    tlMain.setSelectedTabIndicatorColor(Color.TRANSPARENT);

                    TranslateAnimation anim = new TranslateAnimation(location[0],0,0,0);
                    anim.setDuration(400);
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            vIndicator.setVisibility(View.VISIBLE);
                            //vIndicatorAnimate.setVisibility(View.GONE);

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                   // anim.setFillAfter(true);
                    vIndicatorAnimate.startAnimation(anim);
                    tlMain.getTabAt(0).select();
                   // viewPagerFragment.setPosition(0);

                    viewPager.setCurrentItem(0); //move to first page
                }
                else{



                }


                position = 0;
//                position = 0;
//                automatedMove = true;
//                Rect indicatorRect = tlMain.getTabSelectedIndicator().getBounds();

               // viewPager.setCurrentItem(position);




            }
        });
//        TabLayout.Tab tab = tlMain.getTabAt(SCROLLABLE_TABS_POSITION);
//        View v = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        tab.setCustomView(v);



//        View root = tlMain.getChildAt(0);
//        if (root instanceof LinearLayout) {
//            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
//            GradientDrawable drawable = new GradientDrawable();
//            drawable.setColor(getResources().getColor(R.color.colorAccent));
//            drawable.setSize(2, 1);
//            ((LinearLayout) root).setDividerPadding(10);
//            ((LinearLayout) root).setDividerDrawable(drawable);
//        }


        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
//        pagerAdapter.addFragment(new FavoritesFragment(), getString(R.string.tab_explore));
//        pagerAdapter.addFragment(new ExploreFragment(), getString(R.string.tab_explore));
//        pagerAdapter.addFragment(new PaintingsFragment(), getString(R.string.tab_paintings));
//        pagerAdapter.addFragment(new SculpturesFragment(), getString(R.string.tab_sculptures));
//        pagerAdapter.addFragment(new DrawingsFragment(), getString(R.string.tab_drawings));
//        pagerAdapter.addFragment(new PhotographsFragment(), getString(R.string.tab_photographs));
//        pagerAdapter.addFragment(new DesignsFragment(), getString(R.string.tab_designs));

        pagerAdapter.addFragment(new FavoritesFragment(),"1");

        viewPagerFragment = new ViewPagerFragment();
        pagerAdapter.addFragment(viewPagerFragment, "2");
     //   viewPagerFragment.bindTabLayout(tlMain);


        viewPager.setAdapter(pagerAdapter);

        tlMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                move(tab);


//                int[] location = new int[2];
//                LinearLayout layout = ((LinearLayout) ((LinearLayout) tlMain.getChildAt(0)).getChildAt(tlMain.getSelectedTabPosition()));
//                layout.getLocationOnScreen(location);
//
//
//                TranslateAnimation anim = new TranslateAnimation(0,location[0],0,0);
//                anim.setDuration(400);
//                anim.setFillAfter(false);
//                anim.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });
//                vIndicator.startAnimation(anim);



//                if(tab.getPosition() != 0){
//                    tlMain.setSelectedTabIndicatorColor(Color.WHITE);
//                }
//                else{
//                    if(!automatedMove){
//                        tlMain.setSelectedTabIndicatorColor(Color.WHITE);
//                        vIndicator.setVisibility(View.INVISIBLE);
//                    }
//                    else automatedMove = false;
//                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //If we are on favorites then animate:


                move(tab);

            }
        });




//       // viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlMain));
//     //   tlMain.setupWithViewPager(viewPager);
//        tlMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//                position = tab.getPosition()+1;
//
//                viewPager.setCurrentItem(position);
//
//
////                mainViewModel.filterModel("Sculpture");
////
////                viewPager.setCurrentItem(tab.getPosition());
//
////                if(tab.getPosition()==0){
////                    TabLayout.Tab tab1 = tlScroll.getTabAt(0);
////                    tab1.select();
////                    tlScroll.getTabSelectedIndicator().setVisible(false,true);
////                  //  tlScroll.setSelectedTabIndicatorHeight(0);
////                    tlScroll.setSelectedTabIndicatorColor(Color.TRANSPARENT);
////
////                }
////                tab.select();
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                if(position==0){
//                    position = tab.getPosition()+1;
//                    viewPager.setCurrentItem(position);
//                }
//
//            }
//        });

//        LinearLayout layout = ((LinearLayout) ((LinearLayout) tlMain.getChildAt(0)).getChildAt(0));
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
//        layoutParams.weight = 0f;
//        layout.setMinimumWidth(200);
//        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
//        layout.setLayoutParams(layoutParams);



//        tlScroll = v.findViewById(R.id.tlScroll);
//        tlScroll.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                tlScroll.setSelectedTabIndicatorColor(Color.WHITE);
//                viewPager.setCurrentItem(SCROLLABLE_TABS_POSITION);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });



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

        mainViewModel.city.setValue(mainViewModel.getCity());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {




//                if(blockMove){
//                    return;
//                }

                //Show only parent indicator
//                if((position==0 || position==1)&& vIndicator.getVisibility()==View.INVISIBLE){
//                    vIndicator.setVisibility(View.VISIBLE);
//                    tlMain.setSelectedTabIndicatorColor(Color.TRANSPARENT);
//                }


                vIndicator.setVisibility(View.VISIBLE);
                tlMain.setSelectedTabIndicatorColor(Color.TRANSPARENT);

                LinearLayout layout = ((LinearLayout) ((LinearLayout) tlMain.getChildAt(0)).getChildAt(0));
                LinearLayout.LayoutParams layoutParamsTabOne = (LinearLayout.LayoutParams) layout.getLayoutParams();


                LinearLayout.LayoutParams layoutParamsIndicator = (LinearLayout.LayoutParams) vIndicator.getLayoutParams();

                int margin = (int) (positionOffset*layoutParamsIndicator.width);
                if(margin>layoutParamsIndicator.width){
                    margin = layoutParamsIndicator.width;
                }
                if(margin>0){
                    layoutParamsIndicator.leftMargin = margin;
                }
                vIndicator.setLayoutParams(layoutParamsIndicator);
            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("animate");
                MainActivity.this.position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                if(state== ViewPager.SCROLL_STATE_IDLE){
//                    if(position==1){
//                        vIndicator.setVisibility(View.INVISIBLE);
//                        tlMain.setSelectedTabIndicatorColor(Color.WHITE);
//                    }
//                    else{
//
//                        vIndicator.setVisibility(View.VISIBLE);
//                        tlMain.setSelectedTabIndicatorColor(Color.TRANSPARENT);
//                    }
//                }
//                if(state== ViewPager.SCROLL_STATE_DRAGGING){
//
//                    vIndicator.setVisibility(View.VISIBLE);
//                }
            }
        });

        vIndicator = findViewById(R.id.vIndicator);
        vIndicatorAnimate= findViewById(R.id.vIndicatorAnimate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar, menu);

        Drawable drawable = menu.findItem(R.id.account).getIcon();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(this,R.color.colorAccent));
        menu.findItem(R.id.account).setIcon(drawable);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.miAnywhere:
                mainViewModel.setCity("Anywhere");
                return true;
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

    private void move(TabLayout.Tab tab){
        if(automatedMove){
            automatedMove = false;
            return;
        }


        int toPosition = tab.getPosition() +1;

        blockMove = false;
        if(position==0){
            if(toPosition==1){
                //TabLayout.TabView v = tlMain.getTabAt(0).view.
                viewPager.setCurrentItem(1); //move to second page
            }
            else{
                int[] location = new int[2];
                LinearLayout layout = ((LinearLayout) ((LinearLayout) tlMain.getChildAt(0)).getChildAt(toPosition-1));
                layout.getLocationOnScreen(location);
                TranslateAnimation anim = new TranslateAnimation(0,location[0],0,0);
                anim.setDuration(100);
                // anim.setFillAfter(false);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                      //  tlMain.setSelectedTabIndicatorColor(Color.TRANSPARENT);
                      //  vIndicator.setVisibility(View.VISIBLE);
                      //  blockMove = true;
                      //  viewPager.setCurrentItem(1,false); //move to second page

                        vIndicatorAnimate.setVisibility(View.VISIBLE);
                        vIndicator.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        tlMain.setSelectedTabIndicatorColor(Color.WHITE);
                        vIndicatorAnimate.setVisibility(View.INVISIBLE);
                        viewPager.setCurrentItem(tab.getPosition());
                      //  blockMove = false;

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                vIndicatorAnimate.startAnimation(anim);
            }

        }
        else{
            tlMain.setSelectedTabIndicatorColor(Color.WHITE);
            vIndicator.setVisibility(View.INVISIBLE);
        }


        position = toPosition;
    }


}
