package com.shouxiu.wanandroid.simple6.presenter;


import com.shouxiu.wanandroid.simple6.base.BasePresenter;
import com.shouxiu.wanandroid.simple6.model.LoginModel_6;
import com.shouxiu.wanandroid.simple6.view.LoginView;

/**
 * @创建者 yeping
 * @创建时间 2017/9/5 10:02
 * @描述 ${TODO}
 */

public class LoginPresenter extends BasePresenter<LoginView> {
    private LoginModel_6 loginModel;

    public LoginPresenter() {
        this.loginModel = new LoginModel_6();
    }

    public void login(String name) {
//        loginModel.login(name, new StringCallback() {
//            @Override
//            public void onSuccess(Response<String> response) {
//                getView().onLoginResult(response.body());
//            }
//        });
    }
}
