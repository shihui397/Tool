package com.jsd.library.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jsd.library.app.BaseApplication;


public class ToastUtils {

    public static void show(int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    public static void show(final int resId, final int duration) {
        BaseApplication.getMainThreadHandler().post(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BaseApplication.getContext(), resId, duration).show();
                    }
                });
    }

    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void show(final CharSequence text, final int duration) {
        BaseApplication.getMainThreadHandler().post(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BaseApplication.getContext(), text, duration).show();
                    }
                });
    }

    public static void show(int resId, Object... args) {
        show(String.format(BaseApplication.getContext().getResources().getString(resId), args), Toast.LENGTH_SHORT);
    }

    public static void show(String format, Object... args) {
        show(String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration, Object... args) {
        show(String.format(BaseApplication.getContext().getResources().getString(resId), args), duration);
    }

    public static void show(String format, int duration, Object... args) {
        show(String.format(format, args), duration);
    }

    public static void showInCenter(int resId) {
        showInCenter(BaseApplication.getContext().getString(resId));
    }

    public static void showInCenter(final String string) {
        BaseApplication.getMainThreadHandler().post(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(BaseApplication.getContext(), string, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                });
    }

    public static void DebugToast(int resId) {
        if (DebugUtil.isDebuggable()) {
            DebugToast(BaseApplication.getContext().getString(resId));
        }
    }

    public static void DebugToast(String text) {
        if (DebugUtil.isDebuggable()) {
            show("debug模式: \r\n "+text);
        }
    }

    /**
     * 自定义Toast
     * @param context
     * @param layoutId
     * @param content
     */
    public static void showCustomToast(final Context context, final int layoutId, final int textViewId, final String content){
        BaseApplication.getMainThreadHandler().post(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = new Toast(context);
                        View layout = View.inflate(context, layoutId, null);
                        TextView text = (TextView) layout.findViewById(textViewId);
                        text.setText(content);
                        toast.setView(layout);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
    }
}
