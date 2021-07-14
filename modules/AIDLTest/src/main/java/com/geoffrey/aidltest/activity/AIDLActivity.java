package com.geoffrey.aidltest.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.geoffrey.aidltest.Book;
import com.geoffrey.aidltest.BookManagerService;
import com.geoffrey.aidltest.IBookManager;
import com.geoffrey.aidltest.R;

import java.util.List;

/**
 * -------------------------------------
 * 作者：刘京帅 sumu
 * -------------------------------------
 * 时间：3/17/21
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class AIDLActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AIDLActivity";
    private EditText et_aidl;
    private Button btn_add;
    private Button btn_query_single;
    private Button btn_query_list;
    private TextView tv_query_result;

    private ServiceConnection serviceConnection;
    private IBookManager iBookManager = null;
    private Book book;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        initView();
        initListener();
        initData();
        initService();
    }

    private void initView() {
        et_aidl = findViewById(R.id.et_aidl);
        btn_add = findViewById(R.id.btn_add);
        btn_query_single = findViewById(R.id.btn_query_single);
        btn_query_list = findViewById(R.id.btn_query_list);
        tv_query_result = findViewById(R.id.tv_query_result);
    }

    private void initListener() {
        btn_add.setOnClickListener(this);
        btn_query_single.setOnClickListener(this);
        btn_query_list.setOnClickListener(this);
    }

    private void initData() {
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                if (service != null) {
                    iBookManager = IBookManager.Stub.asInterface(service);
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

    private void initService() {
        Intent mIntent = new Intent(this, BookManagerService.class);
        bindService(mIntent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_add) {
            //添加书籍
            addBookToList();

        } else if (id == R.id.btn_query_single) {
            //查询单本书籍
            getBookByIndex();

        } else if (id == R.id.btn_query_list) {
            //查询书籍列表
            getBookList();
        }
    }

    /**
     * 添加书籍到列表中
     */
    private void addBookToList() {

        if (iBookManager != null) {
            String name = TextUtils.isEmpty(et_aidl.getText()) ? "android开发艺术" : et_aidl.getText().toString();
            book = new Book(name);
            try {
                iBookManager.addBookToList(book);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据index查询书籍
     */
    private void getBookByIndex() {

        if (iBookManager == null) {
            return;
        }
        String name = TextUtils.isEmpty(et_aidl.getText()) ? "0" : et_aidl.getText().toString();
        int index = Integer.parseInt(name);
        try {
            Book book = iBookManager.getBookByIndex(index);
            if (book != null){
                tv_query_result.setText(book.toString());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取书籍列表
     */
    private void getBookList() {

        if (iBookManager == null) {
            return;
        }
        try {
            List<Book> bookList = iBookManager.getBookList();
            if (bookList != null){
                tv_query_result.setText(bookList.toString());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (serviceConnection != null){
            unbindService(serviceConnection);
        }
        super.onDestroy();
    }
}
