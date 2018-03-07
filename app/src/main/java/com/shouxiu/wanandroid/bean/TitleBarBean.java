package com.shouxiu.wanandroid.bean;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleBarBean {

    /**
     * 左边布局容器
     */
    private RelativeLayout left_container;

    /**
     * 中间布局容器
     */
    private RelativeLayout center_container;

    /**
     * 右边布局容器
     */
    private RelativeLayout right_container;

    /**
     * 返回按钮(最左边)
     */
    private ImageView iv_back;

    /**
     * 标题栏中间图片
     */
    private ImageView iv_title;

    /**
     * 菜单按钮(最右边)
     */
    private ImageView iv_menu;

    /**
     * 返回按钮右边文字
     */
    private TextView tv_backText;

    /**
     * 标题图标左边文字
     */
    private TextView tv_titleText;

    /**
     * 菜单按钮左边文字
     */
    private TextView tv_menuText;

    /**
     * 底部分割线(阴影部分)
     */
    private View v_separator;

    /**
     * 底部分割线
     */
    private View v_bottom_divider;

    public RelativeLayout getLeft_container() {
        return left_container;
    }

    public void setLeft_container(RelativeLayout left_container) {
        this.left_container = left_container;
    }

    public RelativeLayout getCenter_container() {
        return center_container;
    }

    public void setCenter_container(RelativeLayout center_container) {
        this.center_container = center_container;
    }

    public RelativeLayout getRight_container() {
        return right_container;
    }

    public void setRight_container(RelativeLayout right_container) {
        this.right_container = right_container;
    }

    public View getV_separator() {
        return v_separator;
    }

    public void setV_separator(View v_separator) {
        this.v_separator = v_separator;
    }

    public ImageView getIv_back() {
        return iv_back;
    }

    public void setIv_back(ImageView iv_back) {
        this.iv_back = iv_back;
    }

    public ImageView getIv_title() {
        return iv_title;
    }

    public void setIv_title(ImageView iv_title) {
        this.iv_title = iv_title;
    }

    public ImageView getIv_menu() {
        return iv_menu;
    }

    public void setIv_menu(ImageView iv_menu) {
        this.iv_menu = iv_menu;
    }

    public TextView getTv_backText() {
        return tv_backText;
    }

    public void setTv_backText(TextView tv_backText) {
        this.tv_backText = tv_backText;
    }

    public TextView getTv_titleText() {
        return tv_titleText;
    }

    public void setTv_titleText(TextView tv_titleText) {
        this.tv_titleText = tv_titleText;
    }

    public TextView getTv_menuText() {
        return tv_menuText;
    }

    public void setTv_menuText(TextView tv_menuText) {
        this.tv_menuText = tv_menuText;
    }

    public View getV_bottom_divider() {
        return v_bottom_divider;
    }

    public void setV_bottom_divider(View v_bottom_divider) {
        this.v_bottom_divider = v_bottom_divider;
    }

}
