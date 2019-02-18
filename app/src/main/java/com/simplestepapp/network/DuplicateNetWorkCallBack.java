package com.simplestepapp.network;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;

import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Suren Reddy on 24-Jul-17.
 */

public class DuplicateNetWorkCallBack implements Callback {


    private static final String TAG = DuplicateNetWorkCallBack.class.getName();
    private MyCallBack mUiCallBack;

    DuplicateNetWorkCallBack(MyCallBack mUiCallBack) {
        this.mUiCallBack = mUiCallBack;
    }



    @Override
    public void onResponse(Response response) {

        if (response.isSuccess()) {
            mUiCallBack.onSuccess(response.body());
            return;
        }
        //TODO handle response
        switch (response.code()) {
            case 400:
                mUiCallBack.onFailure(ErrorCodes.BAD_INPUT);
                return;
            case 500:
                mUiCallBack.onFailure(ErrorCodes.INTERNAL_SERVER_ERROR);
                return;
            default:
                mUiCallBack.onFailure(ErrorCodes.UNKONN_ERROR);
                return;

        }
    }

    @Override
    public void onFailure(Throwable t) {

        if (t instanceof IOException) {
            mUiCallBack.onFailure(ErrorCodes.NO_NETWORK);
            return;
        } else if (t instanceof JSONException) {
            mUiCallBack.onFailure(ErrorCodes.JSON_SYNTAX_MISMATCH);
            return;
        }
        Log.d(TAG, t.getMessage());

    }
}

