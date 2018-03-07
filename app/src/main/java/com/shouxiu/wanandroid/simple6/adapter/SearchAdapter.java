package com.shouxiu.wanandroid.simple6.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.network.bean.SearchBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author yeping
 * @date 2018/3/5 11:03
 * @description ${TODO}
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {

    private Context context;
    private List<SearchBean.ArticleBean> list;

    public SearchAdapter(Context context, List<SearchBean.ArticleBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchHolder holder, final int position) {
        final SearchBean.ArticleBean articleBean = list.get(position);
        holder.tvAuthor.setText(articleBean.getAuthor());
        holder.tvNiceDate.setText(articleBean.getNiceDate());
        holder.tvTitle.setText(Html.fromHtml(articleBean.getTitle()));
        holder.tvChapterName.setText(articleBean.getChapterName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, position);
            }
        });
        holder.ivCollect.setImageResource(articleBean.isCollect() ? R.drawable.ic_action_like : R.drawable.ic_action_no_like);
        holder.ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemLikeClick(v, position, articleBean.isCollect());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SearchHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.tvAuthor)
        TextView tvAuthor;
        @BindView(R.id.tvNiceDate)
        TextView tvNiceDate;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvChapterName)
        TextView tvChapterName;
        @BindView(R.id.ivCollect)
        ImageView ivCollect;

        public SearchHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setNewData(@Nullable List<SearchBean.ArticleBean> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLikeClick(View view, int position, boolean isCollect);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
