package com.shouxiu.wanandroid.simple6.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.network.ApiService;
import com.shouxiu.wanandroid.network.bean.LzyListResponse;
import com.shouxiu.wanandroid.network.bean.UsefulSitesBean;
import com.shouxiu.wanandroid.simple6.adapter.UsefulSitesAdapter;
import com.shouxiu.wanandroid.simple6.base.BaseFragment;
import com.shouxiu.wanandroid.simple6.base.BasePresenter;
import com.shouxiu.wanandroid.simple6.base.BaseView;
import com.shouxiu.wanandroid.view.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @创建者 yeping
 * @创建时间 2017/11/22 14:55
 * @描述 ${TODO}
 */

public class Home1TwoFragment extends BaseFragment<BaseView, BasePresenter<BaseView>> implements BaseView {
    @BindView(R.id.rv_home_useful_sites)
    RecyclerView rvHomeUsefulSites;
    Unbinder unbinder;
    private List<UsefulSitesBean> data = new ArrayList<>();
    private UsefulSitesAdapter mUsefulSitesAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_1two;
    }

    @Override
    protected void initView(View view) {
        mUsefulSitesAdapter = new UsefulSitesAdapter(data);
        StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(6, StaggeredGridLayoutManager.HORIZONTAL);
        rvHomeUsefulSites.setLayoutManager(layout);
        rvHomeUsefulSites.setAdapter(mUsefulSitesAdapter);
        mUsefulSitesAdapter.setOnItemClickListener(new UsefulSitesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                WebViewActivity.loadUrl(getContext(), data.get(position).getLink(),
                        Html.fromHtml(data.get(position).getName()).toString());
            }

            @Override
            public void onItemLikeClick(View view, int position, boolean isCollect) {

            }
        });

        ApiService.createHomeService().getUsefulSites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LzyListResponse<UsefulSitesBean>>() {
                    @Override
                    public void accept(LzyListResponse<UsefulSitesBean> usefulSitesBeanLzyListResponse) throws Exception {
                        data.clear();
                        data.addAll(usefulSitesBeanLzyListResponse.getData());
                        mUsefulSitesAdapter.notifyDataSetChanged();
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
}
