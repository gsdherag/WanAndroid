package com.shouxiu.wanandroid.weex.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;


public class ImageAdapter implements IWXImgLoaderAdapter {

    @Override
    public void setImage(String url, ImageView view, WXImageQuality quality, WXImageStrategy strategy) {
        // 传进来的url为绝对地址
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        if (!TextUtils.isEmpty(url)) {
            try {
                Glide.with(view.getContext()).load(url).into(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
