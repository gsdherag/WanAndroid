package com.shouxiu.wanandroid.simple6.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.shouxiu.wanandroid.MainActivity;
import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.simple6.activity.GuideActivity;


/**
 * Author： yolanda
 * <p>
 * CreateTime： 2016/12/6 0006 下午 2:27
 * <p>
 * description：引导页
 */


public class GuideAdapter extends PagerAdapter {
    int[] guides;
    Context context;

    public GuideAdapter(int[] guides, Context context) {
        this.guides = guides;
        this.context = context;
    }

    @Override
    public int getCount() {
        return guides.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.item_guide, null);
        ImageView iv_guide = view.findViewById(R.id.iv_guide);
        Button btn_guide = view.findViewById(R.id.btn_guide);
        if (position == 3)
            btn_guide.setVisibility(View.VISIBLE);
        btn_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, MainActivity.class));
                ((GuideActivity) context).finish();
            }
        });
        iv_guide.setBackgroundResource(guides[position]);
        container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
