package com.example.administrator.devilfinger.mvp.callback;

/**
 *
 * @author 王硕
 * @date 2016/8/3 14:26
 * @desc 文件描述
 */
public interface LoginCallBack {
     void onSuccess(String user);

     void onFail(String errorMsg);
}
