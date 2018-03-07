package com.shouxiu.wanandroid.simple6.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.shouxiu.wanandroid.MainActivity;
import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.cons.Constant;
import com.shouxiu.wanandroid.simple6.base.BaseActivity;
import com.shouxiu.wanandroid.simple6.base.BasePresenter;
import com.shouxiu.wanandroid.simple6.base.BaseView;
import com.shouxiu.wanandroid.utils.SpUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @创建者 yeping
 * @创建时间 2017/11/20 13:43
 * @描述 ${闪屏页}
 */

public class SplashActivity extends BaseActivity<BaseView, BasePresenter<BaseView>> implements BaseView {

    private Disposable mSubscribe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        final boolean isFirst = SpUtils.getInstance(Constant.SHARED_NAME).getBoolean(Constant.KEY_FIRST_USER, true);
        mSubscribe = Observable.interval(3, 3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Intent intent;
                        if (isFirst) {
                            intent = new Intent(SplashActivity.this, GuideActivity.class);
                            SpUtils.getInstance(Constant.SHARED_NAME).put(Constant.KEY_FIRST_USER, false, true);
                        } else {
                            intent = new Intent(SplashActivity.this, MainActivity.class);
                        }
                        startActivity(intent);
                        finish();
                    }
                });
    }

    @Override
    protected BaseView createView() {
        return this;
    }

    @Override
    protected BasePresenter<BaseView> createPresenter() {
        return new BasePresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscribe != null && !mSubscribe.isDisposed()) {
            mSubscribe.dispose();
        }
    }
}
