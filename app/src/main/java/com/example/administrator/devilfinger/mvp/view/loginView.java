package com.example.administrator.devilfinger.mvp.view;

/**
 *
 * @author 王硕
 * @date 2016/8/2 15:02
 * @desc 文件描述
 */
public interface LoginView {
    //返回成功信息
    void showLoginSuccessMsg(String msg);
    //返回失败信息
    void showLoginFail(String msg);

}
