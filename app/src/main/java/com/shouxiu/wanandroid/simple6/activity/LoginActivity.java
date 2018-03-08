package com.shouxiu.wanandroid.simple6.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.shouxiu.wanandroid.R;
import com.shouxiu.wanandroid.bean.LoginEvent;
import com.shouxiu.wanandroid.bean.TitleBarBean;
import com.shouxiu.wanandroid.cons.Constant;
import com.shouxiu.wanandroid.manager.SessionManager;
import com.shouxiu.wanandroid.network.ApiService;
import com.shouxiu.wanandroid.network.bean.LoginBean;
import com.shouxiu.wanandroid.network.bean.LzyResponse;
import com.shouxiu.wanandroid.simple6.base.BaseActivity;
import com.shouxiu.wanandroid.simple6.presenter.LoginPresenter;
import com.shouxiu.wanandroid.simple6.view.LoginView;
import com.shouxiu.wanandroid.utils.RxBus;
import com.shouxiu.wanandroid.utils.SpUtils;
import com.shouxiu.wanandroid.utils.ToastUtil;
import com.shouxiu.wanandroid.utils.UmsStringUtils;
import com.shouxiu.wanandroid.view.ClearEditText;
import com.shouxiu.wanandroid.view.NoDoubleClickButton;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeping
 * @date 2018/3/6 14:54
 * @description ${TODO}
 */
@Route(path = "/test/login")
public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView, BaseActivity.TitleBarLauncher {

    @BindView(R.id.edt_userName)
    ClearEditText edtUserName;
    @BindView(R.id.edt_passwd)
    ClearEditText edtPasswd;
    @BindView(R.id.btn_login)
    NoDoubleClickButton btnLogin;
    @BindView(R.id.btn_registByMobile)
    TextView btnRegistByMobile;
    @BindView(R.id.tv_forgetPWD)
    TextView tvForgetPWD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_mine_login, this);
        initView();
    }

    private void initView() {
        edtUserName.setText("shouxiu_yp@163.com");
        edtPasswd.setText("ping112233");
    }

    @Override
    protected LoginView createView() {
        return this;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void initTitleBar(TitleBarBean titleBar) {
        titleBar.getTv_titleText().setText(R.string.login);
    }

    @OnClick({R.id.btn_login, R.id.btn_registByMobile, R.id.tv_forgetPWD})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String username = edtUserName.getText().toString();
                String password = edtPasswd.getText().toString();
                if (UmsStringUtils.isNull(username) || UmsStringUtils.isNull(username)) {
                    ToastUtil.showToast(R.string.the_username_or_password_can_not_be_empty);
                    return;
                }
                getPresenter().login(username, password);
                break;
            case R.id.btn_registByMobile:

                ApiService.createHotTopicService().sendRegister("18257563278@163.com", "ping112233", "ping112233")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<LzyResponse<LoginBean>>() {
                            @Override
                            public void accept(LzyResponse<LoginBean> loginBeanLzyResponse) throws Exception {
                                LoginBean data = loginBeanLzyResponse.getData();
                                ToastUtil.showToast("注册成功");
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                ToastUtil.showToast("注册失败");
                            }
                        });
                break;
            case R.id.tv_forgetPWD:
                break;
        }
    }

    @Override
    public void loginSuccess(LoginBean loginBean) {
        ToastUtil.showToast("登录成功");
        SpUtils.getInstance(Constant.SHARED_NAME).put(Constant.KEY_LOGIN_STATE,true,true);
        SessionManager.getInstance().saveLoginInfo(loginBean);
        RxBus.getInstance().post(new LoginEvent());
        finish();
    }

    @Override
    public void loginFail(String result) {
        ToastUtil.showToast("登录失败");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
