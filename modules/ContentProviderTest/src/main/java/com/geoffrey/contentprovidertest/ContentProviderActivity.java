package com.geoffrey.contentprovidertest;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * -------------------------------------
 * 作者：刘京帅 sumu
 * -------------------------------------
 * 时间：6/28/21
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class ContentProviderActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText cp_et;
    private TextView cp_insert, cp_update, cp_delete, cp_query,cp_contact, cp_show;
    //ContentResolver提取为成员变量
    private ContentResolver resolver;
    //User表的URI
    private Uri uri_user;
    //Job表的URI
    private Uri uri_job;
    //_id的自增
    private int _id_user = 3;
    private int _id_job = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        initViews();
        initListener();
        initDataBase();
    }

    private void initViews() {
        cp_et = findViewById(R.id.cp_et);
        cp_insert = findViewById(R.id.cp_insert);
        cp_update = findViewById(R.id.cp_update);
        cp_delete = findViewById(R.id.cp_delete);
        cp_query = findViewById(R.id.cp_query);
        cp_contact = findViewById(R.id.cp_contact);
        cp_show = findViewById(R.id.cp_show);
    }

    private void initListener() {
        cp_insert.setOnClickListener(this);
        cp_update.setOnClickListener(this);
        cp_delete.setOnClickListener(this);
        cp_query.setOnClickListener(this);
        cp_contact.setOnClickListener(this);
    }

    private void initDataBase() {
        //初始化数据库
        //设置user表的URI
        uri_user = Uri.parse("content://" + MyContentProvider.AUTHORITY + "/user");
        //设置job表的URI
        uri_job = Uri.parse("content://" + MyContentProvider.AUTHORITY + "/job");
        //获取ContentResolver
        resolver = getContentResolver();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cp_insert) {
            //insert
            //通过ContentReSolver 根据URI,向ContentProvider中插入数据
            ContentValues values = new ContentValues();
            if (getUserOrJob(cp_et.getText())) {
                values.put("_id", _id_user);
                values.put("name", "Iversion" + _id_user);
                resolver.insert(uri_user, values);
                _id_user++;
            } else {
                values.put("_id", _id_job);
                values.put("job", "player" + _id_job);
                resolver.insert(uri_job, values);
                _id_job++;
            }
        } else if (id == R.id.cp_update) {
            //update
        } else if (id == R.id.cp_delete) {
            //delete

            if (getUserOrJob(cp_et.getText())) {
                String where = "name=?";
                _id_user --;
                String[] args = {"Iversion" + _id_user};
                resolver.delete(uri_user,where,args);
            } else {
                _id_job--;
                String where = "job=?";
                String[] args = {"player" + _id_job};
                resolver.delete(uri_job,where,args);
            }
        } else if (id == R.id.cp_query) {
            //query
            StringBuilder builder = new StringBuilder();
            if (getUserOrJob(cp_et.getText())) {

                builder.append("query book:");
                builder.append("\r\n");
                Cursor cursor = resolver.query(uri_user, new String[]{"_id", "name"}, null, null, null);
                while (cursor != null && cursor.moveToNext()) {
                    builder.append(cursor.getInt(0));
                    builder.append("---");
                    builder.append(cursor.getString(1));
                    builder.append("\r\n");
                }
            } else {
                builder.append("query job:");
                builder.append("\r\n");
                Cursor cursor = resolver.query(uri_job, new String[]{"_id", "job"}, null, null, null);
                while (cursor != null && cursor.moveToNext()) {
                    builder.append(cursor.getInt(0));
                    builder.append("---");
                    builder.append(cursor.getString(1));
                    builder.append("\r\n");

                }
                if (cursor != null) {
                    cursor.close();
                }
            }
            cp_show.setText(builder.toString());
        }else if (id == R.id.cp_contact){
            //读取手机的联系人,名字,号码
            readContactFromPhone();
        }
    }

    private void readContactFromPhone() {

        if (ContextCompat.checkSelfPermission(ContentProviderActivity.this, Manifest.permission.READ_CONTACTS ) != PackageManager.PERMISSION_GRANTED){
            //如果没有权限,则申请
            ActivityCompat.requestPermissions(ContentProviderActivity.this,new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS},1);
        }else {
            //直接读取联系人
           Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
           StringBuilder builder = new StringBuilder();
           //联系人的名字和号码
           String name,number;
           while(cursor!= null && cursor.moveToNext()){
               name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
               number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
               builder.append("name = ");
               builder.append(name);
               builder.append(", number = ");
               builder.append(number);
               builder.append("\r\n");
           }

           if (cursor != null){
               cursor.close();
           }
            cp_show.setText(builder.toString());
        }
    }

    public boolean getUserOrJob(CharSequence charSequence) {
        if (charSequence != null && charSequence.toString().contains("job")) {
            return false;
        }
        return true;
    }
}
