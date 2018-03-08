package com.shouxiu.wanandroid.network;


import com.shouxiu.wanandroid.network.bean.BannerBean;
import com.shouxiu.wanandroid.network.bean.HomeArticleBean;
import com.shouxiu.wanandroid.network.bean.LzyListResponse;
import com.shouxiu.wanandroid.network.bean.LzyResponse;
import com.shouxiu.wanandroid.network.bean.UsefulSitesBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author yeping
 * @date 2018/2/28 14:27
 * @description ${TODO}
 */

public interface HomeService {
    @GET("/banner/json")
    Observable<LzyListResponse<BannerBean>> getBanner();

    @GET("/article/list/{page}/json")
    Observable<LzyResponse<HomeArticleBean>> getArticle(@Path("page") int page);

    @GET("/friend/json")
    Observable<LzyListResponse<UsefulSitesBean>> getUsefulSites();
}
