package com.shouxiu.wanandroid.simple6.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.simple6.adapter.GuideAdapter;
import com.shouxiu.wanandroid.simple6.base.BaseActivity;
import com.shouxiu.wanandroid.simple6.base.BasePresenter;
import com.shouxiu.wanandroid.simple6.base.BaseView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @创建者 yeping
 * @创建时间 2017/11/20 14:04
 * @描述 ${引导页}
 */

public class GuideActivity extends BaseActivity<BaseView,BasePresenter<BaseView>> implements BaseView {

    @BindView(R.id.guide)
    ViewPager vp_guide;

    int[] guides = new int[]{R.mipmap.guide_bg1, R.mipmap.guide_bg2, R.mipmap.guide_bg3, R.mipmap.guide_bg4};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        GuideAdapter adapter = new GuideAdapter(guides, this);
        vp_guide.setAdapter(adapter);
    }

    @Override
    protected BaseView createView() {
        return this;
    }

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }
}
