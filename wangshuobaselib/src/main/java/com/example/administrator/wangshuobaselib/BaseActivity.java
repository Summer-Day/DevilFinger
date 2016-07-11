package com.example.administrator.wangshuobaselib;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * com.example.administrator.wangshuobase.BaseActivity
 *
 * @author 王硕
 * @date 2016/7/5 14:56
 * @desc 文件描述
 */
public class BaseActivity extends AppCompatActivity {
    protected ActionBar actionBar;
    private TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
    }

    private void initActionBar() {
        actionBar = getActionBar();
//        View view = View.inflate(this, R.layout.actionbar_title, null);
//        tv = (TextView) view.findViewById(R.id.tv_shimmer);
//        actionBar.setCustomView(view);
//        actionBar.setDisplayHomeAsUpEnabled(true); //给左上角图标的左边加上一个返回的图标
//        actionBar.setDisplayShowHomeEnabled(true); //使左上角图标是否显示，如果设成false，则没有程序图标，仅仅就个标题，否则，显示应用程序图标，对应id为Android.R.id.home，对应ActionBar.DISPLAY_SHOW_HOME
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();//主动调用back键
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
