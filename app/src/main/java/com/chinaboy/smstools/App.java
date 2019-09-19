package com.chinaboy.smstools;

import android.app.Application;
import android.content.Intent;

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        Thread.setDefaultUncaughtExceptionHandler(restartHandler);
    }

    private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {

        public void uncaughtException(Thread thread, Throwable ex) {
    //        restartApp();
        }
    };

    public void restartApp() {
        Intent intent = new Intent(instance,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        instance.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static App getContext() {
        return instance;
    }
}
