package com.geoffrey.contentprovidertest;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.FileDescriptor;

/**
 * -------------------------------------
 * 作者：刘京帅 sumu
 * -------------------------------------
 * 时间：7/1/21
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
class IBinderTest implements IBinder ,IInterface{
    @Nullable
    @Override
    public String getInterfaceDescriptor() throws RemoteException {
        return null;
    }

    @Override
    public boolean pingBinder() {
        return false;
    }

    @Override
    public boolean isBinderAlive() {
        return false;
    }

    @Nullable
    @Override
    public IInterface queryLocalInterface(@NonNull String descriptor) {
        return null;
    }

    @Override
    public void dump(@NonNull FileDescriptor fd, @Nullable String[] args) throws RemoteException {

    }

    @Override
    public void dumpAsync(@NonNull FileDescriptor fd, @Nullable String[] args) throws RemoteException {

    }

    @Override
    public boolean transact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        return false;
    }

    @Override
    public void linkToDeath(@NonNull DeathRecipient recipient, int flags) throws RemoteException {

    }

    @Override
    public boolean unlinkToDeath(@NonNull DeathRecipient recipient, int flags) {
        return false;
    }

    @Override
    public IBinder asBinder() {
        return null;
    }
}
