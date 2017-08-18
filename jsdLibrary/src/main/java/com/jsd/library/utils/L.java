package com.jsd.library.utils;

import android.util.Log;

/**
 * Log统一管理类
 * 
 * @author gkh
 * 
 */
public class L {

	private L() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}
	private static final String TAG = "TAG";
	//LOGGRT
	// 下面四个是默认tag的函数
	public static void i(String msg) {
			Log.i(TAG, msg);
	}

	public static void d(String msg) {
			Log.d(TAG, msg);
	}

	public static void e(String msg) {
			Log.e(TAG, msg);
	}

	public static void v(String msg) {
			Log.v(TAG, msg);
	}

	// 下面是传入自定义tag的函数
	public static void i(String tag, String msg) {
			Log.i(TAG, tag + "--->" + msg);
	}

	public static void d(String tag, String msg) {
			Log.d(TAG, tag + "--->" + msg);
	}

	public static void e(String tag, String msg) {
			Log.e(TAG, tag + "--->" + msg);
	}

	public static void v(String tag, String msg) {
			Log.v(TAG, tag + "--->" + msg);
	}
	public static void w(String tag, String msg) {
			Log.w(TAG,  tag + "--->" + msg);
	}
}
