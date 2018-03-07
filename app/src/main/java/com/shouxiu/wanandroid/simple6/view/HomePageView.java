package com.shouxiu.wanandroid.simple6.view;


import com.shouxiu.wanandroid.network.bean.LoginBean;
import com.shouxiu.wanandroid.simple6.base.BaseView;

/**
 * @author yeping
 * @date 2018/2/28 20:31
 * @description ${TODO}
 */

public interface HomePageView extends BaseView {
    void loginSuccess(LoginBean result);

    void loginFail(String result);
}
