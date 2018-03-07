package com.shouxiu.wanandroid;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.shouxiu.wanandroid.manager.AppletManager;
import com.shouxiu.wanandroid.manager.SessionManager;
import com.squareup.leakcanary.LeakCanary;


/**
 * @创建者 yeping
 * @创建时间 2017/11/21 14:11
 * @描述 ${TODO}
 */

public class MyApp extends Application {
    private static MyApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initARouter();
        AppletManager.getInstance().init(this);
        SessionManager.getInstance().init();
        if (LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(this);
    }

    private void initARouter() {
        if (BuildConfig.DEBUG){
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    /**
     * @return
     * @Description 得到实例
     */
    public static MyApp getInstance() {
        return instance;
    }

    /**
     * @return
     * @Description 得到实例
     */
    public static Context getContext() {
        return getInstance().getApplicationContext();
    }
}
