package com.shouxiu.wanandroid.utils;

import android.annotation.SuppressLint;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.shouxiu.wanandroid.MyApp;


/**
 * Created by jingbin on 2016/12/14.
 * 单例Toast
 */

public class ToastUtil {

    private static Toast mToast;

    @SuppressLint("ShowToast")
    public static void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApp.getContext(), text, Toast.LENGTH_SHORT);
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setText(text);
        mToast.show();
    }

    public static void showToast(@StringRes int text) {
        String string = MyApp.getContext().getResources().getString(text);
        if (mToast == null) {
            mToast = Toast.makeText(MyApp.getContext(), string, Toast.LENGTH_SHORT);
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setText(text);
        mToast.show();
    }

    @SuppressLint("ShowToast")
    public static void showToastLong(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApp.getInstance(), text, Toast.LENGTH_SHORT);
        }
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setText(text);
        mToast.show();
    }

}
