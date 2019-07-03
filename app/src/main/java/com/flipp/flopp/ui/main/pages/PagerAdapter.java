/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/1/19 12:14 PM
 *
 * Last modified 7/1/19 12:14 PM
 */

package com.flipp.flopp.ui.main.pages;

import com.flipp.flopp.ui.main.pages.az.AZFragment;
import com.flipp.flopp.ui.main.pages.explore.ExploreFragment;
import com.flipp.flopp.ui.main.pages.latest.LatestFragment;
import com.flipp.flopp.ui.main.pages.paintings.PaintingsFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ExploreFragment();
            case 1:
                return new PaintingsFragment();
            case 2:
                return new AZFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}