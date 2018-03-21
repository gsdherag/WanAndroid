package com.shouxiu.wanandroid.simple6.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.shouxiu.wanandroid.cons.Constant;
import com.shouxiu.wanandroid.network.bean.FirstLevelBean;
import com.shouxiu.wanandroid.simple6.fragment.ArticleListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yeping
 * @date 2018/3/9 14:25
 * TODO
 */

public class ArticleTypeFragmentPagerAdapter extends FragmentPagerAdapter {

    @Nullable
    private List<FirstLevelBean.ChildrenBean> mChildrenData;
    private List<ArticleListFragment> mArticleTypeFragments;

    public ArticleTypeFragmentPagerAdapter(FragmentManager fm, List<FirstLevelBean.ChildrenBean> childrenBeans) {
        super(fm);
        this.mChildrenData = childrenBeans;
        mArticleTypeFragments = new ArrayList<>();
        if (mChildrenData == null)
            return;
        for (FirstLevelBean.ChildrenBean childrenDatum : mChildrenData) {
            ArticleListFragment articleListFragment = (ArticleListFragment) ARouter.getInstance().build("/article/ArticleListFragment")
                    .withInt(Constant.CONTENT_CID_KEY, childrenDatum.getId())
                    .navigation();
            mArticleTypeFragments.add(articleListFragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mArticleTypeFragments.get(position);
    }

    @Override
    public int getCount() {
        return mArticleTypeFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mChildrenData.get(position).getName();
    }
}
