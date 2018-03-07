package com.shouxiu.wanandroid.simple6.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.androidkun.xtablayout.XTabLayout;
import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.simple6.adapter.TabPagerAdapter;
import com.shouxiu.wanandroid.simple6.base.BaseFragment;
import com.shouxiu.wanandroid.simple6.base.BasePresenter;
import com.shouxiu.wanandroid.simple6.base.BaseView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author yeping
 * @date 2018/3/1 9:23
 * @description ${TODO}
 */

public class HomeFragment1 extends BaseFragment<BaseView, BasePresenter<BaseView>> implements BaseView {

    @BindView(R.id.home_tab_layout)
    XTabLayout homeTabLayout;
    @BindView(R.id.home_view_pager)
    ViewPager homeViewPager;
    @BindView(R.id.rl_titleCenter)
    RelativeLayout rlTitleCenter;
    private ArrayList<Fragment> list_fragment;
    private String titleArray[] = {"关注", "热门"};
    private int imageArray[] = {R.mipmap.shang, R.mipmap.xia};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_one;
    }

    @Override
    protected void initView(View view) {
        Home1OneFragment home1OneFragment = new Home1OneFragment();
        Home1TwoFragment home1TwoFragment = new Home1TwoFragment();
        list_fragment = new ArrayList<>();
        list_fragment.add(home1OneFragment);
        list_fragment.add(home1TwoFragment);

        homeTabLayout.addTab(homeTabLayout.newTab().setText(titleArray[0]));
        homeTabLayout.addTab(homeTabLayout.newTab().setText(titleArray[1]));

        TabPagerAdapter fAdapter = new TabPagerAdapter(getActivity(), getActivity().getSupportFragmentManager(), list_fragment, titleArray, imageArray);
        homeViewPager.setAdapter(fAdapter);
        homeTabLayout.setupWithViewPager(homeViewPager);
    }

    @Override
    protected BaseView createView() {
        return this;
    }

    @Override
    protected BasePresenter<BaseView> createPresenter() {
        return new BasePresenter<>();
    }
}
