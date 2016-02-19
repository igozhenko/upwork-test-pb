package com.upwork.pb.app;

import android.app.Application;
import android.support.annotation.StringRes;

public class App extends Application {
    private static App sInstance;
    private static AppDataManager sDataManager;

    public static AppDataManager getDataManager() {
        return sDataManager;
    }

    public static String getString_(@StringRes int resId) {
        return sInstance.getString(resId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sDataManager = new AppDataManager(this);
    }
}
