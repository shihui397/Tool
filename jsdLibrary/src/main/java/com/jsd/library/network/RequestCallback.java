package com.jsd.library.network;

/**
 * Created by sf on 2017/3/22.
 */

public interface RequestCallback<T> {
    /**
     * 成功
     */
    void success(T t);

    /**
     * 失败
     */
    void error(String err);
}