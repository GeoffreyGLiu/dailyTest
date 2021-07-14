package com.geoffrey.contentprovidertest;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
public class MyContentProvider extends ContentProvider {

    public static final String TAG = "MyContentProvider";

    private Context mContext;
    private DBHelper mDbHelper;
    private SQLiteDatabase db;

    //设置ContentProvider的authority
    public static final String AUTHORITY = "com.geoffrey.contentprovider";

    public static final int User_Code = 1;
    public static final int Job_Code = 2;

    //UriMatcher类使用:在ContentProvider中注册URI
    private static final UriMatcher uriMarcher;

    static {

        uriMarcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMarcher.addURI(AUTHORITY, "user", User_Code);
        uriMarcher.addURI(AUTHORITY, "job", Job_Code);
        //若URI资源路径为content://com.geoffrey.contentprovider/user,则返回注册码User_Code
        //若URI资源路径为content://com.geoffrey.contentprovider/job,则返回注册码Job_Code
    }

    //重写ContentProvider的六个方法

    @Override
    public boolean onCreate() {
        mContext = getContext();
        //在ContentProvider创建时对数据库进行初始化
        //运行在主线程,所以不能做耗时操作
        mDbHelper = new DBHelper(mContext);
        db = mDbHelper.getWritableDatabase();

        //初始化两个表的数据,先清空旧表,再添加一个记录
        db.execSQL("delete from user");
        db.execSQL("insert into user values(1,'Carson');");
        db.execSQL("insert into user values(2,'Kobe');");

        db.execSQL("delete from job");
        db.execSQL("insert into job values(1,'Android');");
        db.execSQL("insert into job values(2,'iOS');");

        return true;
    }

    //添加数据
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        //根据URI匹配URI_CODE,从而匹配ContentProvider中相应的表名
        //getTableName()方法在最下面
        String table = getTableName(uri);
        //通过ContentUris类从URL中获取ID
//        long personId = ContentUris.parseId(uri);
//        Log.e(TAG,"ContentUris.id = " + personId);
        //向表中添加数据
        db.insert(table,null,values);

        //当该URI的ContentProvider数据发生变化时,通知外界(访问带ContentProvider数据的访问者)
        mContext.getContentResolver().notifyChange(uri,null);

        return uri;
    }

    /**
     * 查询数据
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        //根据RUI匹配URI_CODE,从而匹配ContentProvider中的表名.
        //方法在下面
        String table = getTableName(uri);
        //通过ContentUris类从URL中获取ID
//        long personId = ContentUris.parseId(uri);
//        Log.e(TAG,"ContentUris.id = " + personId);
        //查询数据

        return db.query(table,projection,selection,selectionArgs,null,null,sortOrder,null);
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        //此处不展开
        String table = getTableName(uri);
        return db.delete(table,selection,selectionArgs);
    }

    /**
     * 更新数据
     * @param uri
     * @return
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        //此处不展开
        String table = getTableName(uri);
        return db.update(table,values,selection,selectionArgs);
    }

    /**
     * 根据URI匹配URI_CODE,从而匹配ContentProvider中相应的表名
     *
     * @param uri
     * @return
     */
    private String getTableName(Uri uri) {
        String tableName = null;
        switch (uriMarcher.match(uri)) {

            case User_Code:
                tableName = DBHelper.USER_TABLE_NAME;
                break;
            case Job_Code:
                tableName = DBHelper.JOB_TABLE_NAME;
                break;
        }
        return tableName;
    }
}
