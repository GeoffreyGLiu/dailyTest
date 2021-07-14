package com.geoffrey.test;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class TestService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder{

        public void showToast(){
            Toast.makeText(getApplicationContext(),"Service开启",Toast.LENGTH_SHORT).show();
        }
        public void showToast(String str){
            Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
        }
    }

}
