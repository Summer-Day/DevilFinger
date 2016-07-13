package com.example.administrator.devilfinger;

import android.content.Context;

import com.example.administrator.devilfinger.lib.util.AndroidUtils;
import com.example.administrator.devilfinger.lib.util.JDBLog;
import com.example.administrator.devilfinger.utils.ContextUtil;
import com.example.administrator.wangshuobaselib.App;


public class DFApplication extends App {

    public static String TAG = "DFApplication";


    private static Context mContext;

    /**
     * 对于资源文件，加载失败重试次数。
     */
    public static final int RESOURCE_LOAD_MAX_TRY_COUNT = 3;


    private static DFApplication sInstance;

    public static DFApplication sharedInstance() {
        return sInstance;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        if (false == AndroidUtils.shouldInit(this, true)) {
            return;
        }
//        initWorkMode();

        super.onCreate();
        sInstance = this;
        mContext = this.getApplicationContext();
        ContextUtil.setAppContext(mContext);

    }


    @Override
    public void onTerminate() {
        JDBLog.d("onTerminate");
        super.onTerminate();
    }

    public static Context getContext() {
        return mContext;
    }


//    private void initWorkMode() {
//        if ((mContext.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) == 0) {
//            mIsDebugMode = false;
//        } else {
//            mIsDebugMode = true;
//        }
//    }



}
