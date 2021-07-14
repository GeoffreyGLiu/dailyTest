package com.geoffrey.test.greendao;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.geoffrey.test.R;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class DaoActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_add,tv_delete, tv_update,tv_query,tv_show;
    private Long id;
    private int count;
    private DaoMaster.DevOpenHelper devOpenHelper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private UserDao userDao;
    private User user;
    private User user1;
    private User user2;
    private User user3;
    private QueryBuilder<User> queryBuilder;
    private ArrayList<User> userList;
    private String[] nameArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dao);
        initView();
        initData();
        initListener();
    }

    private void initView() {

        tv_add = findViewById(R.id.tv_add);
        tv_delete = findViewById(R.id.tv_delete);
        tv_update = findViewById(R.id.tv_update);
        tv_query = findViewById(R.id.tv_query);
        tv_show = findViewById(R.id.tv_show);
    }

    private void initData() {
        id = 343223454L;
        count = 0;
        userList =  new ArrayList<>();
        nameArray = new String[]{"张三","李四","王五","赵六"};
        devOpenHelper = new DaoMaster.DevOpenHelper(this,"db_user",null);
        db = devOpenHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        userDao = daoSession.getUserDao();
        queryBuilder = userDao.queryBuilder();
        userDao.insertOrReplace(new User(id++,"张三"));
        userDao.insertOrReplace(new User(id++,"李四"));
        userDao.insertOrReplace(new User(id++,"王五"));
        userDao.insertOrReplace(new User(id++,"赵六"));
    }

    private void initListener() {

        tv_add.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_update.setOnClickListener(this);
        tv_query.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_add){
            //增加
            addUser();
        }else if (id == R.id.tv_delete){
            //删除
            deleteUser();
        }else if (id == R.id.tv_update){
            //修改
            updateUser();
        }else if (id == R.id.tv_query){
            //查询
            queryUser();
        }
    }

    private void addUser(){
        userDao.insertOrReplace(new User(id++, nameArray[count % 4] + count));
        queryUser();
        count++;

    }

    private void deleteUser(){
        userDao.deleteByKey(id--);
        queryUser();
    }

    private void updateUser(){
    }


    private void queryUser(){
        List<User> list = queryBuilder.list();
        printUserInfo(list);
    }

    private void printUserInfo(List<User> list) {

        StringBuilder builder = new StringBuilder();
        if (list != null && list.size() > 0){
            for (User user:list){
                builder.append(user.toString());
                builder.append("\r\n");
            }
        }
        tv_show.setText(builder.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
