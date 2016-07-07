package com.example.administrator.devilfinger.utils;

import android.content.Context;

import com.example.administrator.devilfinger.NoProguard;


/**
 * ContextUtil 提供全局的appcontext
 *
 * @author liuwang
 * @date 16/2/24-上午11:27
 * @desc 用来提供一个appcontext，其他需要appcontext对象的地方可以直接引用
 */
public class ContextUtil implements NoProguard {
    private static Context appContext = null;

    public static Context getAppContext() {
        return appContext;
    }

    public static void setAppContext(Context appContext) {
        ContextUtil.appContext = appContext;
    }
}
