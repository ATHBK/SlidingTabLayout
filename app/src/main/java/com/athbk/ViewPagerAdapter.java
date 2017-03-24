package com.athbk;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.athbk.slidingtablayout.SlidingTabAdapter;
import com.athbk.slidingtablayout.model.TabInfo;

import java.util.ArrayList;

/**
 * Created by athbk on 3/22/17.
 */

public class ViewPagerAdapter extends SlidingTabAdapter {

    private ArrayList<Fragment> listFragment;
    private ArrayList<TabInfo> listTabInfo;

    public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> listFragment, ArrayList<TabInfo> listTabInfo) {
        super(fm);
        this.listFragment = listFragment;
        this.listTabInfo = listTabInfo;
    }

    @Override
    protected String getTitle(int position) {
        return listTabInfo.get(position).getTitle();
    }

    @Override
    protected int getIcon(int position) {
        if (position == 0) return R.drawable.tab_1_selected;
        else if (position == 1) return R.drawable.tab_2_selected;
//        else if (position == 2) return R.drawable.tab_3_selected;
        else return R.drawable.tab_4_selected;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }
}
