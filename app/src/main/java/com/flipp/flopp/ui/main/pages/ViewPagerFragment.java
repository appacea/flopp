/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/3/19 8:15 PM
 *
 * Last modified 7/3/19 8:15 PM
 */

package com.flipp.flopp.ui.main.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.flipp.flopp.R;
import com.flipp.flopp.ui.main.MainActivity;
import com.flipp.flopp.ui.main.pages.designs.DesignsFragment;
import com.flipp.flopp.ui.main.pages.drawings.DrawingsFragment;
import com.flipp.flopp.ui.main.pages.explore.ExploreFragment;
import com.flipp.flopp.ui.main.pages.paintings.PaintingsFragment;
import com.flipp.flopp.ui.main.pages.photographs.PhotographsFragment;
import com.flipp.flopp.ui.main.pages.sculptures.SculpturesFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 * Fragment that has embedded ViewPager
 *
 */
public class ViewPagerFragment extends Fragment {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);
        viewPager = view.findViewById(R.id.vpChild);
        pagerAdapter = new PagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new ExploreFragment(),getString(R.string.tab_explore));
        pagerAdapter.addFragment(new PaintingsFragment(),getString(R.string.tab_paintings));
        pagerAdapter.addFragment(new SculpturesFragment(),getString(R.string.tab_sculptures));
        pagerAdapter.addFragment(new DrawingsFragment(),getString(R.string.tab_drawings));
        pagerAdapter.addFragment(new PhotographsFragment(),getString(R.string.tab_photographs));
        pagerAdapter.addFragment(new DesignsFragment(),getString(R.string.tab_designs));
        viewPager.setAdapter(pagerAdapter);

        //Create tabs programmatically so that they are not selected by default.
        TabLayout tabLayout = getActivity().findViewById(R.id.tlMain);
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            tabLayout.addTab(
                    tabLayout.newTab()
                            .setText(pagerAdapter.getPageTitle(i)),false);
        }

        //Link Tablayout to viewpager
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }


    public void setPosition(int position){
        viewPager.setCurrentItem(position);
    }


}
