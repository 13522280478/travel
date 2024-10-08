package com.app.demo;

import android.app.Application;


import com.app.utils.AppDir;
import com.app.utils.FrescoUtil;
import com.app.utils.SharedPreferencesUtil;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.litepal.LitePal;

/**
 */


public class MyApplication extends Application {

    private static MyApplication instance;

    public static String TAG = "xxx";

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        if (instance != null) {
            //数据库初始化
            LitePal.initialize(instance);

            //创建应用缓存路径
            AppDir.getInstance(this);

            //fresco初始化
            FrescoUtil.init(instance);

        }
    }


    public static MyApplication getInstance() {
        return instance;
    }
}
