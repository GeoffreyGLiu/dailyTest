package com.geoffrey.test;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.geoffrey.test.view.DrawView;

import java.util.Iterator;
import java.util.List;

import static android.app.Application.getProcessName;

public class SecondActivity extends AppCompatActivity {

    private TextView tv_second,tv_stop;
    private ImageView iv_animation;
    private FrameLayout fl_second_clock;
    private ServiceConnection connection;
    private TestService.MyBinder myBinder;
    private String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tv_second = findViewById(R.id.tv_second);
        iv_animation = findViewById(R.id.iv_animation);
        iv_animation.setImageDrawable(getResources().getDrawable(R.drawable.animation_voice_));
        tv_stop = findViewById(R.id.tv_stop);
        //接受上级页面传过来的数据
        getDataFromPre();
        final AnimationDrawable mDrawable = (AnimationDrawable) iv_animation.getDrawable();
        mDrawable.start();
        fl_second_clock = findViewById(R.id.fl_second_clock);
        tv_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent mIntent = new Intent();
//                mIntent.putExtra("MainActivity", "SecondActivity");
//                setResult(100, mIntent);
//                finish();
                if (isServiceExisted(SecondActivity.this, TestService.class.getName()))
                    myBinder.showToast("Service存活");
                else
                    Toast.makeText(SecondActivity.this, "Service已不存在", Toast.LENGTH_SHORT).show();

            }
        });

        final DrawView drawView = new DrawView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        drawView.setLayoutParams(params);
        fl_second_clock.addView(drawView);

        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myBinder = (TestService.MyBinder) service;
                myBinder.showToast();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        Intent intent = new Intent(this, TestService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);

        tv_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawable != null){
                    mDrawable.stop();
                }
            }
        });
    }

    private void getDataFromPre() {
        Intent intent = getIntent();
        if (intent != null){
             int requestCode = intent.getIntExtra("requestCode",-1);
            tv_second.setText("我是SecondActivity \r\n requestCode = " + requestCode);
        }
    }

    public static boolean isServiceExisted(Context context, String className) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            ActivityManager.RunningServiceInfo serviceInfo = serviceList.get(i);
            ComponentName serviceName = serviceInfo.service;
            if (serviceName.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }

}