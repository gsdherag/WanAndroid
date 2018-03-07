package com.shouxiu.wanandroid.network;


import com.shouxiu.wanandroid.network.bean.CollectBean;
import com.shouxiu.wanandroid.network.bean.LzyResponse;
import com.shouxiu.wanandroid.network.bean.SearchBean;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author yeping
 * @date 2018/3/5 13:34
 * @description ${TODO}
 */

public interface SearchService {
    @POST("/article/query/{page}/json")
    Observable<LzyResponse<SearchBean>> searchWithword(@Path("page") int page, @Query("k") String k);

    @POST("/lg/collect/{id}/json")
    Observable<LzyResponse<CollectBean>> collectLink(@Path("id") int id);

    @POST("/lg/uncollect_originId/{id}/json")
    Observable<LzyResponse> uncollectLink(@Path("id") int id);
}
