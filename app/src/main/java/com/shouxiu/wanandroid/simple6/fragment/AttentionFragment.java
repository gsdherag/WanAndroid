package com.shouxiu.wanandroid.simple6.fragment;

import android.view.View;
import android.widget.Toast;

import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.network.ApiService;
import com.shouxiu.wanandroid.network.bean.BannerBean;
import com.shouxiu.wanandroid.network.bean.LzyListResponse;
import com.shouxiu.wanandroid.other.GlideImageLoader;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @创建者 yeping
 * @创建时间 2017/11/22 14:55
 * @描述 ${TODO}
 */

public class AttentionFragment extends BaseFragment<BaseView, BasePresenter<BaseView>> implements BaseView, OnBannerListener {

    @BindView(R.id.banner)
    Banner banner;
    private List<BannerBean> bannerList = new ArrayList<>();
    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attention;
    }

    @Override
    protected void initView(View view) {
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
                        Toast.makeText(getContext(),"网络连接异常",Toast.LENGTH_SHORT).show();
                    }
                });
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
}
