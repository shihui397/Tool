package com.jsd.library.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;


import com.jsd.library.app.BaseApplication;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import static android.content.Context.WINDOW_SERVICE;

public class DisplayUtils {

    private static int screenHeight = 0;
    private static Context mContext = BaseApplication.getContext();
    private static WindowManager mWindowManager = (WindowManager) mContext.getSystemService(WINDOW_SERVICE);

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, mContext.getResources().getDisplayMetrics());
//        final float scale = mContext.getResources().getDisplayMetrics().density;
//		return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float px2dip(float pxVal) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
//        final float scale = mContext.getResources().getDisplayMetrics().density;
//        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 得到的屏幕的宽度
     */
    public static int getWidthPx() {
        // DisplayMetrics 一个描述普通显示信息的结构，例如显示大小、密度、字体尺寸
        DisplayMetrics displaysMetrics = new DisplayMetrics();// 初始化一个结构
        mWindowManager.getDefaultDisplay().getMetrics(displaysMetrics);// 对该结构赋值
        return displaysMetrics.widthPixels;
    }

    /**
     * 得到的屏幕的高度
     */
    public static int getHeightPx() {
        // DisplayMetrics 一个描述普通显示信息的结构，例如显示大小、密度、字体尺寸
        DisplayMetrics displaysMetrics = new DisplayMetrics();// 初始化一个结构
        mWindowManager.getDefaultDisplay().getMetrics(displaysMetrics);// 对该结构赋值
        return displaysMetrics.heightPixels;
    }

    /**
     * 得到的屏幕的高度
     */
    public static int getHeightPxByBox() {
//        if (screenHeight <= 0) {
//            screenHeight = getHeightPx() + getNavigationBarHeight(mContext);
//        }
        return getHeightPx();
    }

    public static int getNavigationBarHeight(Context context){
        Class<?> c = null;
        Object obj = null;
        Field field = null, field2 = null;
        int x = 0, navigationBarHeight = 72;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("navigation_bar_height");
//
//            for (Field f: c.getFields()
//                 ) {
//                DLog.d("debug", "name = " + f.getName());
//            }

            x = Integer.parseInt(field.get(obj).toString());
            navigationBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return navigationBarHeight;

    }

    /**
     * 得到屏幕的dpi
     *
     * @return
     */
    public static float getScrrenDPI() {
        // DisplayMetrics 一个描述普通显示信息的结构，例如显示大小、密度、字体尺寸
        DisplayMetrics displaysMetrics = new DisplayMetrics();// 初始化一个结构
        mWindowManager.getDefaultDisplay().getMetrics(displaysMetrics);// 对该结构赋值
        return displaysMetrics.scaledDensity;
    }

    /**
     * 得到屏幕的dpi
     *
     * @return
     */
    public static int getDensityDpi() {
        // DisplayMetrics 一个描述普通显示信息的结构，例如显示大小、密度、字体尺寸
        DisplayMetrics displaysMetrics = new DisplayMetrics();// 初始化一个结构
        mWindowManager.getDefaultDisplay().getMetrics(displaysMetrics);// 对该结构赋值
        return displaysMetrics.densityDpi;
    }

    /**
     * 得到屏幕的dpi
     *
     * @return
     */
    public static float getDensity() {
        // DisplayMetrics 一个描述普通显示信息的结构，例如显示大小、密度、字体尺寸
        DisplayMetrics displaysMetrics = new DisplayMetrics();// 初始化一个结构
        mWindowManager.getDefaultDisplay().getMetrics(displaysMetrics);// 对该结构赋值
        return displaysMetrics.density;
    }

    /**
     * 返回状态栏/通知栏的高度
     *
     * @param activity
     * @return
     */
    public static int getStatusHeight(Activity activity) {
//        Rect frame = new Rect();
//        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//        int statusBarHeight = frame.top;
//        return statusBarHeight;
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = mContext.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static int getStatusBarHeight() {
        int result = 75;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int[] getOptimalSize(int width, int height) {
        int screenWidth = getWidthPx();
        int screenHeight = getHeightPx();
        int [] size = new int[2];
        size[0] = screenWidth;
        size[1] = screenHeight;
        if (0 < width && 0 < height && screenWidth > width && screenHeight > height) {
            //如果视频的宽小于屏幕，高也小于屏幕
            float scalingWidth = 1f * screenWidth / width;
            float scalingHeight = 1f * screenHeight / height;
            int compareResult = new BigDecimal(scalingWidth).compareTo(new BigDecimal(scalingHeight));

            if (0 == compareResult || 1 == compareResult) {
                //左右留白
                size[0] = (int) (scalingHeight * width);
                size[1] = screenHeight;
            } else {
                //上下留白
                size[0] = screenWidth;
                size[1] = (int) (scalingWidth * height);
            }
        }

        return size;
    }
}
