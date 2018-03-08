package com.shouxiu.wanandroid.simple6.presenter;

import com.shouxiu.wanandroid.network.bean.HomeArticleBean;
import com.shouxiu.wanandroid.network.bean.LzyResponse;
import com.shouxiu.wanandroid.simple6.base.BasePresenter;
import com.shouxiu.wanandroid.simple6.model.HomeArticleModel;
import com.shouxiu.wanandroid.simple6.view.HomeArticleView;

import io.reactivex.functions.Consumer;

/**
 * @author yeping
 * @date 2018/3/7 15:52
 * @description ${TODO}
 */

public class HomeArticlePresenter extends BasePresenter<HomeArticleView> {

    private HomeArticleModel mHomeArticleModel;

    public HomeArticlePresenter() {
        mHomeArticleModel = new HomeArticleModel();
    }

    public void loadArticle(int page) {
        mHomeArticleModel.loadArticle(page, new Consumer<LzyResponse<HomeArticleBean>>() {
            @Override
            public void accept(LzyResponse<HomeArticleBean> homeArticleBeanLzyResponse) throws Exception {
                getView().loadArticleSuccess(homeArticleBeanLzyResponse.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getView().loadArticleFail(throwable.getMessage());
            }
        });
    }
}
