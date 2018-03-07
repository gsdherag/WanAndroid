package com.shouxiu.wanandroid.utils;

import android.util.Log;

import com.shouxiu.wanandroid.BuildConfig;
import com.shouxiu.wanandroid.cons.BuildType;


public class UmsLog {

    private static String TAG = "shouxiu";

    static private void println(int priority, String msg, Object... params) {
        if (UmsStringUtils.isNotBlank(msg) && params != null) {
            for (Object param : params) {
                try {
                    msg = msg.replaceFirst("[{][}]", param.toString());
                } catch (Exception e) {
                }
            }
        }
        Log.println(priority, TAG, msg);
    }

    static public void v(String msg) {
        if (!BuildType.RELEASE.equalsIgnoreCase(BuildConfig.BUILD_TYPE))
            println(Log.VERBOSE, msg);
    }

    static public void v(String msg, Object... params) {
        if (!BuildType.RELEASE.equalsIgnoreCase(BuildConfig.BUILD_TYPE))
            println(Log.VERBOSE, msg, params);
    }

    static public void d(String msg) {
        if (!BuildType.RELEASE.equalsIgnoreCase(BuildConfig.BUILD_TYPE))
            println(Log.DEBUG, msg);
    }

    static public void d(String msg, Object... params) {
        if (!BuildType.RELEASE.equalsIgnoreCase(BuildConfig.BUILD_TYPE))
            println(Log.DEBUG, msg, params);
    }

    static public void i(String msg) {
        println(Log.INFO, msg);
    }

    static public void i(String msg, Object... params) {
        println(Log.INFO, msg, params);
    }

    static public void e(String msg) {
        println(Log.ERROR, msg);
    }

    static public void e(String msg, Throwable t) {
        e(msg);
        if (t != null) {
            t.printStackTrace();
        }
    }

    static public void e(String msg, Throwable t, Object... params) {
        e(msg, params);
        if (t != null) {
            t.printStackTrace();
        }
    }

    static public void e(String msg, Object... params) {
        println(Log.ERROR, msg, params);
    }

    static public void w(String msg) {
        println(Log.WARN, msg);
    }

    static public void w(String msg, Object... params) {
        println(Log.WARN, msg, params);
    }

}
