package com.shouxiu.wanandroid.view.recyclerview;

import android.animation.Animator;
import android.view.View;

/**
 * @author yeping
 * @date 2018/3/7 10:44
 * @description ${TODO}
 */
public interface BaseAnimation {
    Animator[] getAnimators(View view);
}