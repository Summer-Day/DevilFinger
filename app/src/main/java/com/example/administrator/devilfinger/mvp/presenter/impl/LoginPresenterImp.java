package com.example.administrator.devilfinger.mvp.presenter.impl;

import com.example.administrator.devilfinger.mvp.callback.LoginCallBack;
import com.example.administrator.devilfinger.mvp.modle.LoginModle;
import com.example.administrator.devilfinger.mvp.presenter.LoginPresenter;
import com.example.administrator.devilfinger.mvp.view.LoginView;

/**
 * com.example.administrator.devilfinger.mvp.presenter.impl.LoginPresenterImp
 *
 * @author 王硕
 * @date 2016/8/3 14:32
 * @desc 文件描述
 */
public class LoginPresenterImp implements LoginPresenter {

    /** 作为presenter来说要持有 view 和 modle 两个对象 因为需要做到中间的调度 */
    private LoginModle loginModle;
    private LoginView loginView;

    /** 构造方法时初始化loginView 和 loginModle */

    public LoginPresenterImp(LoginView loginView , LoginModle loginModle)
    {
        this.loginModle = loginModle;
        this.loginView = loginView;
    }
    @Override
    public void login(String username, String password) {
        loginModle.checkLogin(username, password, new LoginCallBack() {
            @Override
            public void onSuccess(String user) {
                loginView.showLoginSuccessMsg(user);
            }

            @Override
            public void onFail(String errorMsg) {
                loginView.showLoginFail(errorMsg);
            }
        });

    }
}
