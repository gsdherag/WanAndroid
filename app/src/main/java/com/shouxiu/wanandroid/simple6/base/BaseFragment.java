package com.shouxiu.wanandroid.simple6.base;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.callback.PerfectClickListener;

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
    protected boolean mIsVisible;
    private LinearLayout mLlProgressBar;
    private AnimationDrawable mAnimationDawable;
    private LinearLayout mLlErrorRefresh;

    public P getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate");
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
        ARouter.getInstance().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("onCreateView");
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

        mLlProgressBar = rootView.findViewById(R.id.ll_progress_bar);
        ImageView img = rootView.findViewById(R.id.img_progress);
        if (img != null) {
            mAnimationDawable = (AnimationDrawable) img.getDrawable();
            if (!mAnimationDawable.isRunning()) {
                mAnimationDawable.start();
            }
            mLlErrorRefresh = rootView.findViewById(R.id.ll_error_refresh);
            mLlErrorRefresh.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    showLoading();
                    onRefresh();
                }
            });
        }

        return rootView;
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        System.out.println("setUserVisibleHint");
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }
    }

    protected void onInvisible() {
    }

    /**
     * 显示时加载数据,需要这样的使用
     * 注意声明 isPrepared，先初始化
     * 生命周期会先执行 setUserVisibleHint 再执行onActivityCreated
     * 在 onActivityCreated 之后第一次显示加载数据，只加载一次
     */
    protected void loadData() {
    }

    protected void onVisible() {
        loadData();
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

    private void onRefresh() {

    }

    protected void showLoading() {
        if (mLlProgressBar.getVisibility() != View.VISIBLE) {
            mLlProgressBar.setVisibility(View.VISIBLE);
        }
        //开始动画
        if (!mAnimationDawable.isRunning()) {
            mAnimationDawable.start();
        }

        if (mLlErrorRefresh.getVisibility() != View.GONE) {
            mLlErrorRefresh.setVisibility(View.GONE);
        }
    }

    /**
     * 加载失败点击重新加载的状态
     */
    protected void showError() {
        if (mLlProgressBar.getVisibility() != View.GONE) {
            mLlProgressBar.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDawable.isRunning()) {
            mAnimationDawable.stop();
        }
        if (mLlErrorRefresh.getVisibility() != View.VISIBLE) {
            mLlErrorRefresh.setVisibility(View.VISIBLE);
        }
    }

    protected <T extends View> T getView(int id) {
        return (T) getView().findViewById(id);
    }

    protected abstract V createView();

    protected abstract P createPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.presenter.detachView();
        unbinder.unbind();
    }

    public int getStatusHeight() {
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }
}
