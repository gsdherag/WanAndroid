package com.shouxiu.wanandroid.simple6.view;

import com.shouxiu.wanandroid.network.bean.HomeArticleBean;
import com.shouxiu.wanandroid.simple6.base.BaseView;

/**
 * @author yeping
 * @date 2018/3/7 15:52
 * @description ${TODO}
 */

public interface HomeArticleView extends BaseView {
    void loadArticleSuccess(HomeArticleBean data);

    void loadArticleFail(String message);
}
