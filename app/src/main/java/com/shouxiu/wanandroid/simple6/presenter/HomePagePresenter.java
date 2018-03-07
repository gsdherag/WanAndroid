package com.shouxiu.wanandroid.simple6.presenter;


import com.shouxiu.wanandroid.network.bean.LoginBean;
import com.shouxiu.wanandroid.network.bean.LzyResponse;
import com.shouxiu.wanandroid.simple6.base.BasePresenter;
import com.shouxiu.wanandroid.simple6.model.HomePageModel;
import com.shouxiu.wanandroid.simple6.view.HomePageView;

import io.reactivex.functions.Consumer;

/**
 * @author yeping
 * @date 2018/2/28 20:30
 * @description ${TODO}
 */

public class HomePagePresenter extends BasePresenter<HomePageView> {
    private HomePageModel mModel;

    public HomePagePresenter() {
        mModel = new HomePageModel();
    }

    public void login(String username, String password) {
        mModel.login(username, password, new Consumer<LzyResponse<LoginBean>>() {
            @Override
            public void accept(LzyResponse<LoginBean> loginBeanLzyResponse) throws Exception {
                getView().loginSuccess(loginBeanLzyResponse.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getView().loginFail(throwable.getMessage());
            }
        });
    }
}
