package com.simplestepapp.network;


import com.simplestepapp.data.CommonModel;

/**
 * Created by Suren Reddy on 24-Jul-17.
 */

public interface UICallBack<T extends CommonModel> {

    void onSuccess(T t);

    void onFailure(ErrorCodes errorCodes);
}
