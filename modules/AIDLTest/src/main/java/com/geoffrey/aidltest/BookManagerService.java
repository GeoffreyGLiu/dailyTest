package com.geoffrey.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * -------------------------------------
 * 作者：刘京帅 sumu
 * -------------------------------------
 * 时间：3/17/21
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class BookManagerService extends Service {

    private static final String TAG = "BookManagerService";
    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public Book getBookByIndex(int index) throws RemoteException {
            if (bookList != null && index < bookList.size()){
                return bookList.get(index);
            }
            return null;
        }

        @Override
        public void addBookToList(Book book) throws RemoteException {

            if (bookList != null && !bookList.contains(book)){
                bookList.add(book);
            }
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
