package com.shouxiu.wanandroid.simple6.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;

import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.network.bean.UsefulSitesBean;
import com.shouxiu.wanandroid.view.recyclerview.BaseQuickAdapter;
import com.shouxiu.wanandroid.view.recyclerview.BaseViewHolder;

import java.util.List;

/**
 * @author yeping
 * @date 2018/3/8 11:45
 * @description ${TODO}
 */

public class UsefulSitesAdapter extends BaseQuickAdapter<UsefulSitesBean, UsefulSitesAdapter.UsefulHolder> {

    public UsefulSitesAdapter(@Nullable List<UsefulSitesBean> data) {
        super(R.layout.item_hot, data);
    }

    @Override
    protected void convert(UsefulHolder helper, final UsefulSitesBean articleBean, final int position) {
        helper.setText(R.id.tvTitle, articleBean.getName());
        String str = Integer.toHexString((int) (Math.random() * 16777215));
        try {
            int parseColor = Color.parseColor("#".concat(str));
            helper.setTextColor(R.id.tvTitle, parseColor);
        } catch (Exception e) {
            helper.setTextColor(R.id.tvTitle, Color.parseColor("#ffce3d3a"));
        }
        helper.setOnClickListener(R.id.tvTitle, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, position);
            }
        });
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLikeClick(View view, int position, boolean isCollect);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    class UsefulHolder extends BaseViewHolder {

        public UsefulHolder(View view) {
            super(view);
        }
    }
}
