package com.shouxiu.wanandroid;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.shouxiu.wanandroid.bean.TabBean;
import com.shouxiu.wanandroid.simple6.adapter.MainFragmentAdapter;
import com.shouxiu.wanandroid.simple6.base.BaseActivity;
import com.shouxiu.wanandroid.simple6.base.BasePresenter;
import com.shouxiu.wanandroid.simple6.base.BaseView;
import com.shouxiu.wanandroid.simple6.fragment.CardBagFragment;
import com.shouxiu.wanandroid.simple6.fragment.HomePageFragment;
import com.shouxiu.wanandroid.simple6.fragment.MineFragment;
import com.shouxiu.wanandroid.simple6.fragment.ServiceFragment;
import com.shouxiu.wanandroid.view.CustomViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<BaseView, BasePresenter<BaseView>> implements BaseView {

    public static final int FRAGMENT_VIP_CARD = 0;
    public static final int FRAGMENT_SERVICE = 1;
    public static final int FRAGMENT_CARD_BAG = 2;
    public static final int FRAGMENT_MINE = 3;

    @BindView(R.id.main_view_pager)
    CustomViewPager mainViewPager;
    @BindView(R.id.ll_tab_bottom)
    LinearLayout llTabBottom;
    @BindView(R.id.main_tab)
    BottomNavigationBar mainTab;

    /**
     * myAdapter
     */
    protected MainFragmentAdapter myAdapter = null;

    /**
     * 存放选项卡信息的列表
     */
    protected ArrayList<TabBean> mTabs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_main, null);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        int currentTab = supplyTabs();
        myAdapter = new MainFragmentAdapter(getSupportFragmentManager(), mTabs);
        mainViewPager.setOffscreenPageLimit(mTabs.size());
        mainViewPager.setAdapter(myAdapter);
        mainViewPager.setCurrentItem(currentTab, true);
        mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mainTab.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        BadgeItem badgeItem = new BadgeItem().setBorderWidth(1).setBackgroundColorResource(R.color.colorAccent).setText("2").setHideOnSelect(true);
        mainTab.clearAll();
        mainTab.setMode(BottomNavigationBar.MODE_FIXED);
        mainTab.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mainTab.addItem(new BottomNavigationItem(mTabs.get(0).getIcon(), mTabs.get(0).getName()).setActiveColorResource(R.color.blue_app).setBadgeItem(badgeItem))
                .addItem(new BottomNavigationItem(mTabs.get(1).getIcon(), mTabs.get(1).getName()).setActiveColorResource(R.color.blue_app))
                .addItem(new BottomNavigationItem(mTabs.get(2).getIcon(), mTabs.get(2).getName()).setActiveColorResource(R.color.blue_app))
                .addItem(new BottomNavigationItem(mTabs.get(3).getIcon(), mTabs.get(3).getName()).setActiveColorResource(R.color.blue_app))
                .setFirstSelectedPosition(0)
                .initialise();

        mainTab.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                mainViewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

    }

    private int supplyTabs() {
        mTabs.add(new TabBean(FRAGMENT_VIP_CARD, getString(R.string.home_page), R.drawable.selector_vipcard_icon, HomePageFragment.class));
        mTabs.add(new TabBean(FRAGMENT_CARD_BAG, getString(R.string.card_bag), R.drawable.selector_cardbag_icon, CardBagFragment.class));
        mTabs.add(new TabBean(FRAGMENT_SERVICE, getString(R.string.government_service), R.drawable.selector_wealth_icon, ServiceFragment.class));
        mTabs.add(new TabBean(FRAGMENT_MINE, getString(R.string.mine), R.drawable.selector_mine_icon, MineFragment.class));
        return FRAGMENT_VIP_CARD;
    }

    @Override
    protected BaseView createView() {
        return this;
    }

    @Override
    protected BasePresenter<BaseView> createPresenter() {
        return new BasePresenter();
    }
}
