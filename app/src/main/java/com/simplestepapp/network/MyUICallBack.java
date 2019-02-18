package com.simplestepapp.network;

/**
 * Created by Suren Reddy on 24-Jul-17.
 */

public interface MyUICallBack<T> {

    void onSuccess(T t);

    void onFailure(ErrorCodes errorCodes);
}
