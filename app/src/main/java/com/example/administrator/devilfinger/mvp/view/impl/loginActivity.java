package com.example.administrator.devilfinger.mvp.view.impl;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.devilfinger.R;
import com.example.administrator.devilfinger.activity.MainActivity;
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
    @InjectView(R.id.tvTestView)
    TextView tvTestView;

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
//        String tvText = getString(R.string.tvText,"android",60);
        String html="<html><head><title>TextView使用HTML</title></head><body><p><strong>强调</strong></p><p><em>斜体</em></p>"
                +"<p><a href=\"http://www.dreamdu.com/xhtml/\">超链接HTML入门</a>学习HTML!</p><p><font color=\"#aabb00\">颜色1"
                +"</p><p><font color=\"#00bbaa\">颜色2</p><h1>标题1</h1><h3>标题2</h3><h6>标题3</h6><p>大于>小于<</p><p>" +
                "下面是网络图片</p><img src=\"http://avatar.csdn.net/0/3/8/2_zhang957411207.jpg\"/></body></html>";

        tvTestView.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
        tvTestView.setText(Html.fromHtml(html));



    }

    @Override
    public void showLoginSuccessMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        MainActivity.startActivity(this);
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
