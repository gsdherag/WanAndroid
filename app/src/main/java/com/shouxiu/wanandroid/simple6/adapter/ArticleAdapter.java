package com.shouxiu.wanandroid.simple6.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.network.bean.ArticleBean;
import com.shouxiu.wanandroid.view.recyclerview.BaseQuickAdapter;
import com.shouxiu.wanandroid.view.recyclerview.BaseViewHolder;

import java.util.List;

/**
 * @author yeping
 * @date 2018/3/7 10:54
 * @description ${TODO}
 */

public class ArticleAdapter extends BaseQuickAdapter<ArticleBean, ArticleAdapter.SearchHolder> {

    public ArticleAdapter(@Nullable List<ArticleBean> data) {
        super(R.layout.item_article, data);
    }

    @Override
    protected void convert(SearchHolder helper, final ArticleBean articleBean, final int position) {
        helper.setText(R.id.tvAuthor, articleBean.getAuthor());
        helper.setText(R.id.tvNiceDate, articleBean.getNiceDate());
        helper.setText(R.id.tvTitle, articleBean.getTitle());
        helper.setText(R.id.tvChapterName, articleBean.getChapterName());
        helper.setOnClickListener(R.id.cardView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, position);
            }
        });
        helper.setImageResource(R.id.ivCollect, articleBean.isCollect() ? R.drawable.ic_action_like : R.drawable.ic_action_no_like);
        helper.setOnClickListener(R.id.ivCollect, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemLikeClick(v, position, articleBean.isCollect());
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

    class SearchHolder extends BaseViewHolder {

        public SearchHolder(View view) {
            super(view);
        }
    }
}
