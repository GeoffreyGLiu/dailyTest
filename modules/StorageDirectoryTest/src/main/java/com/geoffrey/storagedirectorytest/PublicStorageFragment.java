package com.geoffrey.storagedirectorytest;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.geoffrey.base.fragment.BaseFragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;


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
public class PublicStorageFragment extends BaseFragment implements View.OnClickListener {

    //创建文件名称输入框和button
    private EditText et_create_file;
    private Button btn_create_file;
    //文件写入内容输入和button
    private EditText et_write_file;
    private Button btn_write_file;
    //展示创建结果和文件内容的view
    private TextView tv_show_result;
    //设置文件路径,downloads/test
    private String downloadPath;

    @Override
    public View setFragmentContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_public_storage, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initListeners();
        initDirectory();
    }

    @Override
    public void initViews() {
        if (getView() == null) {
            return;
        }
        et_create_file = getView().findViewById(R.id.et_create_file);
        btn_create_file = getView().findViewById(R.id.btn_create_file);
        et_write_file = getView().findViewById(R.id.et_write_file);
        btn_write_file = getView().findViewById(R.id.btn_write_file);
        tv_show_result = getView().findViewById(R.id.tv_show_result);
    }

    private void initDirectory() {
        String downloadPathTemp = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        downloadPath = downloadPathTemp + File.separator + "test";
        File file = new File(downloadPath);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    @Override
    public void initListeners() {
        btn_create_file.setOnClickListener(this);
        btn_write_file.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_create_file) {
            //创建文件
            createFileToDownLoad();
        } else if (id == R.id.btn_write_file) {
            //内容写入文件
            writeToFileDefault();
        }
    }

    /**
     * 创建文件到downloads目录下,默认的文件名为default,可指定文件名称.
     */
    private void createFileToDownLoad() {
        if (getActivity() == null) {
            return;
        }
        //下载路径
        //两种写法都可以,getExternalStoragePublicDirectory() 为旧API
        String downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
//        String downloadPath = getActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        CharSequence charSequence = et_create_file.getText();
        StringBuilder builder = new StringBuilder();
        String fileName;
        if (!TextUtils.isEmpty(charSequence)) {
            fileName = charSequence.toString();
        } else {
            fileName = "default";
        }
        File file = new File(downloadPath + File.separator + "test" + File.separator + fileName + ".text");
        if (!file.exists()) {
            try {
                builder.append("文件创建成功? : ");
                builder.append(file.createNewFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            builder.append("文件已存在");

        }
        builder.append("\r\n");
        builder.append("文件创建路径:");
        builder.append(file.getAbsolutePath());
        tv_show_result.setText(builder.toString());
    }

    /**
     * 将输入框中的内容写入文件中,文件名称可自定义取自创建文件的Edittext中
     */
    private void writeToFileDefault() {
        FileWriter fileWriter = null;
        CharSequence charSequenceCreate, charSequenceWrite;
        String contentTemp = "";
        //文件的全路径
        String filePath;
        //将内容写入default文件
        charSequenceCreate = et_create_file.getText();
        charSequenceWrite = et_write_file.getText();
        filePath = downloadPath + File.separator + (TextUtils.isEmpty(charSequenceCreate) ? "default" : charSequenceCreate.toString()) + ".text";
        try {
            fileWriter = new FileWriter(filePath);
            if (!TextUtils.isEmpty(charSequenceWrite)) {
                contentTemp = charSequenceWrite.toString();
            }
            fileWriter.write(contentTemp);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //将default中的内容读出显示
        String index;
        StringBuilder builder = new StringBuilder();
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        filePath = downloadPath + File.separator + (TextUtils.isEmpty(charSequenceCreate) ? "default" : charSequenceCreate.toString()) + ".text";
        try {
            fileReader = new FileReader(filePath);
            bufferedReader = new BufferedReader(fileReader);
            while ((index = bufferedReader.readLine()) != null) {
                builder.append(index);
                builder.append("\r\n");
            }
            tv_show_result.setText(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
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
    }
}
