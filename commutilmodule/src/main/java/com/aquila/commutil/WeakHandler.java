package com.aquila.commutil;

import android.os.Handler;

import java.lang.ref.WeakReference;

/***
 *
 */
public class WeakHandler<T> extends Handler {

    private WeakReference<T> weakReference ;

    private T instance;

    protected T getInstance(){
        if (instance == null){
            instance = weakReference.get() ;
        }
        return instance ;
    }

    public WeakHandler(T t){
        weakReference = new WeakReference<>(t) ;
    }

}
