package com.shouxiu.wanandroid.simple6.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.network.bean.BannerBean;

import java.util.List;

/**
 * @author yeping
 * @date 2018/3/1 14:54
 * @description ${TODO}
 */

public class BannerRecyclerAdapter extends RecyclerView.Adapter<BannerRecyclerAdapter.BannerHolder> {

    private Context context;
    private List<BannerBean> mDatas;

    public BannerRecyclerAdapter(Context context, List<BannerBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public BannerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gallery, parent, false);
        return new BannerHolder(view);
    }

    @Override
    public void onBindViewHolder(BannerHolder holder, int position) {
        Glide.with(context).load(mDatas.get(position).getImagePath()).into(holder.mView);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class BannerHolder extends RecyclerView.ViewHolder {

        public final ImageView mView;

        public BannerHolder(View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.iv_photo);
        }
    }
}
