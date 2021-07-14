package com.geoffrey.storagedirectorytest;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.geoffrey.base.fragment.BaseFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * -------------------------------------
 * 作者：刘京帅 sumu
 * -------------------------------------
 * 时间：7/13/21
 * -------------------------------------
 * 描述：android Q 文件保存和读取的测试
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class AndroidQStorageFragment extends BaseFragment implements View.OnClickListener {

    private final String TAG = "AndroidQStorageFragment";
    private final String FOLDER_NAME_ABSOLUTE = "MyPictureAbsolute";
    private final String FOLDER_NAME_RELATIVE = "MyPictureRelative";
    private final String MINE_TYPE = "image/JPEG";
    private final String pictures = "Pictures";
    private final String PIC_NAME = "launcher.jpg";
    private final int PIC_QUALITY = 90;

    private Button btn_absolute_save, btn_absolute_read, btn_relative_save, btn_relative_read;
    private TextView tv_path_show;
    private ImageView iv_path_show;

    @Override
    public View setFragmentContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_androidq_storage, container, false);
    }

    @Override
    public void initViews() {
        super.initViews();
        View rootView = getView();
        if (rootView != null) {
            btn_absolute_save = rootView.findViewById(R.id.btn_absolute_save);
            btn_absolute_read = rootView.findViewById(R.id.btn_absolute_read);
            btn_relative_save = rootView.findViewById(R.id.btn_relative_save);
            btn_relative_read = rootView.findViewById(R.id.btn_relative_read);
            tv_path_show = rootView.findViewById(R.id.tv_path_show);
            iv_path_show = rootView.findViewById(R.id.iv_path_show);
        }
    }

    @Override
    public void initListeners() {
        super.initListeners();
        btn_absolute_save.setOnClickListener(this);
        btn_absolute_read.setOnClickListener(this);
        btn_relative_save.setOnClickListener(this);
        btn_relative_read.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_absolute_save) {
            //绝对路径保存图片
            savePicByAbsolutePath(getActivity(), PIC_NAME, BitmapFactory.decodeResource(getResources(), R.mipmap.launcher));
        } else if (id == R.id.btn_absolute_read) {
            //绝对路径读取图片
            loadPicByAbsolutePath(getActivity(), PIC_NAME);
        } else if (id == R.id.btn_relative_save) {
            //相对路径保存图片
            savePicByRelativePath(PIC_NAME, BitmapFactory.decodeResource(getResources(), R.mipmap.launcher));
        } else if (id == R.id.btn_relative_read) {
            //相对路径读取图片
            loadPicByRelativePath(PIC_NAME);
        }
    }

    /**
     * 绝对路径保存图片,设置路径
     */

    private void savePicByAbsolutePath(Context context, String picName, Bitmap bitmap) {

        if (context == null || bitmap == null) {
            return;
        }
        //路径为App沙盒路径
//        String folderAbsolutePath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + FOLDER_NAME_ABSOLUTE;
        //公共存储区
        String folderAbsolutePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + FOLDER_NAME_ABSOLUTE;
        ;
        File folderFile = new File(folderAbsolutePath);
        if (folderFile.exists()) {
            savePicToPictures(context, folderAbsolutePath, picName, bitmap);
        } else if (folderFile.mkdir()) {
            savePicToPictures(context, folderAbsolutePath, picName, bitmap);
        }

    }

    /**
     * 绝对路径保存图片,具体流程
     */
    private void savePicToPictures(Context context, String folderAbsolutePath, String picName, Bitmap bitmap) {
        String picAbsolutePath = folderAbsolutePath + File.separator + picName;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(picAbsolutePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, PIC_QUALITY, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                    Toast.makeText(context, "图片保存成功,绝对路径", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append("图片路径为:");
        builder.append(picAbsolutePath);
        tv_path_show.setText(builder.toString());
    }


    /**
     * 以绝对路径加载DIRECTORY_PICTURES路径下的图片
     *
     * @param context
     * @param picName
     */
    private void loadPicByAbsolutePath(Context context, String picName) {
        if (context == null || TextUtils.isEmpty(picName)) {
            return;
        }
        Bitmap bitmap = null;
        //app私有路径
//        File pictureFolderFile = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //公共pictures路径
        String picFolderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator+ FOLDER_NAME_ABSOLUTE;
        File pictureFolderFile = new File(picFolderPath);
        if (pictureFolderFile.exists() && pictureFolderFile.isDirectory()) {
            File[] pictureFiles = pictureFolderFile.listFiles();
            if (pictureFiles != null) {
                for (File file : pictureFiles) {
                    if (file != null && file.isFile() && picName.equals(file.getName())) {
                        bitmap = BitmapFactory.decodeFile(file.getPath());
                        break;
                    }
                }
            }
        }
        if (bitmap != null) {
            iv_path_show.setImageBitmap(bitmap);
        }
    }

    /**
     * 相对路径保存图片,由ContentProvider和ContentResolver
     */
    private void savePicByRelativePath(String fileName, Bitmap bitmap) {
        if (getActivity() == null || bitmap == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        //设置文件名
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //Android Q中不再使用DATA字段,而是使用RELATIVE_PATH字段,相对路径
            //PICTURES,系统自带的文件夹
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, pictures + File.separator + FOLDER_NAME_RELATIVE);
        } else {
            contentValues.put(MediaStore.Images.Media.DATA, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath());
        }
        //设置文件类型
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, MINE_TYPE);
        //执行insert,向系统文件夹中插入数据
        //EXTERNAL_CONTENT_URI,代表外存储器,其值不变.

        ContentResolver contentResolver = getActivity().getContentResolver();
        OutputStream os = null;
        Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        if (uri != null) {
            //若生成了Uri,表示文件插入成功,此时使用OutputStream写入文件即可
            try {
                os = contentResolver.openOutputStream(uri);
                if (os != null) {
                    //压缩图片的方法
                    bitmap.compress(Bitmap.CompressFormat.JPEG, PIC_QUALITY, os);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (os != null) {
                    try {
                        os.flush();
                        os.close();
                        Toast.makeText(getActivity(), "图片保存成功,相对路径", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    /**
     * 相对路径加载保存的图片
     */
    private void loadPicByRelativePath(String fileName) {
        if (getActivity() == null || TextUtils.isEmpty(fileName)) {
            return;
        }
        //兼容android Q版本
        String queryPathKey = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ? MediaStore.Images.Media.RELATIVE_PATH : MediaStore.Images.Media.DATA;
        //查询语句
        String selection = queryPathKey + "=?";
        //查询的sql
        //Uri：指向外部存储Uri
        //projection：查询那些结果
        //selection：查询的where条件
        //sortOrder：排序
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID, queryPathKey, MediaStore.Images.Media.MIME_TYPE, MediaStore.Images.Media.DISPLAY_NAME},
                selection,
                new String[]{pictures + "/" + FOLDER_NAME_RELATIVE + "/"},
                null);
        int id = -1;
        String name = null;
        Uri uri = null;
        InputStream is = null;
        Bitmap bitmap = null;
        while (cursor != null && cursor.moveToNext()){
            id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
            name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,String.valueOf(id));
            if (uri != null && fileName.equals(name)){
                try {
                    is = contentResolver.openInputStream(uri);
                    bitmap = BitmapFactory.decodeStream(is);
                    if (bitmap != null){
                        iv_path_show.setImageBitmap(bitmap);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null){
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
