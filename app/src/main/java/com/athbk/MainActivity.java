package com.athbk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.athbk.slidingtablayout.SlidingTabLayout;
import com.athbk.slidingtablayout.TabLayout;
import com.athbk.slidingtablayout.model.TabInfo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
//    private SlidingTabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
//        tabLayout = (SlidingTabLayout) findViewById(R.id.tabLayout);


        ArrayList<TabInfo> listTab = new ArrayList<>();
        TabInfo tabInfo1 = new TabInfo("Tab 1");
        TabInfo tabInfo2 = new TabInfo("Tab 2");
        TabInfo tabInfo3 = new TabInfo("Tab 3");
//        TabInfo tabInfo4 = new TabInfo("TAB 4");
//        TabInfo tabInfo5 = new TabInfo("TAB 5");
//        TabInfo tabInfo6 = new TabInfo("TAB 6");
//        TabInfo tabInfo7 = new TabInfo("TAB 7");
//        TabInfo tabInfo8 = new TabInfo("TAB 8");
//        TabInfo tabInfo9 = new TabInfo("TAB 9");
//        TabInfo tabInfo10 = new TabInfo("TAB 10");


        listTab.add(tabInfo1);
        listTab.add(tabInfo2);
        listTab.add(tabInfo3);
//        listTab.add(tabInfo4);
//        listTab.add(tabInfo5);
//        listTab.add(tabInfo6);
//        listTab.add(tabInfo7);
//        listTab.add(tabInfo8);
//        listTab.add(tabInfo9);
//        listTab.add(tabInfo10);


        ArrayList<Fragment> listFragment = new ArrayList<>();
        listFragment.add(ItemFragment.newInstance());
        listFragment.add(ItemFragment.newInstance());
        listFragment.add(ItemFragment.newInstance());
//        listFragment.add(ItemFragment.newInstance());
//        listFragment.add(ItemFragment.newInstance());
//        listFragment.add(ItemFragment.newInstance());
//        listFragment.add(ItemFragment.newInstance());
//        listFragment.add(ItemFragment.newInstance());
//        listFragment.add(ItemFragment.newInstance());
//        listFragment.add(ItemFragment.newInstance());


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), listFragment, listTab);
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager, adapter);

    }
}
