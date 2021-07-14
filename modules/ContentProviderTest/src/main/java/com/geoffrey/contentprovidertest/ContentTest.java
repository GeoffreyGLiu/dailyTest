package com.geoffrey.contentprovidertest;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.UriMatcher;
import android.net.Uri;

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
public class ContentTest {

    public static final String URI_INIT = "content://com.geoffrey.contentprovider/user";
    public static final String URI_INIT_WITHOUT_TABLE = "content://com.geoffrey.contentprovider";
    public static final int URI_CODE_A = 1;
    public static final int URI_CODE_B = 2;
    //测试初始化的类
    public void init() {
        Uri uri = Uri.parse(URI_INIT) ;
        //resultUri追加了一个id,7;
        //resultUri为"content://com.geoffrey.contentprovider/user/7"
        Uri resultUri = ContentUris.withAppendedId(uri,7);

        //parseId(),从Uri中获取Id
        //获取resultUri的id 7
        long parseId = ContentUris.parseId(resultUri);
    }

    public String uriMartcherTest(){

        //UriMartcher类在ContentProvider中注册Uri
        //根据URI匹配ContentProvider中的数据表

        //1.初始花UriMatcher对象
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        //常量UriMatcher.NO_MATCH,不匹配任何路径的返回码

        //2.在ContentProvider中注册URI(addURI())
        matcher.addURI(URI_INIT_WITHOUT_TABLE,"user1",URI_CODE_A);
        matcher.addURI(URI_INIT_WITHOUT_TABLE,"user2",URI_CODE_B);
        //如果URI资源路径为
        //"content://com.geoffrey.contentprovider/user1",则返回注册码URI_CODE_A
        //"content://com.geoffrey.contentprovider/user2",则返回注册码URI_CODE_B

        //3.根据URI匹配URI_CODE,匹配ContentProvider中的资源(match())
        Uri uri = Uri.parse("content://com.geoffrey.contentprovider/user1");
        switch(matcher.match(uri)){
            case URI_CODE_A:
                return "TABLE_NAME_USER1";
            case URI_CODE_B:
                return "TABLE_NAME_USER2";
            default:
                return "UNKNOW";

            //如果根据URI匹配返回码为URI_CODE_A,则返回ContentProvider中名为TABLE_NAME_USER1的表名称
            //如果根据URI匹配返回码为URI_CODE_B,则返回ContentProvider中名为TABLE_NAME_USER2的表名称
        }
    }

























}