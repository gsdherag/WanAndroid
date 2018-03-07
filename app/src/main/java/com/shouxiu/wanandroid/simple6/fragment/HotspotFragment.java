package com.shouxiu.wanandroid.simple6.fragment;

import android.view.View;

import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.simple6.base.BaseFragment;
import com.shouxiu.wanandroid.simple6.base.BasePresenter;
import com.shouxiu.wanandroid.simple6.base.BaseView;


/**
 * @创建者 yeping
 * @创建时间 2017/11/22 14:55
 * @描述 ${TODO}
 */

public class HotspotFragment extends BaseFragment<BaseView,BasePresenter<BaseView>> implements BaseView{
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hotspot;
    }

    @Override
    protected void initView(View view) {

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
