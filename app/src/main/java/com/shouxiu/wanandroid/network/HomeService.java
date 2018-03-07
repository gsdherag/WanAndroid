package com.shouxiu.wanandroid.network;


import com.shouxiu.wanandroid.network.bean.BannerBean;
import com.shouxiu.wanandroid.network.bean.LzyListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author yeping
 * @date 2018/2/28 14:27
 * @description ${TODO}
 */

public interface HomeService {
    @GET("/banner/json")
    Observable<LzyListResponse<BannerBean>> getBanner();
//    @POST("/user/register")
//    Observable<LzyResponse<LoginBean>> sendRegister(@Query("username") String username, @Query("password") String password, @Query("repassword") String repassword);
}
