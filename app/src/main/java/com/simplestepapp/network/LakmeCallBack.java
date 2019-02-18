package com.simplestepapp.network;


import com.simplestepapp.data.CommonModel;

/**
 * Created by Suren Reddy on 24-Jul-17.
 */

public abstract class LakmeCallBack<T extends CommonModel> implements UICallBack<T> {

    public abstract void onFailure(String s, ErrorCodes errorCodes);

    @Override
    public final void onFailure(ErrorCodes errorCodes) {
        onFailure("", errorCodes);
    }
}
