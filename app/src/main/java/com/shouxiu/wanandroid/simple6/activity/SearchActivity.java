package com.shouxiu.wanandroid.simple6.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.bean.LoginEvent;
import com.shouxiu.wanandroid.manager.SessionManager;
import com.shouxiu.wanandroid.network.ApiService;
import com.shouxiu.wanandroid.network.bean.CollectBean;
import com.shouxiu.wanandroid.network.bean.LzyResponse;
import com.shouxiu.wanandroid.network.bean.SearchBean;
import com.shouxiu.wanandroid.simple6.adapter.SearchAdapter;
import com.shouxiu.wanandroid.simple6.base.BaseActivity;
import com.shouxiu.wanandroid.simple6.base.BasePresenter;
import com.shouxiu.wanandroid.simple6.base.BaseView;
import com.shouxiu.wanandroid.utils.RxBus;
import com.shouxiu.wanandroid.utils.ToastUtil;
import com.shouxiu.wanandroid.view.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeping
 * @date 2018/3/5 10:14
 * @description ${TODO}
 */
@Route(path = "/activity/SearchActivity")
public class SearchActivity extends BaseActivity<BaseView, BasePresenter<BaseView>> implements BaseView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_searchList)
    RecyclerView rvSearchList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private List<SearchBean.ArticleBean> searchList = new ArrayList<>();
    private SearchAdapter searchAdapter;
    private SearchView mSearchView;
    private String searchWord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        ButterKnife.bind(this);
        initView();
        initToolBar();
    }

    /**
     * 初始化toolbar
     */
    private void initToolBar() {
        if (toolbar == null) {
            throw new NullPointerException("toolbar can not be null");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /**toolbar除掉阴影*/
        getSupportActionBar().setElevation(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(0);
        }
    }

    private void initView() {
        searchAdapter = new SearchAdapter(this, searchList);
        rvSearchList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvSearchList.setAdapter(searchAdapter);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryWord(searchWord);

            }
        });
        searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                WebViewActivity.loadUrl(SearchActivity.this, searchList.get(position).getLink(), Html.fromHtml(searchList.get(position).getTitle()).toString());
            }

            @Override
            public void onItemLikeClick(View view, final int position, final boolean isCollect) {
                if (!SessionManager.getInstance().isLogin()){
                    ARouter.getInstance().build("/test/login").navigation();
                    return;
                }
                if (isCollect){
                    ApiService.createSearchService().uncollectLink(searchList.get(position).getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<LzyResponse>() {
                                @Override
                                public void accept(LzyResponse lzyResponse) throws Exception {
                                    searchList.get(position).setCollect(!isCollect);
                                    searchAdapter.notifyItemChanged(position);
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
                                    searchAdapter.notifyItemChanged(position);
                                    ToastUtil.showToast("收藏成功");
                                }
                            });
                }
            }
        });

        RxBus.getInstance().toFlowablle(LoginEvent.class).subscribe(new Consumer<LoginEvent>() {
            @Override
            public void accept(LoginEvent loginEvent) throws Exception {
                queryWord(searchWord);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setTitle(R.string.actionbar_search);
        getMenuInflater().inflate(R.menu.menu_search, menu);
        mSearchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        mSearchView.setMaxWidth(1920);
        mSearchView.setIconified(true);
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                SearchActivity.this.finish();
                return true;
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchWord = query;
                queryWord(searchWord);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void queryWord(String searchWord) {
        ApiService.createSearchService().searchWithword(0, searchWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LzyResponse<SearchBean>>() {
                    @Override
                    public void accept(LzyResponse<SearchBean> searchBeanLzyResponse) throws Exception {
                        SearchBean data = searchBeanLzyResponse.getData();
                        searchList.clear();
                        searchList.addAll(data.getDatas());
                        searchAdapter.setNewData(searchList);
                        if (refresh.isRefreshing()) {
                            refresh.setRefreshing(false);
                        }
                    }
                });
    }

    @Override
    protected BaseView createView() {
        return this;
    }

    @Override
    protected BasePresenter<BaseView> createPresenter() {
        return new BasePresenter<>();
    }
}
