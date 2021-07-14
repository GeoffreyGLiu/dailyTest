package com.geoffrey.test.view;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


import java.lang.ref.WeakReference;

public class MyHandler extends Handler {

    private WeakReference<Callback> mWeakReference;

    public MyHandler(Callback callback) {
        mWeakReference = new WeakReference<>(callback);
    }

    public MyHandler(Callback callback, Looper looper) {
        super(looper);
        mWeakReference = new WeakReference<>(callback);
    }

    @Override
    public void handleMessage(Message msg) {
        if (mWeakReference != null && mWeakReference.get() != null) {
            Callback callback = mWeakReference.get();
            callback.handleMessage(msg);
        }
    }
}
