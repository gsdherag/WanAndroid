package com.shouxiu.wanandroid.simple6.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.manager.SessionManager;
import com.shouxiu.wanandroid.network.ApiService;
import com.shouxiu.wanandroid.network.bean.ArticleBean;
import com.shouxiu.wanandroid.network.bean.CollectBean;
import com.shouxiu.wanandroid.network.bean.LzyResponse;
import com.shouxiu.wanandroid.network.bean.SecondLevelBean;
import com.shouxiu.wanandroid.simple6.adapter.ArticleAdapter;
import com.shouxiu.wanandroid.simple6.base.BaseFragment;
import com.shouxiu.wanandroid.simple6.base.BasePresenter;
import com.shouxiu.wanandroid.simple6.base.BaseView;
import com.shouxiu.wanandroid.utils.ToastUtil;
import com.shouxiu.wanandroid.view.recyclerview.BaseQuickAdapter;
import com.shouxiu.wanandroid.view.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeping
 * @date 2018/3/9 14:34
 * 二级分类
 */

@Route(path = "/article/ArticleListFragment")
public class ArticleListFragment extends BaseFragment<BaseView, BasePresenter<BaseView>> implements BaseView, SwipeRefreshLayout.OnRefreshListener {

    @Autowired
    public int cid;

    private List<ArticleBean> data = new ArrayList<>();
    private ArticleAdapter mArticleAdapter;
    // 是否正在刷新（用于刷新数据时返回页面不再刷新）
    private boolean mIsLoading = false;

    private boolean mIsPrepared = false;
    private RecyclerView mRvArticleList;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private int page = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article_list;
    }

    @Override
    protected void initView(View view) {
        mRvArticleList = view.findViewById(R.id.rvArticleList);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        mArticleAdapter = new ArticleAdapter(data);
        mRvArticleList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvArticleList.setAdapter(mArticleAdapter);

        mArticleAdapter.setOnItemClickListener(new ArticleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                WebViewActivity.loadUrl(getContext(), data.get(position).getLink(), Html.fromHtml(data.get(position).getTitle()).toString());
            }

            @Override
            public void onItemLikeClick(View view, final int position, final boolean isCollect) {
                if (!SessionManager.getInstance().isLogin()) {
                    ARouter.getInstance().build("/test/login").navigation();
                    return;
                }
                if (isCollect) {
                    ApiService.createSearchService().uncollectLink(data.get(position).getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<LzyResponse>() {
                                @Override
                                public void accept(LzyResponse lzyResponse) throws Exception {
                                    data.get(position).setCollect(!isCollect);
                                    mArticleAdapter.notifyItemChanged(position);
                                    ToastUtil.showToast("取消收藏成功");
                                }
                            });
                } else {
                    ApiService.createSearchService().collectLink(data.get(position).getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<LzyResponse<CollectBean>>() {
                                @Override
                                public void accept(LzyResponse<CollectBean> collectBeanLzyResponse) throws Exception {
                                    data.get(position).setCollect(!isCollect);
                                    mArticleAdapter.notifyItemChanged(position);
                                    ToastUtil.showToast("收藏成功");
                                }
                            });
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mArticleAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                loadArticleData();
            }
        });

        mIsPrepared = true;
        postDelayLoad();

    }

    private void postDelayLoad() {
        synchronized (this) {
            if (!mIsLoading && mIsPrepared) {
                mIsLoading = true;
                mRvArticleList.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadArticleData();
                    }
                }, 150);
            }
        }
    }

    private void loadArticleData() {
        ApiService.createHomeService().getKnowledgeSystemArticles(page, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LzyResponse<SecondLevelBean>>() {
                    @Override
                    public void accept(LzyResponse<SecondLevelBean> response) throws Exception {
                        if (page == 0) {
                            data.clear();
                            mSwipeRefreshLayout.setRefreshing(false);
                            data.addAll(response.getData().getDatas());
                            mArticleAdapter.notifyDataSetChanged();
                        } else {
                            data.addAll(response.getData().getDatas());
                            mArticleAdapter.notifyDataSetChanged();

                            if (data == null || data.isEmpty() || data.size() < 20) {
                                mArticleAdapter.loadMoreEnd(false);
                            } else {
                                mArticleAdapter.loadMoreComplete();
                            }
                        }
                        mIsLoading = false;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mIsLoading = false;
                        showError();
                    }
                });
    }

    @Override
    protected void loadData() {

        if (!mIsVisible || !mIsPrepared) {
            return;
        }

        postDelayLoad();
    }

    @Override
    protected BaseView createView() {
        return this;
    }

    @Override
    protected BasePresenter<BaseView> createPresenter() {
        return new BasePresenter<>();
    }

    @Override
    public void onRefresh() {
        page = 0;
        loadArticleData();
    }
}
