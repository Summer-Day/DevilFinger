package com.example.administrator.wangshuobaselib;

import android.app.Application;
import android.content.Context;

import com.example.administrator.wangshuobaselib.lib.util.BitmapUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

/**
 * Created by storm on 14-3-24.
 */
public class App extends Application {
    private static Context sContext;
    private boolean mIsDebugMode = false;
    private static App sApp = null;

    @Override
    public void onCreate() {
        super.onCreate();

        initBdBaseApp(this);

    }
    public void initBdBaseApp(Application context) {
        sApp = this;
        sContext = this;
        //init ok-http
        initOkhttp();


        initHttpManager();
        initBitmapHelper();
    }

    public static App getInst() {
        return sApp;
    }
    public static Context getContext() {
        return sContext;
    }

    private void initOkhttp() {

        /**  https://github.com/Summer-Day/okhttp-utils   */
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null); //支持https
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
                .hostnameVerifier(
                        new HostnameVerifier() {
                            @Override
                            public boolean verify(String hostname, SSLSession session) {
                                return true;
                            }
                        })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 应用内存不足时回调方法。
     * <p/>
     * <p>
     * 在检测到OOM时回调，回调来自于background线程。
     * </p>
     */
    public void onAppMemoryLow() {
        BitmapUtils.getInstance().clearCashBitmap();
        RRHActivityStack.getInst().releaseAllPossibleAcitivities();

        System.gc();
    }

    @Override
    public void onLowMemory() {
        onAppMemoryLow();

        super.onLowMemory();
    }
    public boolean isDebugMode() {
        return mIsDebugMode;
    }
    /**
     * 此方法需要在 {@link #onCreate()} 方法执行完毕后调用才能生效。
     */
    public void setDebugMode(boolean mIsDebugMode) {
        this.mIsDebugMode = mIsDebugMode;
    }

    private void initBitmapHelper() {
        BitmapUtils.getInstance().initial(sContext);
    }
    /**
     * 应该调用HttpManager.getInstance().init(this, false);
     * 在common_rrx是空实现，要由其下层common_jdb来实现
     */
    protected void initHttpManager() {
        // 空实现
    }



}
