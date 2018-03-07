package com.shouxiu.wanandroid.other;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * @author yeping
 * @date 2018/3/2 14:25
 * @description ${TODO}
 */

public class GlideImageLoader extends ImageLoader{
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load((String) path).into(imageView);
    }
}
