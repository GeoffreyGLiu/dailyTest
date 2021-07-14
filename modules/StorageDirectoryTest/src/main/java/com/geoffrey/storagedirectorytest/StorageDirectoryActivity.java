package com.geoffrey.storagedirectorytest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.geoffrey.base.activity.BaseFragmentActivity;
import com.geoffrey.base.fragment.BaseFragment;

/**
 * -------------------------------------
 * 作者：刘京帅 sumu
 * -------------------------------------
 * 时间：7/11/21
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class StorageDirectoryActivity extends BaseFragmentActivity implements View.OnClickListener {

    private Button btn_public_storage, btn_android_q, btn_empty2;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_directory);
        initViews();
        initListener();
        initData();
    }

    private void initViews() {
        btn_public_storage = findViewById(R.id.btn_public_storage);
        btn_android_q = findViewById(R.id.btn_android_q);
        btn_empty2 = findViewById(R.id.btn_empty2);
    }

    private void initListener() {
        btn_public_storage.setOnClickListener(this);
        btn_android_q.setOnClickListener(this);
        btn_empty2.setOnClickListener(this);
    }

    private void initData() {
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_public_storage) {
            //公共存储
            loadPublicStorage();
        } else if (id == R.id.btn_android_q) {
            //Android Q 图片保存和加载
            loadAndroidQFragment();
        } else if (id == R.id.btn_empty2) {
            //广告位招租2

        }
    }

    private PublicStorageFragment getPublicStorageFragment(){
        PublicStorageFragment publicStorageFragment = null;
        Fragment fragment = null;
        fragment = fragmentManager.findFragmentByTag(PublicStorageFragment.class.getSimpleName());
        if (fragment instanceof PublicStorageFragment) {
            publicStorageFragment = (PublicStorageFragment) fragment;
        } else {
            publicStorageFragment = new PublicStorageFragment();
        }
        return publicStorageFragment;
    }

    private AndroidQStorageFragment getAndroidQStorageFragment(){
        AndroidQStorageFragment publicStorageFragment = null;
        Fragment fragment = null;
        fragment = fragmentManager.findFragmentByTag(AndroidQStorageFragment.class.getSimpleName());
        if (fragment instanceof AndroidQStorageFragment) {
            publicStorageFragment = (AndroidQStorageFragment) fragment;
        } else {
            publicStorageFragment = new AndroidQStorageFragment();
        }
        return publicStorageFragment;
    }



    /**
     * 加载公共存储的fragment
     */
    private void loadPublicStorage() {
        PublicStorageFragment fragment = getPublicStorageFragment();
        replaceFragmentToContainer(getSupportFragmentManager(), R.id.fl_container_storage, fragment, PublicStorageFragment.class.getSimpleName());
    }

    private void loadAndroidQFragment() {
        AndroidQStorageFragment fragment = getAndroidQStorageFragment();
        replaceFragmentToContainer(getSupportFragmentManager(), R.id.fl_container_storage, fragment, AndroidQStorageFragment.class.getSimpleName());
    }
}
