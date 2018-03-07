package com.shouxiu.wanandroid.simple6.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shouxiu.wanandroid.R;

import java.util.List;

/**
 * @创建者 yp
 * @创建时间 2017/6/5
 * @描述 ${TODO tablayout 适配}
 */

public class TabPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> list_fragment;                         //fragment列表
    private String[] list_Title;                              //tab名的列表
    private int[] image_Array;                              //图片

    public TabPagerAdapter(Context context, FragmentManager fm, List<Fragment> list_fragment, String[] list_Title, int[] imageArray) {
        super(fm);
        this.context = context;
        this.list_fragment = list_fragment;
        this.list_Title = list_Title;
        this.image_Array = imageArray;
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        return list_Title.length;
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {

//        Drawable image = context.getResources().getDrawable(image_Array[position]);
//        image.setBounds(0, 0, image.getIntrinsicWidth() / 2, image.getIntrinsicHeight() / 2);
//        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BASELINE);
//        SpannableString ss = new SpannableString(list_Title[position] + " ");
//        ss.setSpan(imageSpan, list_Title[position].length(), list_Title[position].length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return ss;
        return list_Title[(position % list_Title.length)];
    }

    public View getTabView(int position){
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item, null);
        TextView tv= (TextView) view.findViewById(R.id.txt_title);
        tv.setText(list_Title[position]);
        ImageView img = (ImageView) view.findViewById(R.id.img_title);
        img.setImageResource(image_Array[position]);
        return view;
    }
}
