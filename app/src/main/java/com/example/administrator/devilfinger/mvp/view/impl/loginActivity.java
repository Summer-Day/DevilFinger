package com.example.administrator.devilfinger.mvp.view.impl;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.devilfinger.R;
import com.example.administrator.devilfinger.mvp.modle.impl.LoginModleImp;
import com.example.administrator.devilfinger.mvp.presenter.LoginPresenter;
import com.example.administrator.devilfinger.mvp.presenter.impl.LoginPresenterImp;
import com.example.administrator.devilfinger.mvp.view.LoginView;
import com.example.administrator.wangshuobaselib.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author 王硕
 * @date 2016/8/2 15:04
 * @desc 文件描述
 */
public class LoginActivity extends BaseActivity implements LoginView {


    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.submit)
    Button submit;
    @InjectView(R.id.name)
    EditText name;
    @InjectView(R.id.password)
    EditText password;

    /**
     * view持有presenter的引用
     */
    private LoginPresenter loginPresenter = new LoginPresenterImp(this, new LoginModleImp());


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        initView();

    }

    private void initView() {
    }

    @Override
    public void showLoginSuccessMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.submit)
    public void onClick() {
        loginPresenter.login(name.getText().toString(), password.getText().toString());

    }
}
