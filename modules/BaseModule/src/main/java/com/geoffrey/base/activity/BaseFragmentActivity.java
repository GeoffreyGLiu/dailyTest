package com.geoffrey.base.activity;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.geoffrey.base.fragment.BaseFragment;

/**
 * -------------------------------------
 * 作者：刘京帅 sumu
 * -------------------------------------
 * 时间：7/13/21
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public abstract class BaseFragmentActivity extends FragmentActivity {


    public <T extends BaseFragment> void replaceFragmentToContainer(FragmentManager fm, @IdRes int containerId, T t, String tag) {
        if (t == null) {
            return;
        }
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(containerId, t, tag);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
