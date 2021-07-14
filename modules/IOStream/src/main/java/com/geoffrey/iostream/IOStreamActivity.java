package com.geoffrey.iostream;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * -------------------------------------
 * 作者：刘京帅 sumu
 * -------------------------------------
 * 时间：7/6/21
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class IOStreamActivity extends AppCompatActivity implements View.OnClickListener {


    private final String TAG = "IOStreamActivity";
    private EditText et_io;
    private Button btn_input_io, btn_output_io;
    private TextView tv_show_io;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_io_stream);
        initView();
        initListener();
    }

    private void initView() {
        et_io = findViewById(R.id.et_io);
        btn_input_io = findViewById(R.id.btn_input_io);
        btn_output_io = findViewById(R.id.btn_output_io);
        tv_show_io = findViewById(R.id.tv_show_io);
    }

    private void initListener() {
        btn_input_io.setOnClickListener(this);
        btn_output_io.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_output_io) {
            //文件写出
            String path = checkOrCreateFile();
            outPutToFile(path);
        } else if (id == R.id.btn_input_io) {
            //文件读取
            String path = checkOrCreateFile();
            inputFromFile(path);
        }
    }

    //文件保存到
    // /storage/emulated/0/Android/data/com.geoffrey.iostream/files/io
    private String checkOrCreateFile() {
        String filePath = getExternalFilesDir("io").getAbsolutePath() + File.separator + "fileIOTest.text";
        Log.e(TAG, "path = " + filePath);

        File file = new File(filePath);
        boolean isFileReady = true;
        if (ActivityCompat.checkSelfPermission(IOStreamActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(IOStreamActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return "";
        } else {
            if (!file.exists()) {
                try {
                    isFileReady = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return isFileReady ? filePath : "";
        }
    }

    private void outPutToFile(String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        String content = "default";
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(filePath);

            if (et_io.getText() != null) {
                content = et_io.getText().toString();
            }
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            tv_show_io.setText("写入成功,路径为:" + filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void inputFromFile(String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder builder = new StringBuilder();
        builder.append("读取文件成功,路径为:");
        builder.append(filePath);
        builder.append("\r\n");
        builder.append("读取内容为:");
        String temp = "";

        try {
            fileInputStream = new FileInputStream(filePath);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            Log.e(TAG,"file size = " + fileInputStream.available());
            while ((temp = bufferedReader.readLine()) != null) {
                builder.append(temp);
                builder.append("\r\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        tv_show_io.setText(builder.toString());
    }

    public void testByteArrayInputStream(){
        String test = "我是谁,我在哪儿,我要到哪里去";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(test.getBytes(),0,1);
    }
}
