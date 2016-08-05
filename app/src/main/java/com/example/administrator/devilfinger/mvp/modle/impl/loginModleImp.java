package com.example.administrator.devilfinger.mvp.modle.impl;

import com.example.administrator.devilfinger.mvp.callback.LoginCallBack;
import com.example.administrator.devilfinger.mvp.modle.LoginModle;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 *
 * @author 王硕
 * @date 2016/8/3 14:22
 * @desc 文件描述
 */
public class LoginModleImp implements LoginModle {
    @Override
    public void checkLogin(String name, String password, LoginCallBack loginCallBack) {

        String url = "http://121.42.171.113:8080/appServer/im/crm/checkLogin";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("userId", name)
                .addParams("pwd", password)
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.print(""+e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        loginCallBack.onSuccess(response);
                    }
                });
    }



}
