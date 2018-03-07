package com.shouxiu.wanandroid.simple6.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.bean.LoginEvent;
import com.shouxiu.wanandroid.cons.Constant;
import com.shouxiu.wanandroid.network.CookiesManager;
import com.shouxiu.wanandroid.simple6.base.BaseFragment;
import com.shouxiu.wanandroid.simple6.base.BasePresenter;
import com.shouxiu.wanandroid.simple6.base.BaseView;
import com.shouxiu.wanandroid.utils.RxBus;
import com.shouxiu.wanandroid.utils.SpUtils;
import com.shouxiu.wanandroid.view.NoDoubleClickButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;


/**
 * @创建者 yeping
 * @创建时间 2017/11/21 10:39
 * @描述 ${TODO}
 */

public class MineFragment extends BaseFragment<BaseView, BasePresenter<BaseView>> implements BaseView {

    @BindView(R.id.btn_mine_login)
    Button btnMineLogin;
    @BindView(R.id.tv_mine_login)
    TextView tvMineLogin;
    Unbinder unbinder;
    @BindView(R.id.btn_exit_login)
    NoDoubleClickButton btnExitLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view) {

        setLoginState();
        RxBus.getInstance().toFlowablle(LoginEvent.class).subscribe(new Consumer<LoginEvent>() {
            @Override
            public void accept(LoginEvent loginEvent) throws Exception {
                setLoginState();
            }
        });
    }

    private void setLoginState() {
        boolean isLogin = SpUtils.getInstance(Constant.SHARED_NAME).getBoolean(Constant.KEY_LOGIN_STATE);
        if (isLogin) {
            btnMineLogin.setVisibility(View.INVISIBLE);
            tvMineLogin.setVisibility(View.VISIBLE);
            tvMineLogin.setText(R.string.welcome_use);
        } else {
            btnMineLogin.setVisibility(View.VISIBLE);
            tvMineLogin.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected BaseView createView() {
        return this;
    }

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_mine_login, R.id.btn_exit_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_mine_login:
                ARouter.getInstance().build("/test/login").navigation();
                break;
            case R.id.btn_exit_login:
                logout();
                break;
            default:
                break;
        }
    }

    /**
     * 退出登录
     */
    private void logout() {
        setLoginState();
        CookiesManager.clearAllCookies();
        SpUtils.getInstance(Constant.SHARED_NAME).clear();
        RxBus.getInstance().post(new LoginEvent());
    }
}
