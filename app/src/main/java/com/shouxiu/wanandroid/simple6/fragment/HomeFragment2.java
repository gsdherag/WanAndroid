package com.shouxiu.wanandroid.simple6.fragment;

import android.view.View;

import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.simple6.base.BaseFragment;
import com.shouxiu.wanandroid.simple6.base.BasePresenter;
import com.shouxiu.wanandroid.simple6.base.BaseView;


/**
 * @author yeping
 * @date 2018/3/1 9:23
 * @description ${TODO}
 */

public class HomeFragment2 extends BaseFragment<BaseView,BasePresenter<BaseView>> implements BaseView{
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_two;
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
