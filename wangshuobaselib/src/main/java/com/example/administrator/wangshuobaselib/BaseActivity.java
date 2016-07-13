package com.example.administrator.wangshuobaselib;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zhy.http.okhttp.OkHttpUtils;

/**
 * com.example.administrator.wangshuobase.BaseActivity
 *
 * @author 王硕
 * @date 2016/7/5 14:56
 * @desc 文件描述
 */
public class BaseActivity extends AppCompatActivity {

    /**
     *
     * 是否需要安全相关处理，如果需要，子类覆盖此方法，返回 true
     *
     */
    protected boolean isSecurity() {
        return false;
    }
    /**
     * 是否打开安全屏幕
     *
     * @return
     */
    protected boolean isOpenSecureScreen() {
        return false;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isSecurity() && isOpenSecureScreen()) {
            // 禁止截屏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        //设置屏幕透明
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        //加入activty 队列
        RRHActivityStack.getInst().pushActivity(this);

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

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //删除队列
        RRHActivityStack.getInst().popActivity(this);
        //取消okhttp请求
        OkHttpUtils.getInstance().cancelTag(this);
    }


}
