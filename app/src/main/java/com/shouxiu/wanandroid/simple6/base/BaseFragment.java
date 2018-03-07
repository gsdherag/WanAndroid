package com.shouxiu.wanandroid.simple6.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @创建者 yeping
 * @创建时间 2017/9/5 14:04
 * @描述 ${TODO}
 */
public abstract class BaseFragment<V extends BaseView, P extends BasePresenter<V>> extends Fragment {
    private P presenter;
    private V view;
    protected ViewGroup rootView;
    private Unbinder unbinder;
    private boolean mIsVisible;

    public P getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //防止Fragment从新创建会出现重叠
        if (savedInstanceState != null && getTag() != null) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            List<Fragment> list = manager.getFragments();
            if (null != list && list.size() > 0) {
                for (Fragment fragment : list) {
                    if (fragment != null && getTag().equals(fragment.getTag())) {
                        transaction.remove(fragment);
                        break;
                    }
                }
            }
            transaction.commit();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = (ViewGroup) createView(inflater, container, savedInstanceState);
            unbinder = ButterKnife.bind(this, rootView);
            initView(rootView);
        } else {
            ViewGroup parent = ((ViewGroup) rootView.getParent());
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }


    protected abstract int getLayoutId();

    protected abstract void initView(View view);


    protected View createView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), null, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (this.presenter == null) {
            //创建P层
            this.presenter = createPresenter();
        }

        if (this.view == null) {
            //创建V层
            this.view = createView();
        }
        //判定是否为空
        if (this.presenter == null) {
            throw new NullPointerException("presneter不能够为空");
        }
        if (this.view == null) {
            throw new NullPointerException("view不能够为空");
        }
        //绑定
        this.presenter.attachView(this.view);
    }


    protected abstract V createView();

    protected abstract P createPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.presenter.detachView();
        unbinder.unbind();
    }

    public int getStatusHeight(){
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInVisible();
        }
    }

    private void onInVisible() {
    }

    private void onVisible() {
        loadData();
    }

    protected void loadData() {
    }
}
