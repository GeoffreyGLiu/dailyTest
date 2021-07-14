package com.geoffrey.dagger2;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.geoffrey.dagger2.base.IpContract;
import com.geoffrey.dagger2.bean.IpInfo;
import com.geoffrey.dagger2.impl.IpInfoTask;
import com.geoffrey.dagger2.impl.IpPresenter;

/**
 * -------------------------------------
 * 作者：刘京帅 sumu
 * -------------------------------------
 * 时间：4/23/21
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class RetrofitMainActivity extends AppCompatActivity implements IpContract.IpView {

    private Button btn_net;
    private TextView tv_show;
    private String url = "117.136.12.79";
    private IpContract.IpPresenter presenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        initView();
        initListener();
        setPresenter(new IpPresenter(this, IpInfoTask.getInstance()));
    }

    private void initView() {
        btn_net = findViewById(R.id.btn_net);
        tv_show = findViewById(R.id.tv_show);
    }

    private void initListener() {
        btn_net.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getIpInfo(url);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void setIpInfo(IpInfo ipInfo) {

        if (ipInfo != null){
            tv_show.setText(ipInfo.toString());
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void setPresenter(IpContract.IpPresenter presenter) {
        this.presenter = presenter;
    }
}
