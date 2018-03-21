package com.shouxiu.wanandroid.simple6.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.bean.TitleBarBean;
import com.shouxiu.wanandroid.callback.ILoadingProgress;
import com.shouxiu.wanandroid.utils.StatusBarUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @创建者 yeping
 * @创建时间 2017/9/5 13:34
 * @描述 ${TODO}
 */

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>> extends AppCompatActivity implements ILoadingProgress {

    private P presenter;

    private V view;

    private TitleBarBean titleBar;

    private TitleBarLauncher titleBarLauncher;
    private ViewGroup activity_title_bar;
    private Unbinder mUnbinder;

    public P getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.presenter == null) {
            this.presenter = createPresenter();
        }

        if (this.view == null) {
            this.view = createView();
        }

        if (this.presenter == null) {
            throw new NullPointerException("presenter不能为空");
        }

        if (this.view == null) {
            throw new NullPointerException("view不能为空");
        }

        this.presenter.attachView(this.view);
    }

    protected abstract V createView();

    protected abstract P createPresenter();

    protected void setContentLayout(int layoutResID, TitleBarLauncher launcher) {
        this.setContentLayout(layoutResID, R.layout.activity_basic_layout, launcher);
        mUnbinder = ButterKnife.bind(this);
    }

    protected void setContentLayout(int layoutResID, int basicLayoutResID, TitleBarLauncher launcher) {
        titleBarLauncher = launcher;

        View parentLayout = View.inflate(this, basicLayoutResID, null);
        View contentView = View.inflate(this, layoutResID, null);
        LinearLayout ll_mainContent = parentLayout.findViewById(R.id.ll_mainContent);
        ll_mainContent.addView(contentView);
        activity_title_bar = parentLayout.findViewById(R.id.activity_title_bar);
        setContentView(parentLayout);
        initTitleBar(parentLayout);
        if (launcher != null) {
            StatusBarUtils.translucentStatusBar(this, false);
            parentLayout.findViewById(R.id.base_root).setPadding(0, getStatusHeight(), 0, 0);
        } else {
            //            StatusBarUtils.setStatusBarLightMode(this,getResources().getColor(R.color.blue_app));
            StatusBarUtils.translucentStatusBar(this, true);
        }
    }

    private void initTitleBar(View layout) {
        titleBar = new TitleBarBean();
        titleBar.setIv_back((ImageView) layout.findViewById(R.id.iv_back));
        titleBar.setIv_menu((ImageView) layout.findViewById(R.id.iv_menuIcon));
        titleBar.setTv_backText((TextView) layout.findViewById(R.id.tv_backText));
        titleBar.setTv_titleText((TextView) layout.findViewById(R.id.tv_title));
        titleBar.setTv_menuText((TextView) layout.findViewById(R.id.tv_menuText));
        titleBar.setV_separator(layout.findViewById(R.id.v_separator));
        titleBar.setV_bottom_divider(layout.findViewById(R.id.v_bottom_divider));
        titleBar.setLeft_container((RelativeLayout) layout.findViewById(R.id.rl_titleLeft));
        titleBar.setCenter_container((RelativeLayout) layout.findViewById(R.id.rl_titleCenter));
        titleBar.setRight_container((RelativeLayout) layout.findViewById(R.id.rl_titleRight));
        titleBar.getV_separator().setVisibility(View.VISIBLE);
        // 设置默认的返回功能
        titleBar.getIv_back().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
                hiddenInput();
            }
        });
        if (titleBarLauncher != null) {
            titleBarLauncher.initTitleBar(titleBar);
        } else {
            disableTitleBar();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.presenter.detachView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
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

    private void disableTitleBar() {
        activity_title_bar.setVisibility(View.GONE);
        titleBar.getV_separator().setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    public void hiddenInput() {
        try {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(BaseActivity.this.getCurrentFocus()
                                    .getWindowToken(),
                            0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface TitleBarLauncher {

        /**
         * 初始化状态栏
         */
        void initTitleBar(TitleBarBean titleBar);

    }
}
