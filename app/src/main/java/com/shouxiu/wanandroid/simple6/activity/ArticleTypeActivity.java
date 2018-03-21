package com.shouxiu.wanandroid.simple6.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.network.bean.FirstLevelBean;
import com.shouxiu.wanandroid.simple6.adapter.ArticleTypeFragmentPagerAdapter;
import com.shouxiu.wanandroid.simple6.base.BaseActivity;
import com.shouxiu.wanandroid.simple6.base.BasePresenter;
import com.shouxiu.wanandroid.simple6.base.BaseView;
import com.shouxiu.wanandroid.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author yeping
 * @date 2018/3/9 13:45
 * 博客二级分类
 */
@Route(path = "/article/ArticleTypeActivity")
public class ArticleTypeActivity extends BaseActivity<BaseView, BasePresenter<BaseView>> implements BaseView {

    @Autowired
    public String title;
    @Autowired
    public List<FirstLevelBean.ChildrenBean> childrenData;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_article_type)
    TabLayout tabArticleType;
    @BindView(R.id.vp_article_type)
    ViewPager vpArticleType;
    private ArticleTypeFragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_article_type);
        ButterKnife.bind(this);
        initToolBar();
        initData();
    }

    private void initData() {
        toolbar.setTitle(title);
        mAdapter = new ArticleTypeFragmentPagerAdapter(getSupportFragmentManager(), childrenData);
        vpArticleType.setOffscreenPageLimit(2);
        vpArticleType.setAdapter(mAdapter);
        tabArticleType.setupWithViewPager(vpArticleType);

    }

    @Override
    protected BaseView createView() {
        return this;
    }

    @Override
    protected BasePresenter<BaseView> createPresenter() {
        return new BasePresenter<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_type_content, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
                break;
            case R.id.menuSearch:
                ARouter.getInstance().build("/activity/SearchActivity").navigation();
            default:
                ToastUtil.showToast("后续功能敬请期待");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化toolbar
     */
    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /**toolbar除掉阴影*/
        getSupportActionBar().setElevation(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(0);
        }
    }
}
