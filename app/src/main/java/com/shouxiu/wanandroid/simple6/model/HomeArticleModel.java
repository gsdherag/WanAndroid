package com.shouxiu.wanandroid.simple6.model;

import com.shouxiu.wanandroid.network.ApiService;
import com.shouxiu.wanandroid.network.bean.HomeArticleBean;
import com.shouxiu.wanandroid.network.bean.LzyResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeping
 * @date 2018/3/7 15:51
 * @description ${TODO}
 */

public class HomeArticleModel {
    public void loadArticle(int page, Consumer<LzyResponse<HomeArticleBean>> consumer1, Consumer<Throwable> consumer2) {
        ApiService.createHomeService().getArticle(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer1, consumer2);
    }
}
