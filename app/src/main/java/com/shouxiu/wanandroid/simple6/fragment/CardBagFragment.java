package com.shouxiu.wanandroid.simple6.fragment;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.simple6.base.BaseFragment;
import com.shouxiu.wanandroid.simple6.base.BasePresenter;
import com.shouxiu.wanandroid.simple6.base.BaseView;
import com.shouxiu.wanandroid.view.widgets.APHeaderView;
import com.shouxiu.wanandroid.view.widgets.CommonListDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 yeping
 * @创建时间 2017/11/21 10:39
 * @描述 ${TODO}
 */

public class CardBagFragment extends BaseFragment<BaseView,BasePresenter<BaseView>> implements BaseView {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private APHeaderView mHeaderView;
    private AlipayAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_card_bag;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = view.findViewById(R.id.alipay_rv);
        mRefreshLayout = view.findViewById(R.id.alipay_srl);
        mHeaderView = view.findViewById(R.id.alipay_header);

        final LinearLayoutManager lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {

            @Override
            public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
                int scrolled = super.scrollVerticallyBy(dy, recycler, state);
                if (dy < 0 && scrolled != dy) {
                    // 有剩余
                    APHeaderView.Behavior behavior = mHeaderView.getBehavior();
                    if (behavior != null) {
                        int unconsumed = dy - scrolled;
                        int consumed = behavior.scroll((CoordinatorLayout) mHeaderView.getParent(), mHeaderView, unconsumed, -mHeaderView.getScrollRange(), 0);
                        scrolled += consumed;
                    }
                }
                return scrolled;
            }
        };

        mHeaderView.setOnHeaderFlingUnConsumedListener(new APHeaderView.OnHeaderFlingUnConsumedListener() {
            @Override
            public int onFlingUnConsumed(APHeaderView header, int targetOffset, int unconsumed) {
                APHeaderView.Behavior behavior = mHeaderView.getBehavior();
                int dy = -unconsumed;
                if (behavior != null) {
                    mRecyclerView.scrollBy(0, dy);
                }
                return dy;
            }
        });

        ArrayList<String> titles = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            titles.add("item " + i);
        }

        mRecyclerView.setLayoutManager(lm);
        mAdapter = new AlipayAdapter(titles);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new CommonListDecoration());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    APHeaderView.Behavior behavior = mHeaderView.getBehavior();
                    if (behavior != null) {
                        behavior.checkSnap((CoordinatorLayout) mHeaderView.getParent(), mHeaderView);
                    }
                }
            }
        });

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected BaseView createView() {
        return this;
    }

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }


    static class AlipayItemViewHolder extends RecyclerView.ViewHolder {

        TextView titleTv;

        public AlipayItemViewHolder(View itemView) {
            super(itemView);
            titleTv = (TextView) itemView;
        }
    }

    private static class AlipayAdapter extends RecyclerView.Adapter<AlipayItemViewHolder> {

        private List<String> mTitles;

        public AlipayAdapter(List<String> titles) {
            mTitles = titles;
        }

        @Override
        public AlipayItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AlipayItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false));
        }

        @Override
        public void onBindViewHolder(AlipayItemViewHolder holder, int position) {
            holder.titleTv.setText(mTitles.get(position));
        }

        @Override
        public int getItemCount() {
            return mTitles == null ? 0 : mTitles.size();
        }
    }
}
