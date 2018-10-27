package com.example.tonghai.testapp.activity;

import android.support.multidex.MultiDexApplication;

import com.example.tonghai.testapp.service.MainService;


public class MyApplication extends MultiDexApplication {
    private MainService mainService;
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public MainService getMainService() {
        if (mainService == null) mainService = MainService.Factory.create();
        return mainService;
    }
}
