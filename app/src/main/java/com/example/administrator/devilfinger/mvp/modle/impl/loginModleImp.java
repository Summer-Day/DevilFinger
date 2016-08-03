package com.example.administrator.devilfinger.mvp.modle.impl;

import com.example.administrator.devilfinger.mvp.callback.LoginCallBack;
import com.example.administrator.devilfinger.mvp.modle.LoginModle;

/**
 *
 * @author 王硕
 * @date 2016/8/3 14:22
 * @desc 文件描述
 */
public class LoginModleImp implements LoginModle {
    @Override
    public void checkLogin(String name, String password, LoginCallBack loginCallBack) {
        if(login(name , password))
        {
            loginCallBack.onSuccess("Y");
        }
        else
        {
            loginCallBack.onFail("N");
        }
    }

    private Boolean login(String name, String password) {

        if("Y".equals(name))
        {
            return true;
        }
        else
        {
            return false;
        }

    }


}
