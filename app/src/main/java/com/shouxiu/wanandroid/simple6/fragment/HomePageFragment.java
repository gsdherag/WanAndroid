package com.shouxiu.wanandroid.simple6.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.simple6.activity.AlarmClockActivity;
import com.shouxiu.wanandroid.simple6.adapter.MyFragmentPagerAdapter;
import com.shouxiu.wanandroid.simple6.base.BaseFragment;
import com.shouxiu.wanandroid.simple6.base.BasePresenter;
import com.shouxiu.wanandroid.simple6.base.BaseView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

/**
 * @创建者 yeping
 * @创建时间 2017/11/21 10:39
 * @描述 ${TODO}
 */

public class HomePageFragment extends BaseFragment<BaseView, BasePresenter<BaseView>> implements BaseView, ViewPager.OnPageChangeListener, View.OnClickListener {

    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.view_status)
    View viewStatus;
    @BindView(R.id.ll_title_menu)
    FrameLayout llTitleMenu;
    @BindView(R.id.iv_title_gank)
    ImageView ivTitleGank;
    @BindView(R.id.iv_title_one)
    ImageView ivTitleOne;
    @BindView(R.id.iv_title_dou)
    ImageView ivTitleDou;
    @BindView(R.id.main_vp_content)
    ViewPager mainVpContent;
    @BindView(R.id.ll_title_search)
    FrameLayout llTitleSearch;
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        View navHeadView = navView.inflateHeaderView(R.layout.nav_header_main);
        navHeadView.findViewById(R.id.ll_nav_music_Clock).setOnClickListener(this);
        navHeadView.findViewById(R.id.ll_nav_deedback).setOnClickListener(this);
        ViewGroup.LayoutParams layoutParams = viewStatus.getLayoutParams();
        layoutParams.height = getStatusHeight();
        viewStatus.setLayoutParams(layoutParams);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment1());
        fragments.add(new HomeFragment2());
        fragments.add(new HomeFragment3());

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
        mainVpContent.setAdapter(adapter);
        mainVpContent.setOffscreenPageLimit(2);
        mainVpContent.addOnPageChangeListener(this);
        ivTitleGank.setSelected(true);
        mainVpContent.setCurrentItem(0);
    }

    @Override
    protected BaseView createView() {
        return this;
    }

    @Override
    protected BasePresenter<BaseView> createPresenter() {
        return new BasePresenter<>();
    }

    @OnClick({R.id.ll_title_menu, R.id.iv_title_gank, R.id.iv_title_one, R.id.iv_title_dou, R.id.ll_title_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_menu:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_title_gank:
                if (mainVpContent.getCurrentItem() != 0) {
                    ivTitleGank.setSelected(true);
                    ivTitleDou.setSelected(false);
                    ivTitleOne.setSelected(false);
                    mainVpContent.setCurrentItem(0);
                }
                break;
            case R.id.iv_title_one:
                if (mainVpContent.getCurrentItem() != 1) {
                    ivTitleGank.setSelected(false);
                    ivTitleDou.setSelected(false);
                    ivTitleOne.setSelected(true);
                    mainVpContent.setCurrentItem(1);
                }
                break;
            case R.id.iv_title_dou:
                if (mainVpContent.getCurrentItem() != 2) {
                    ivTitleGank.setSelected(false);
                    ivTitleDou.setSelected(true);
                    ivTitleOne.setSelected(false);
                    mainVpContent.setCurrentItem(2);
                }
                break;
            case R.id.ll_title_search:
                ARouter.getInstance().build("/activity/SearchActivity").navigation(getContext());
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                ivTitleGank.setSelected(true);
                ivTitleDou.setSelected(false);
                ivTitleOne.setSelected(false);
                break;
            case 1:
                ivTitleOne.setSelected(true);
                ivTitleGank.setSelected(false);
                ivTitleDou.setSelected(false);
                break;
            case 2:
                ivTitleDou.setSelected(true);
                ivTitleOne.setSelected(false);
                ivTitleGank.setSelected(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_nav_music_Clock:
                startActivity(new Intent(getContext(), AlarmClockActivity.class));
                break;
            case R.id.ll_nav_deedback:
                RxPermissions rxPermissions = new RxPermissions(getActivity());
                rxPermissions.request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.INTERNET)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) { // 在android 6.0之前会默认返回true

                                } else {
                                    // 未获取权限
                                    Toast.makeText(getActivity(), "您没有授权该权限，请在设置中打开授权", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
        }
    }
}
