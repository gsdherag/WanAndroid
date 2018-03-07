package com.shouxiu.wanandroid.manager;


import android.text.TextUtils;

import com.shouxiu.wanandroid.cons.Constant;
import com.shouxiu.wanandroid.network.bean.LoginBean;
import com.shouxiu.wanandroid.utils.JsonUtils;
import com.shouxiu.wanandroid.utils.SpUtils;

/**
 * 会话管理
 */
public class SessionManager{

    /**
     * 单例类
     */
    private static SessionManager instance;

    /**
     * 用户信息
     */
    public LoginBean loginInfo;


    synchronized public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void init() {

        //登录请求返回info
        loginInfo = new LoginBean();
        //账户查询返回info
    }

    public void saveLoginInfo(LoginBean resp) {
        loginInfo = resp;
    }

    public void saveInfoFromSp(){
        String login = SpUtils.getInstance(Constant.SHARED_NAME).getString(Constant.KEY_SESSION_LOGIN);
        if (!TextUtils.isEmpty(login)){
            LoginBean loginInfo = JsonUtils.fromJsonString(login, LoginBean.class);
            this.loginInfo = loginInfo;
        }
    }

    /**
     * 判断用户是否登录
     */
    public boolean isLogin() {
        return !TextUtils.isEmpty(loginInfo.getUsername());
    }

    /**
     * 清除用户信息
     */
    public void clear() {
        loginInfo = new LoginBean();
    }

}
