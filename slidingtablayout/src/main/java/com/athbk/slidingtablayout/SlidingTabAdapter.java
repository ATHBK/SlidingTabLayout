package com.athbk.slidingtablayout;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by athbk on 3/22/17.
 */

public abstract class SlidingTabAdapter extends FragmentPagerAdapter {

    protected abstract String getTitle(int position);

    protected abstract int getIcon(int position);

    public SlidingTabAdapter(FragmentManager fm) {
        super(fm);
    }
}
