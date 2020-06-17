package com.example.updms;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.HandlerThread;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.example.updms.constants.AppConstants;
import com.example.updms.receivers.NetworkChangeReceiver;
import com.example.updms.rest.Response.LoginData;
import com.example.updms.rest.Response.UserData;
import com.example.updms.rest.Response.WorkerLoginData;
import com.example.updms.util.AppContext;
import com.example.updms.util.LogUtils;
import com.example.updms.util.NetworkUtil;
import com.example.updms.util.TempStorage;


import com.shawnlin.preferencesmanager.PreferencesManager;

import java.util.ArrayList;
import java.util.List;


public class MyApplication extends MultiDexApplication implements NetworkChangeReceiver.OnNetworkChangeListener, Application.ActivityLifecycleCallbacks {

    private Context context;

    public final static ApiMode apiMode = ApiMode.LIVE;
    public final static boolean USE_CRASH_ANALYTICS = false;

    public final static boolean SHOW_LOG = true;
    public final static boolean RETROFIT_SHOW_LOG = true;
    public final static boolean TOAST_ERROR_LIVE = false;
    public final static boolean API_DEBUG = true;

    public final static List<Activity> ACTIVITIES = new ArrayList<>();
    public static boolean isAppForeground;
    private NetworkChangeReceiver mNetWorkChangeReciver;

    public enum ApiMode {
        TESTING_ALPHA,
        TESTING_BETA,
        LIVE
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        LogUtils.debug("MyuApplication onCreate");

        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();

        registerActivityLifecycleCallbacks(this);
        MultiDex.install(this);
        AppContext.getInstance().setContext(this);



        initialize(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(android.net.ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(new NetworkChangeReceiver(), filter);

        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(new NetworkChangeReceiver(), intentFilter);



        mNetWorkChangeReciver = new NetworkChangeReceiver();
        NetworkChangeReceiver.register(this, mNetWorkChangeReciver);
        setTempStorage();
    }

    public static void initialize(Application context) {
        try {
            NetworkUtil.getInstance(context).initialize();
            new PreferencesManager(context).setName(AppConstants.Pref.NAME).init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setTempStorage() {
        LoginData loginData = PreferencesManager.getObject(AppConstants.Pref.LOGIN_MODEL_OBJECT, LoginData.class);
        WorkerLoginData workerLoginData = PreferencesManager.getObject(AppConstants.Pref.WORKER_LOGIN_MODEL_OBJECT, WorkerLoginData.class);
        UserData userData = PreferencesManager.getObject(AppConstants.Pref.USER_MODEL_OBJECT, UserData.class);
        String authToken = PreferencesManager.getString(AppConstants.Pref.AUTH_TOKEN, "");
        LogUtils.debug(userData+"set data");
       if(loginData!=null){
            TempStorage.authToken=authToken;
            TempStorage.loginData = loginData;
        }
       if(workerLoginData!=null){
           TempStorage.workerLoginData= workerLoginData;
       }
    }

    @Override
    public void onNetworkChange(boolean isConnected) {

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        ACTIVITIES.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (!isAppForeground) {
            isAppForeground = true;
            LogUtils.debug("App is in Foreground");
            onAppForeground();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ACTIVITIES.remove(activity);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogUtils.debug("MyuApplication onTrimMemory " + level);

    }

    private void onAppForeground() {
        LogUtils.debug("AppStatus: Foreground");
    }

    private void onAppBackground() {
        LogUtils.debug("AppStatus: Background");
    }



}
