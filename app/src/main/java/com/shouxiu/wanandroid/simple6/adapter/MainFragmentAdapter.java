package com.shouxiu.wanandroid.simple6.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.shouxiu.wanandroid.bean.TabBean;

import java.util.ArrayList;

public class MainFragmentAdapter extends FragmentPagerAdapter {

    /**
     * mTabs
     */
    private final ArrayList<TabBean> mTabs;

    /**
     * 当前的fragment
     */
    private Fragment mCurrentFragment;

    public MainFragmentAdapter(FragmentManager fm, ArrayList<TabBean> tabs) {
        super(fm);
        mTabs = tabs;
    }

    @Override
    public Fragment getItem(int pos) {
        Fragment fragment = null;
        if (mTabs != null && pos < mTabs.size()) {
            TabBean tab = mTabs.get(pos);
            if (tab == null)
                return null;
            fragment = tab.createFragment();
        }
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mTabs == null ? 0 : mTabs.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TabBean tab = mTabs.get(position);
        Fragment fragment = (Fragment) super.instantiateItem(container,
                position);
        tab.fragment = fragment;
        return fragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment = (Fragment) object;
        super.setPrimaryItem(container, position, object);
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }
}