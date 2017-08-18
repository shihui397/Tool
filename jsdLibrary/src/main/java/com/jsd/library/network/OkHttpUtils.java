package com.jsd.library.network;

import com.google.gson.Gson;
import com.jsd.library.utils.L;
import com.lzy.okgo.model.HttpParams;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by sf on 2017/3/22
 */

public class OkHttpUtils {

    private static <T> void process(String url,String result,Type type ,RequestCallback<T> callback){
        L.i(url+"\n"+result);
//        LongLogUtils.showLogCompletion(url + "\n" + result, 300);
        ResponseEntity<T> responseEntity = null;
        try {
            responseEntity = new Gson().fromJson(result, type);
            if ("1".equals(responseEntity.getResultCode())) {
                if (null != responseEntity.getRecord()) {
                    //返回record时生成 对象
                    callback.success(responseEntity.getRecord());
                } else if (null != responseEntity.getRecords()) {
                    //返回records时生成 集合
                    callback.success(responseEntity.getRecords());
                } else {
                    //只返回resultCode或者record和records返回空时
                    callback.success(responseEntity.getRecord());
                }
            } else {
                callback.error(responseEntity.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
            callback.error("解析数据错误");
        }
    }

    /** post请求 */
    public static  <T> void post (final String url, Map<String, Object> map, final Type type, final RequestCallback<T> callback) {
        Request request = new Request(map, url);
        request.postExecute().setListener(new ResponseListener() {
            @Override
            public void success(String result) {
                process(url,result,type,callback);
            }

            @Override
            public void failure(String msg) {
                callback.error(msg);
            }
        });
    }
    public static  <T> void post (final String url, HttpParams httpParams, boolean isMultipart ,final Type type,final RequestCallback<T> callback) {
        Request request = new Request(httpParams, url,isMultipart);
        request.postExecute().setListener(new ResponseListener() {
            @Override
            public void success(String result) {
                process(url,result,type,callback);
            }

            @Override
            public void failure(String msg) {
                callback.error(msg);
            }
        });
    }
    public static  <T> void post (final String url, HttpParams httpParams, final Type type, final RequestCallback<T> callback) {
        post(url,httpParams,false,type,callback);
    }
    public static  <T> void post (final String url, final Type type, final RequestCallback<T> callback) {
        Request request = new Request(url);
        request.postExecute().setListener(new ResponseListener() {
            @Override
            public void success(String result) {
                process(url,result,type,callback);
            }

            @Override
            public void failure(String msg) {
                callback.error(msg);
            }
        });
    }
    /** get请求  */
    public static  <T> void get (final String url, Map<String, Object> map, final Type type, final RequestCallback<T> callback) {
        Request request = new Request(map, url);
        request.getExecute().setListener(new ResponseListener() {
            @Override
            public void success(String result) {
                process(url,result,type,callback);
            }
            @Override
            public void failure(String msg) {
                callback.error(msg);
            }
        });
    }
    /** get请求  */
    public static  <T> void get (final String url, final Type type, final RequestCallback<T> callback) {
        Request request = new Request(url);
        request.getExecute().setListener(new ResponseListener() {
            @Override
            public void success(String result) {
                process(url,result,type,callback);
            }
            @Override
            public void failure(String msg) {
                callback.error(msg);
            }
        });
    }
    /** get请求  */
    public static  <T> void get (final String url,HttpParams httpParams, final Type type,final RequestCallback<T> callback) {
        Request request = new Request(httpParams, url);
        request.getExecute().setListener(new ResponseListener() {
            @Override
            public void success(String result) {
                process(url,result,type,callback);
            }
            @Override
            public void failure(String msg) {
                callback.error(msg);
            }
        });
    }
}
