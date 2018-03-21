package com.shouxiu.wanandroid.simple6.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.cons.Constant;
import com.shouxiu.wanandroid.network.ApiService;
import com.shouxiu.wanandroid.network.bean.FirstLevelBean;
import com.shouxiu.wanandroid.network.bean.LzyListResponse;
import com.shouxiu.wanandroid.simple6.adapter.FirstLevelAdapter;
import com.shouxiu.wanandroid.simple6.base.BaseFragment;
import com.shouxiu.wanandroid.simple6.base.BasePresenter;
import com.shouxiu.wanandroid.simple6.base.BaseView;
import com.shouxiu.wanandroid.view.recyclerview.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * @author yeping
 * @date 2018/3/1 9:23
 * @description ${TODO}
 */

public class HomeFragment2 extends BaseFragment<BaseView, BasePresenter<BaseView>> implements BaseView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.rv_home_2)
    RecyclerView rvHome2;
    @BindView(R.id.srl_home_2)
    SwipeRefreshLayout srlHome2;
    Unbinder unbinder;
    private List<FirstLevelBean> data = new ArrayList<>();
    private FirstLevelAdapter mFirstLevelAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_two;
    }

    @Override
    protected void initView(View view) {
        rvHome2.setLayoutManager(new LinearLayoutManager(getContext()));
        mFirstLevelAdapter = new FirstLevelAdapter(data);
        rvHome2.setAdapter(mFirstLevelAdapter);

        srlHome2.setOnRefreshListener(this);

        ApiService.createHomeService().getFirstLevel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LzyListResponse<FirstLevelBean>>() {
                    @Override
                    public void accept(LzyListResponse<FirstLevelBean> firstLevelBeanLzyListResponse) throws Exception {
                        data.clear();
                        data.addAll(firstLevelBeanLzyListResponse.getData());
                        mFirstLevelAdapter.notifyDataSetChanged();
                    }
                });

        mFirstLevelAdapter.setOnItemClickListener(this);
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

    @Override
    public void onRefresh() {
        ApiService.createHomeService().getFirstLevel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LzyListResponse<FirstLevelBean>>() {
                    @Override
                    public void accept(LzyListResponse<FirstLevelBean> firstLevelBeanLzyListResponse) throws Exception {
                        data.clear();
                        data.addAll(firstLevelBeanLzyListResponse.getData());
                        mFirstLevelAdapter.notifyDataSetChanged();
                        srlHome2.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ARouter.getInstance().build("/article/ArticleTypeActivity")
                .withString(Constant.CONTENT_TITLE_KEY, mFirstLevelAdapter.getItem(position).getName())
                .withObject(Constant.CONTENT_CHILDREN_DATA_KEY, mFirstLevelAdapter.getItem(position).getChildren())
                .navigation();
    }
}
