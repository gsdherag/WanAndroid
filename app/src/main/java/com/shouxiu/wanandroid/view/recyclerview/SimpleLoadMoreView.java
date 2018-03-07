package com.shouxiu.wanandroid.view.recyclerview;

import com.shouxiu.wanandroid.R;

/**
 * @author yeping
 * @date 2018/3/7 10:47
 * @description ${TODO}
 */
public final class SimpleLoadMoreView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.quick_view_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}