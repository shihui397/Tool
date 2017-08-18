package com.jsd.library.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.github.anrwatchdog.ANRError;
import com.github.anrwatchdog.ANRWatchDog;
import com.github.anrwatchdog.ANRWatchDog.ANRListener;
import com.jsd.library.utils.DebugUtil;
import com.lzy.okgo.OkGo;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


/**
 * Created by tom on 15/6/23.
 */
public class BaseApplication extends Application {

    private static Handler mHandler = new Handler();
    private static Context mContext = null;
    private static Application instance;
    private static RefWatcher refWatcher;


    public static void setApplication(Application application) {
        instance = application;
        mContext = application;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getMainThreadHandler() {
        return mHandler;
    }

    public static Context getContext() {
        return mContext;
    }


    public static Application get() {
        return instance;
    }

    public static RefWatcher getRefWatcher() {
        return refWatcher == null ? installLeakCanary() : refWatcher;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        instance = this;
        initWatchDog();
        //必须调用初始化
        OkGo.init(this);
        refWatcher = installLeakCanary();
    }

    private void initWatchDog() {
        new ANRWatchDog(8000).setANRListener(
                new ANRListener() {
                    @Override
                    public void onAppNotResponding(ANRError error) {
                        if (DebugUtil.isDebuggable() && !DebugUtil.isDebuggerConnected()) {
                            // 非调试连接状态的Debug版本中，ANR时抛出异常
                            throw error;
                        }
                    }
                }).start();
    }

    protected static RefWatcher installLeakCanary() {
        return DebugUtil.isDebuggable() ? LeakCanary.install(get()) : RefWatcher.DISABLED;
    }
}
