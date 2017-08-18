package com.jsd.library.network;

/**
 * Created by sf on 2017/3/22.
 */
public  interface ResponseListener {
    void success(String result);
    void failure(String errorMsg);
}
