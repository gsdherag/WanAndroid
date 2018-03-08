package com.shouxiu.wanandroid.simple6.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.bean.LoginEvent;
import com.shouxiu.wanandroid.manager.SessionManager;
import com.shouxiu.wanandroid.network.ApiService;
import com.shouxiu.wanandroid.network.bean.ArticleBean;
import com.shouxiu.wanandroid.network.bean.CollectBean;
import com.shouxiu.wanandroid.network.bean.HomeArticleBean;
import com.shouxiu.wanandroid.network.bean.LzyResponse;
import com.shouxiu.wanandroid.simple6.adapter.ArticleAdapter;
import com.shouxiu.wanandroid.simple6.base.BaseFragment;
import com.shouxiu.wanandroid.simple6.presenter.HomeArticlePresenter;
import com.shouxiu.wanandroid.simple6.view.HomeArticleView;
import com.shouxiu.wanandroid.utils.RxBus;
import com.shouxiu.wanandroid.utils.ToastUtil;
import com.shouxiu.wanandroid.view.recyclerview.BaseQuickAdapter;
import com.shouxiu.wanandroid.view.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @创建者 yeping
 * @创建时间 2017/11/22 14:55
 * @描述 ${加载首页文章}
 */

public class Home1OneFragment extends BaseFragment<HomeArticleView, HomeArticlePresenter>
        implements HomeArticleView, BaseQuickAdapter.RequestLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener, ArticleAdapter.OnItemClickListener {

    @BindView(R.id.rv_home_new_article)
    RecyclerView rvHomeNewArticle;
    @BindView(R.id.spl_home_new_article)
    SwipeRefreshLayout splHomeNewArticle;
    @BindView(R.id.fab_top)
    FloatingActionButton fabTop;
    Unbinder unbinder;
    private List<ArticleBean> searchList = new ArrayList<>();
    private ArticleAdapter mArticleAdapter;
    private int page = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_1one;
    }

    @Override
    protected void initView(View view) {
        mArticleAdapter = new ArticleAdapter(searchList);
        rvHomeNewArticle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvHomeNewArticle.setAdapter(mArticleAdapter);
        splHomeNewArticle.setOnRefreshListener(this);
        mArticleAdapter.setOnLoadMoreListener(this);
        mArticleAdapter.setOnItemClickListener(this);
        RxBus.getInstance().toFlowablle(LoginEvent.class).subscribe(new Consumer<LoginEvent>() {
            @Override
            public void accept(LoginEvent loginEvent) throws Exception {
                getPresenter().loadArticle(page);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getPresenter().loadArticle(page);
    }

    @Override
    protected HomeArticleView createView() {
        return this;
    }

    @Override
    protected HomeArticlePresenter createPresenter() {
        return new HomeArticlePresenter();
    }

    @Override
    public void onRefresh() {
        page = 0;
        getPresenter().loadArticle(page);
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        getPresenter().loadArticle(page);
    }

    @Override
    public void loadArticleSuccess(HomeArticleBean data) {
        if (splHomeNewArticle.isRefreshing()) {
            searchList.clear();
            searchList.addAll(data.getDatas());
            mArticleAdapter.addData(searchList);
            splHomeNewArticle.setRefreshing(false);
        } else {
            searchList.addAll(data.getDatas());
            mArticleAdapter.addData(searchList);

            if (searchList == null || searchList.isEmpty() || searchList.size() < 20) {
                mArticleAdapter.loadMoreEnd(false);
            } else {
                mArticleAdapter.loadMoreComplete();
            }
        }
    }

    @Override
    public void loadArticleFail(String message) {
        mArticleAdapter.loadMoreFail();
        Toast.makeText(getContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab_top)
    public void onViewClicked() {
        rvHomeNewArticle.smoothScrollToPosition(0);
    }

    @Override
    public void onItemClick(View view, int position) {
        WebViewActivity.loadUrl(getContext(), searchList.get(position).getLink(), Html.fromHtml(searchList.get(position).getTitle()).toString());

    }

    @Override
    public void onItemLikeClick(View view, final int position, final boolean isCollect) {
        if (!SessionManager.getInstance().isLogin()) {
            ARouter.getInstance().build("/test/login").navigation();
            return;
        }
        if (isCollect) {
            ApiService.createSearchService().uncollectLink(searchList.get(position).getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<LzyResponse>() {
                        @Override
                        public void accept(LzyResponse lzyResponse) throws Exception {
                            searchList.get(position).setCollect(!isCollect);
                            mArticleAdapter.notifyItemChanged(position);
                            ToastUtil.showToast("取消收藏成功");
                        }
                    });
        } else {
            ApiService.createSearchService().collectLink(searchList.get(position).getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<LzyResponse<CollectBean>>() {
                        @Override
                        public void accept(LzyResponse<CollectBean> collectBeanLzyResponse) throws Exception {
                            searchList.get(position).setCollect(!isCollect);
                            mArticleAdapter.notifyItemChanged(position);
                            ToastUtil.showToast("收藏成功");
                        }
                    });
        }
    }
}
