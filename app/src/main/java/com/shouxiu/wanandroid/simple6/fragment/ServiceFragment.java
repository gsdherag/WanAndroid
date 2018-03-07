package com.shouxiu.wanandroid.simple6.fragment;

import android.content.Context;
import android.view.View;

import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.simple6.base.BaseFragment;
import com.shouxiu.wanandroid.simple6.base.BasePresenter;
import com.shouxiu.wanandroid.simple6.base.BaseView;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;

/**
 * @创建者 yeping
 * @创建时间 2017/11/21 10:39
 * @描述 ${TODO}
 */

public class ServiceFragment extends BaseFragment<BaseView, BasePresenter<BaseView>> implements BaseView, IWXRenderListener {

    private WXSDKInstance mWXSDKInstance;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mWXSDKInstance = new WXSDKInstance(getActivity());
        mWXSDKInstance.registerRenderListener(this);
        mWXSDKInstance.render(getActivity().getPackageName(), WXFileUtils.loadAsset("ceshi.js",
                getContext()),null,null,-1,-1, WXRenderStrategy.APPEND_ASYNC);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service;
    }

    @Override
    protected void initView(View view) {
    }

    @Override
    protected BaseView createView() {
        return this;
    }

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        rootView.addView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWXSDKInstance != null){
            mWXSDKInstance.onActivityResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWXSDKInstance != null){
            mWXSDKInstance.onActivityPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mWXSDKInstance != null){
            mWXSDKInstance.onActivityStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWXSDKInstance != null){
            mWXSDKInstance.onActivityDestroy();
        }
    }
}
