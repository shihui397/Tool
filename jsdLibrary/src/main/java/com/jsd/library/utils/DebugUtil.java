
package com.jsd.library.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Debug;

import com.jsd.library.app.BaseApplication;


public class DebugUtil {

    private static Boolean sDebuggable;

    private static final int STATE_DEBUG = 0;
    private static final int STATE_PRE = 1;
    private static final int STATE_RELEASE = 2;
    private static final int STATE_TEST = 3;
    private static int debugState = -1;


    private DebugUtil() {
    }

    public static boolean isPre() {
        return getDebugState(BaseApplication.getContext()) == STATE_PRE;
    }

    public static boolean isDebug() {
        return getDebugState(BaseApplication.getContext()) == STATE_DEBUG;
    }

    public static boolean isRelease() {
        return getDebugState(BaseApplication.getContext()) == STATE_RELEASE;
    }

    public static boolean isTest() {
        return getDebugState(BaseApplication.getContext()) == STATE_TEST;
    }

    private static int getDebugState(Context context) {
        if (context == null) {
            return 0;
        }
        if (debugState == -1) {
            ApplicationInfo appInfo = context.getApplicationInfo();
            try {
                appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                        PackageManager.GET_META_DATA);
                debugState = appInfo.metaData.getInt("DEBUG_STATE_KEY");
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                debugState = 0;
            }
        }
        return debugState;
    }


    public static boolean isDebuggable(Context context) {
        if (context == null) {
            return true;
        }
        if (sDebuggable == null) {
            ApplicationInfo appInfo = context.getApplicationInfo();
            sDebuggable = (appInfo != null) && ((appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
        }
        return sDebuggable;
    }

    public static boolean isDebuggable() {
        return isDebuggable(BaseApplication.getContext());
    }

    /**
     * 判断是否为调试连接状态
     *
     * @return
     */
    public static boolean isDebuggerConnected() {
        return Debug.isDebuggerConnected();
    }
}
