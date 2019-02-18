package com.simplestepapp.network;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;

/**
 * Created by dell on 15/3/16.
 */
public class CustomResultReceiver extends ResultReceiver {

    private Receiver receiver;

    public CustomResultReceiver(Handler handler) {
        super(handler);
    }

    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle resultData);

    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        if (receiver != null) {
            receiver.onReceiveResult(resultCode, resultData);
        }
    }
}
