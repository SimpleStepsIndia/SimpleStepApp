package com.simplestepapp.network;

/**
 * Created by Suren Reddy on 24-Jul-17.
 */

public abstract class MyCallBack<T> implements MyUICallBack<T> {

    public abstract void onFailure(String s, ErrorCodes errorCodes);

    @Override
    public final void onFailure(ErrorCodes errorCodes) {
        onFailure("", errorCodes);
    }
}
