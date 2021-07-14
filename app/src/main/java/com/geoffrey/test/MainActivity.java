package com.geoffrey.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.geoffrey.aidltest.activity.AIDLActivity;
import com.geoffrey.algorithm.AlgorithmActivity;
import com.geoffrey.contentprovidertest.ContentProviderActivity;
import com.geoffrey.dagger2.RetrofitMainActivity;
import com.geoffrey.hybridtest.HybridActivity;
import com.geoffrey.hybridtest.WebViewActivity;
import com.geoffrey.iostream.IOStreamActivity;
import com.geoffrey.recyclerview.RecyclerTestActivity;
import com.geoffrey.storagedirectorytest.StorageDirectoryActivity;
import com.geoffrey.test.fragments.FragmentTestActivity;
import com.geoffrey.test.greendao.DaoActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView tv_main, tv_notification, tv_choose_list, tv_dao,tv_fragment,
            show_stub, hide_stub,tv_aidl,tv_algorithm,tv_retrofit,tv_content_provider,tv_io_stream,
            tv_recycler_move,tv_hybrid,tv_hybrid_call,tv_storage_directory;
    EditText et_input;
    ViewStub view_stub;
    private TextView tv_lav_reset;
    private LottieAnimationView lav;
    private RecyclerView rv_list;
    private ItemTestAdapter adapter;
    View cl_stub;
    String temp;
    public static final int requestCodes = 100;
    public static final String TAG = "MainActivity";
    public static final String IMAGE_UNSPECIFIED = "image/*";
    public static final int TEST_LIST_ONE = 0;
    public static final int TEST_LIST_TWO = 1;
    public static final int TEST_LIST_THREE = 2;
    public static final int TEST_LIST_FOUR = 3;

    //notification requestCode
    private int requestCode = 0;

    private Handler mHandler;
    private NotificationManager manager;
    private Notification mNotification;

    private ViewGroup.LayoutParams params;
    private AnimatorSet mAnimatorSet;
    private ArrayList<TestListBean> testList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        initView();

        //testHandler();
        String testUrl = "https://wwww.baidu.com/?key=value1&key2=value2 ";
        testGetParameter(testUrl);
    }

    private void initView() {

        tv_main = findViewById(R.id.tv_main);
        tv_notification = findViewById(R.id.tv_notification);
        tv_choose_list = findViewById(R.id.tv_choose_list);
        tv_dao = findViewById(R.id.tv_dao);
        tv_fragment = findViewById(R.id.tv_fragment);
        et_input = findViewById(R.id.et_input);
        show_stub = findViewById(R.id.show_stub);
        hide_stub = findViewById(R.id.hide_stub);
        tv_aidl = findViewById(R.id.tv_aidl);
        tv_algorithm = findViewById(R.id.tv_algorithm);
        tv_retrofit = findViewById(R.id.tv_retrofit);
        tv_content_provider = findViewById(R.id.tv_content_provider);
        tv_io_stream = findViewById(R.id.tv_io_stream);
        tv_recycler_move = findViewById(R.id.tv_recycler_move);
        tv_hybrid = findViewById(R.id.tv_hybrid);
        tv_hybrid_call = findViewById(R.id.tv_hybrid_call);
        tv_storage_directory = findViewById(R.id.tv_storage_directory);

        view_stub = findViewById(R.id.view_stub);
        lav = findViewById(R.id.lav);
        tv_lav_reset = findViewById(R.id.tv_lav_reset);
        tv_main.setText("我是MainActivity");
        tv_notification.setText("发送消息");

        params = et_input.getLayoutParams();
        tv_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, SecondActivity.class);
                startActivityForResult(mIntent, requestCodes);
            }
        });

        tv_dao.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, DaoActivity.class);
                startActivity(mIntent);
            }
        });

        tv_fragment.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, FragmentTestActivity.class);
                startActivity(mIntent);
            }
        });

        tv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageTo();
            }
        });

        hide_stub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view_stub.getParent() != null) {
                    view_stub.inflate();
                    cl_stub = findViewById(R.id.cl_stub);
                }
                cl_stub.setVisibility(View.INVISIBLE);
            }
        });
        show_stub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view_stub.getParent() != null) {
                    view_stub.inflate();
                    cl_stub = findViewById(R.id.cl_stub);
                }
                cl_stub.setVisibility(View.VISIBLE);
            }
        });
        lav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lav.playAnimation();
            }
        });

        tv_lav_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        tv_choose_list.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(mIntent);
            }
        });
        tv_aidl.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, AIDLActivity.class);
                startActivity(mIntent);
            }
        });
        tv_algorithm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, AlgorithmActivity.class);
                startActivity(mIntent);
            }
        });
        tv_retrofit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, RetrofitMainActivity.class);
                startActivity(mIntent);
            }
        });
        tv_content_provider.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, ContentProviderActivity.class);
                startActivity(mIntent);
            }
        });
        tv_io_stream.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, IOStreamActivity.class);
                startActivity(mIntent);
            }
        });
        tv_recycler_move.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, RecyclerTestActivity.class);
                startActivity(mIntent);
            }
        });
        tv_hybrid.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, WebViewActivity.class);
                startActivity(mIntent);
            }
        });
        tv_hybrid_call.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, HybridActivity.class);
                startActivity(mIntent);
            }
        });
        tv_storage_directory.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, StorageDirectoryActivity.class);
                startActivity(mIntent);
            }
        });

        testList = new ArrayList<>();
        testList.add(new TestListBean(TEST_LIST_ONE,"String1"));
        testList.add(new TestListBean(TEST_LIST_TWO,"String2"));
        testList.add(new TestListBean(TEST_LIST_THREE,"String3"));
        testList.add(new TestListBean(TEST_LIST_FOUR,"String4"));
        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new ItemTestAdapter(this);
        rv_list.setAdapter(adapter);
        adapter.addAll(testList);
    }

    public void reset(){
        if (mAnimatorSet == null){
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(lav,"scaleX",1.0f,0.0f,1.0f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(lav,"scaleY",1.0f,0.0f,1.0f);
            mAnimatorSet = new AnimatorSet();
            mAnimatorSet.playTogether(scaleX,scaleY);
            scaleX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float scale = (float) animation.getAnimatedValue();
                    if (scale <= 0.1){
                        lav.setProgress(0);
                    }
                }
            });
        }
        mAnimatorSet.start();
    }

    public void testGetParameter(String url){

        if (TextUtils.isEmpty(url)){
            return;
        }
        Uri uri = Uri.parse(url);
        Log.e("test","key = " + uri.getQueryParameter("key"));
        Log.e("test","key2 = " + uri.getQueryParameter("key2"));
    }

    // Uri截图
    public void startPhoto(Context mContext) {

        try {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,IMAGE_UNSPECIFIED);
            intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
            ((Activity) mContext).startActivityForResult(intent, 1222);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessageTo() {

        int importance = NotificationManager.IMPORTANCE_DEFAULT;
//        String id = getApplicationContext().getPackageName();
        String id = "5996773";
        CharSequence name = "channel";
        String description = "description";

        Notification.Builder notification = new Notification.Builder(this, id);
        notification.setAutoCancel(true);
        notification.setContentTitle("title");
        notification.setContentText("describe");
        notification.setOngoing(true);
        notification.setSmallIcon(R.mipmap.ic_launcher_round);
        notification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground));
        notification.setWhen(System.currentTimeMillis());

        Intent intent = new Intent(this, SecondActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("requestCode",requestCode);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setFullScreenIntent(pendingIntent, true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setBypassDnd(false);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{0, 100});
            manager.createNotificationChannel(mChannel);
        }
        RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.layout_notification);
        remoteView.setTextColor(R.id.re_text, Color.RED);
        remoteView.setTextViewText(R.id.re_text,"通知标题" + requestCode);
        remoteView.setOnClickPendingIntent(R.id.notification, pendingIntent);

        notification.setContentIntent(pendingIntent);
        notification.setCustomContentView(remoteView);
        mNotification = notification.build();
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(requestCode, mNotification);
        requestCode++;
    }

    public void testHandler() {
        mHandler = new MyHandler(MainActivity.this);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "处理消息");
            }
        });
        Message message = mHandler.obtainMessage();
        message.obj = "message的消息";
        mHandler.sendMessage(message);

        startIntent();
    }

    public void startIntent() {
        Intent intent = getIntent();
        Log.e(TAG, "scheme:" + intent.getScheme());
        Uri uri = intent.getData();
        if (uri != null) {

            Log.e(TAG, "scheme: " + uri.getScheme());
            Log.e(TAG, "host: " + uri.getHost());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCodes) {
            if (data != null) {
                temp = data.getStringExtra(TAG);
                Toast.makeText(this, temp, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = mActivity.get();
            if (activity != null) {
                Log.i(TAG, "msg = " + msg.obj);
            }
        }
    }


    private String[] units = {"B", "KB", "MB", "GB", "TB"};

    //调用系统函数，字符串转换 long -String KB/MB
    public static String formateFileSize(Context context,long size){
        return Formatter.formatFileSize(context, size);
    }


    /**
     * 单位转换
     */
    private String getUnit(float size) {
        int index = 0;
        while (size > 1024 && index < 4) {
            size = size / 1024;
            index++;
        }
        return String.format(Locale.getDefault(), " %.2f %s", size, units[index]);
    }

}