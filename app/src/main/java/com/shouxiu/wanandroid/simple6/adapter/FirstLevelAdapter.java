package com.shouxiu.wanandroid.simple6.adapter;

import android.support.annotation.Nullable;

import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.network.bean.FirstLevelBean;
import com.shouxiu.wanandroid.view.recyclerview.BaseQuickAdapter;
import com.shouxiu.wanandroid.view.recyclerview.BaseViewHolder;

import java.util.List;

/**
 * @author yeping
 * @date 2018/3/9 11:33
 * TODO
 */

public class FirstLevelAdapter extends BaseQuickAdapter<FirstLevelBean, BaseViewHolder> {

    public FirstLevelAdapter(@Nullable List<FirstLevelBean> data) {
        super(R.layout.item_level, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FirstLevelBean item, int position) {
        helper.setText(R.id.typeItemFirst, item.getName());
        StringBuffer sb = new StringBuffer();
        for (FirstLevelBean.ChildrenBean childrenBean : item.getChildren()) {
            sb.append(childrenBean.getName() + "     ");
        }
        helper.setText(R.id.typeItemSecond, sb.toString());
    }
}
