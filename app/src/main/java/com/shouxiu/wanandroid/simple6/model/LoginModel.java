package com.shouxiu.wanandroid.simple6.model;


import com.shouxiu.wanandroid.network.ApiService;
import com.shouxiu.wanandroid.network.bean.LoginBean;
import com.shouxiu.wanandroid.network.bean.LzyResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeping
 * @date 2018/2/28 20:34
 * @description ${TODO}
 */

public class LoginModel {
    public void login(String username, String password, Consumer<LzyResponse<LoginBean>> consumer1, Consumer<Throwable> consumer2) {
        ApiService.createHotTopicService().sendLogin(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer1,consumer2);
    }

    public void register(String username, String password, String resPassword){
    }
}
