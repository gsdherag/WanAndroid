package com.shouxiu.wanandroid.simple6.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidkun.xtablayout.XTabLayout;
import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.network.ApiService;
import com.shouxiu.wanandroid.network.bean.BannerBean;
import com.shouxiu.wanandroid.network.bean.LzyListResponse;
import com.shouxiu.wanandroid.other.GlideImageLoader;
import com.shouxiu.wanandroid.simple6.adapter.TabPagerAdapter;
import com.shouxiu.wanandroid.simple6.base.BaseFragment;
import com.shouxiu.wanandroid.simple6.base.BasePresenter;
import com.shouxiu.wanandroid.simple6.base.BaseView;
import com.shouxiu.wanandroid.view.webview.WebViewActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeping
 * @date 2018/3/1 9:23
 * @description ${TODO}
 */

public class HomeFragment1 extends BaseFragment<BaseView, BasePresenter<BaseView>> implements BaseView, OnBannerListener {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tab_new_frag)
    XTabLayout homeTabLayout;
    @BindView(R.id.vp_new_frag)
    ViewPager homeViewPager;
    Unbinder unbinder;
    private ArrayList<Fragment> list_fragment;
    private String titleArray[] = {"关注", "热门"};
    private int imageArray[] = {R.mipmap.shang, R.mipmap.xia};
    private List<BannerBean> bannerList = new ArrayList<>();
    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

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
        banner.setOnBannerListener(this);
    }

    @Override
    protected void loadData() {
        super.loadData();
        ApiService.createHomeService().getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LzyListResponse<BannerBean>>() {
                    @Override
                    public void accept(LzyListResponse<BannerBean> bannerBeanLzyResponse) throws Exception {
                        images.clear();
                        titles.clear();
                        List<BannerBean> data = bannerBeanLzyResponse.getData();
                        bannerList.addAll(data);
                        for (int i = 0; i < data.size(); i++) {
                            if (i >= 6) {
                                break;
                            }
                            BannerBean datum = data.get(i);
                            images.add(datum.getImagePath());
                            titles.add(datum.getTitle());
                        }
                        banner.setImageLoader(new GlideImageLoader())
                                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                                .setImages(images)
                                .setBannerAnimation(Transformer.Default)
                                .setBannerTitles(titles)
                                .isAutoPlay(true)
                                .setDelayTime(5000)
                                .start();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    @Override
    public void OnBannerClick(int position) {
        WebViewActivity.loadUrl(getContext(), bannerList.get(position).getUrl(), bannerList.get(position).getTitle());
    }

    @Override
    protected BaseView createView() {
        return this;
    }

    @Override
    protected BasePresenter<BaseView> createPresenter() {
        return new BasePresenter<>();
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
}
