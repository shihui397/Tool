package com.jsd.library.utils;

import android.util.Log;

/**
 * 日期：2017/4/12 10:48
 * 作者： 张建
 * 功能描述：用于显示过长的log信息
 */

public class LongLogUtils {

    /**
     * 分段打印出较长log文本
     * @param log        原log文本
     * @param showCount  规定每段显示的长度（最好不要超过eclipse限制长度）
     */
    public static void showLogCompletion(String log,int showCount){
        if(log.length() >showCount){
            String show = log.substring(0, showCount);
            Log.i("TAG", show+"");
            if((log.length() - showCount)>showCount){//剩下的文本还是大于规定长度
                String partLog = log.substring(showCount,log.length());
                showLogCompletion(partLog, showCount);
            }else{
                String surplusLog = log.substring(showCount, log.length());
                Log.i("TAG", surplusLog+"");
            }

        }else{
            Log.i("TAG", log+"");
        }
    }
}
