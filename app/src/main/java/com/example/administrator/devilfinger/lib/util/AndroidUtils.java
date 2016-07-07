package com.example.administrator.devilfinger.lib.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Looper;
import android.os.PowerManager;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.Window;

import com.example.administrator.devilfinger.DFApplication;
import com.example.administrator.devilfinger.lib.safe.JavaTypesHelper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.lang.reflect.Method;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

/**
 * 系统Utils
 * <p/>
 * Created by abu on 2015/12/26.
 */
public class AndroidUtils {

    /**
     * 判断是否为主线程
     *
     * @return true:主线程;false:非主线程
     */
    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /**
     * 安装APK
     *
     * @param context      上下文
     * @param fullFilePath 文件的全目录
     */
    public static void installApk(Context context, String fullFilePath) {
        if (fullFilePath == null || fullFilePath.length() <= 0) {
            return;
        }
        File file = new File(fullFilePath);

        if (file.exists()) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            String type = "application/vnd.android.package-archive";
            intent.setDataAndType(Uri.fromFile(file), type);
            context.startActivity(intent);
        }
    }

    /**
     * 判断apk是否有安装
     *
     * @param context     上下文对象
     * @param packageName 安装包名称
     * @return true:已安装;false:未安装
     */
    public static boolean isInstallApk(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName) || context == null) {
            return false;
        }
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }

        return packageInfo == null ? false : true;
    }

    /**
     * 判断是否为Debuggable模式
     *
     * @param context
     * @return
     */
    public static boolean isDebuggable(Context context) {
        if (context == null) {
            return false;
        }
        return (0 != (context.getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE));
    }

    /**
     * 获取状态栏的高度
     *
     * @param context 上下文实例对象
     * @return int
     * 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        if (context == null)
            return 0;
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取状态栏的高度
     *
     * @param activity Activity实例
     * @return int
     * 状态栏高度
     */
    public static int getStatusBarHeight(Activity activity) {
        int height = 0;
        Rect rect = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        height = rect.top;
        if (height == 0) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = JavaTypesHelper.toInt(localClass.getField("status_bar_height").get(localObject).toString(), 0);
                height = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                JDBLog.detailException(e);
            } catch (IllegalAccessException e) {
                JDBLog.detailException(e);
            } catch (InstantiationException e) {
                JDBLog.detailException(e);
            } catch (NumberFormatException e) {
                JDBLog.detailException(e);
            } catch (IllegalArgumentException e) {
                JDBLog.detailException(e);
            } catch (SecurityException e) {
                JDBLog.detailException(e);
            } catch (NoSuchFieldException e) {
                JDBLog.detailException(e);
            }
        }
        return height;
    }

    /**
     * 获取NavigationBar高度
     *
     * @param context 上下文实例对象
     * @return int
     * NavigationBar高度
     */
    public static int getNavigationBarHeight(Context context) {
        if (context == null)
            return 0;
        int result = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取NavigationBarLandscape的高度
     *
     * @param context 上下文实例对象
     * @return int
     * NavigationBarLandscape的高度
     */
    public static int getNavigationBarLandscapeHeight(Context context) {
        if (context == null)
            return 0;
        int result = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height_landscape", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 获取应用的签名
     *
     * @param context 上下文
     * @return int
     * 应用的数字签名
     */
    public static int getSignatureOfThisApp(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo info = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = info.signatures;
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(signs[0]
                    .toByteArray()));
            PublicKey key = cert.getPublicKey();
            int modulusHash = ((RSAPublicKey) key).getModulus().hashCode();

            return modulusHash;
        } catch (Throwable e) {

        }

        return 0;
    }


    /**
     * 获取本地已安装的应用版本号
     *
     * @param context     上下文对象
     * @param packageName 包名称
     * @return int
     * 成功，返回版本号；失败，返回-1
     */
    public static int getInstallApkVersion(Context context, String packageName) {

        if (TextUtils.isEmpty(packageName)) {
            return -1;
        }

        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            if (packageInfo != null) {
                return packageInfo.versionCode;
            }
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * @param intent
     * @param name
     * @param defaultValue
     * @return
     */
    public static int getIntentIntExtra(Intent intent, String name, int defaultValue) {
        if (intent == null) {
            return defaultValue;
        }

        try {
            return intent.getIntExtra(name, defaultValue);
        } catch (RuntimeException t) {
            // 处理黑客intent注入非法数据引发的异常。
            // http://bbs.pediy.com/showthread.php?p=1344035
//            if (DFApplication.getContext().isDebugMode()) {
//                throw t;
//            } else {
//                CrashReport.postCatchedException(t);
//            }
        }

        return defaultValue;
    }

    /**
     * @param intent
     * @param name
     * @return
     */
    public static String getIntentStringExtra(Intent intent, String name) {
        if (intent == null) {
            return null;
        }

        try {
            return intent.getStringExtra(name);
        } catch (RuntimeException t) {
            // 处理黑客intent注入非法数据引发的异常。
            // http://bbs.pediy.com/showthread.php?p=1344035
//            if (DFApplication.getContext().isDebugMode()) {
//                throw t;
//            } else {
//                CrashReport.postCatchedException(t);
//            }
        }

        return null;
    }

    /**
     * 复制到剪贴板
     *
     * @param content 上下文
     */
    public static void copyToClipboard(String content) {
        if (content == null) {
            content = "";
        }

        try {
            ClipboardManager clip = (ClipboardManager) DFApplication.getContext()
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            clip.setText(content);
        } catch (Throwable ex) {
            JDBLog.e(ex);
        }
    }

    /**
     * 检查是否为主线程
     *
     * @throws Throwable
     */
    public static void checkMainThread() {
        boolean error = false;
        if (Looper.myLooper() == null || Looper.getMainLooper() != Looper.myLooper()) {
            error = true;
        }

        if (error == true) {
            throw new RuntimeException("Must be in UI thread!");
        }
    }

    /**
     * 添加快捷方式
     *
     * @param context     上下文对象
     * @param appName     app名称
     * @param packageName 安装包名称
     * @param className   类名称
     * @param iconResId   快捷方式图标
     */
    public static void addShortcut(Context context, String appName,
                                   String packageName, String className, int iconResId) {
        Intent target = new Intent();
        target.addCategory(Intent.CATEGORY_LAUNCHER);
        target.setAction(Intent.ACTION_MAIN);
        ComponentName comp = new ComponentName(packageName, className);
        target.setComponent(comp);

        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 快捷方式的名称
        shortcut.putExtra("duplicate", false);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, target);

        // 快捷方式的图标
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(context, iconResId);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
        context.sendBroadcast(shortcut);
    }

    /**
     * 检查是否有快捷方式
     *
     * @param context 上下文对象
     * @param appName 被检测app名称
     * @return true:有快捷方式;false:无快捷方式
     */
    public static boolean checkShortCut(Context context, String appName) {
        boolean hasShortCut = false;
        try {
            ContentResolver cr = context.getContentResolver();
            final String AUTHORITY1 = "com.android.launcher.settings";
            final String AUTHORITY2 = "com.android.launcher2.settings";
            String contentUri = "";
            if (android.os.Build.VERSION.SDK_INT < 8) {
                contentUri = "content://" + AUTHORITY1 + "/favorites?notify=true";
            } else {
                contentUri = "content://" + AUTHORITY2 + "/favorites?notify=true";
            }
            final Uri CONTENT_URI = Uri.parse(contentUri);
            Cursor c = cr.query(CONTENT_URI, new String[]{"title", "iconResource"}, "title=?",
                    new String[]{appName}, null);
            if (c != null && c.getCount() > 0) {
                hasShortCut = true;
            }
        } catch (Exception e) {
            JDBLog.e(e.toString());
        }
        return hasShortCut;
    }

    /**
     * Activity转场动画设置
     *
     * @param activity Activity实例
     * @param inAnim   进入动画Res id
     * @param exitAnim 退出动画Res id
     */
    public static void overridePendingTransition(Activity activity, int inAnim, int exitAnim) {
        Class<?> myTarget;
        Method myMethod = null;
        Class<?>[] paramTypes = {Integer.TYPE, Integer.TYPE};

        try {
            myTarget = Class.forName("android.app.Activity");
            myMethod = myTarget.getDeclaredMethod("overridePendingTransition", paramTypes);
            myMethod.invoke(activity, inAnim, exitAnim); // this - your Activity
            // instance
        } catch (Exception e) {
            JDBLog.detailException(e);
        }
    }

    /**
     * 检查初始化环境
     *
     * @param context
     * @param nullValue
     * @return
     */
    public static boolean shouldInit(Context context, boolean nullValue) {
        if (context == null) {
            return false;
        }
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        if (null == processInfos) {
            return nullValue;
        }
        String mainProcessName = context.getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /** 电源锁要慎用！！！*/

    /**
     * 立即获取电源锁,保持后台运行
     *
     * @param context
     */
    public static void acquireWakeLock(Context context) {
        if (context == null) {
            return;
        }

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, AndroidUtils.class.getCanonicalName());
        wl.acquire();
    }

    /**
     * 立即获取电源锁,延时acquireTime后释放
     *
     * @param context
     * @param acquireTime
     */
    public static void acquireWakeLock(Context context, long acquireTime) {
        if (context == null) {
            return;
        }

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, AndroidUtils.class.getCanonicalName());
        wl.acquire(acquireTime);
    }

    /**
     * 立即释放电源锁
     *
     * @param context
     */
    public static void releaseWakeLock(Context context) {
        if (context == null) {
            return;
        }
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, AndroidUtils.class.getCanonicalName());
        if (wl.isHeld()) {
            wl.release();
        }
    }
}
