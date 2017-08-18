package com.jsd.library.network;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import java.io.File;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;


/**
 * HttpUtils发送请求
 */
public class Request {
    private String url;
    private ResponseListener listener;
    private  Map<String, Object> map;
    private HttpParams httpParams;
    private boolean isMultipart;

    public Request(Map<String, Object> map, String url) {
        this.url = url;
        this.map  = map;
    }
    public Request(HttpParams httpParams, String url,boolean isMultipart) {
        this.url = url;
        this.httpParams  = httpParams;
        this.isMultipart  = isMultipart;
    }
    public Request(HttpParams httpParams, String url) {
        this.url = url;
        this.httpParams  = httpParams;
    }
    public Request(String url) {
        this.url = url;
    }

    public void setListener(ResponseListener listener) {
        this.listener = listener;
    }

    public Request postExecute() {
        PostRequest postRequest = OkGo.post(url);
        if(httpParams != null) {
            postRequest.params(httpParams);
        }else if(map != null){
            for (String key : map.keySet()) {
                Object value = map.get(key);
                if(value instanceof Integer){
                    postRequest.params(key,(Integer)value);
                }else if(value instanceof  Double){
                    postRequest.params(key,(Double)value);
                }else if(value instanceof  Character){
                    postRequest.params(key,(Character)value);
                } else if(value instanceof  Long){
                    postRequest.params(key,(Long)value);
                }else if(value instanceof  Boolean){
                    postRequest.params(key,(Boolean)value);
                }else if(value instanceof  String){
                    postRequest.params(key,(String)value);
                }else if(value instanceof  File){
                    postRequest.params(key,(File)value);
                }
            }
        }
        if(isMultipart)
            postRequest.isMultipart(isMultipart);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(String string, Call call, Response response) {
                listener.success(string);
            }
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                listener.failure("网络异常");
            }
        });
        return this;
    }

    public Request getExecute() {
        GetRequest getRequest = OkGo.get(url);
        if(httpParams != null){
            getRequest.params(httpParams);
        }else if (map != null){
            for (String key : map.keySet()) {
                Object value = map.get(key);
                if(value instanceof Integer){
                    getRequest.params(key,(Integer)value);
                }else if(value instanceof  Double){
                    getRequest.params(key,(Double)value);
                }else if(value instanceof  Character){
                    getRequest.params(key,(Character)value);
                } else if(value instanceof  Long){
                    getRequest.params(key,(Long)value);
                }else if(value instanceof  Boolean){
                    getRequest.params(key,(Boolean)value);
                }else if(value instanceof  String){
                    getRequest.params(key,(String)value);
                }
            }
        }
        getRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(String string, Call call, Response response) {
                listener.success(string);
            }
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                listener.failure("网络异常");
            }
        });
        return this;
    }
}
