package com.shouxiu.wanandroid.network;

import com.shouxiu.wanandroid.network.bean.LoginBean;
import com.shouxiu.wanandroid.network.bean.LzyResponse;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author yeping
 * @date 2018/2/28 14:27
 * @description ${TODO}
 */

public interface UserService {
    @POST("/user/login")
    Observable<LzyResponse<LoginBean>> sendLogin(@Query("username") String username, @Query("password") String password);
    @POST("/user/register")
    Observable<LzyResponse<LoginBean>> sendRegister(@Query("username") String username, @Query("password") String password, @Query("repassword") String repassword);
}
