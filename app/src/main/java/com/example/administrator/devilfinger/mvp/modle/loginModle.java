package com.example.administrator.devilfinger.mvp.modle;

import com.example.administrator.devilfinger.mvp.callback.LoginCallBack;

/**
 *
 * @author 王硕
 * @date 2016/8/3 14:20
 * @desc 文件描述
 */
public interface LoginModle {
    void checkLogin(String name , String password, LoginCallBack LoginCallBack);
}
